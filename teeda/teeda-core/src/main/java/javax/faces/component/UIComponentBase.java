package javax.faces.component;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;

public abstract class UIComponentBase extends UIComponent {

    private String id_;

    private ComponentAttributesMap_ attributesMap_ = null;

    private Map bindingMap_ = new HashMap();

    private UIComponent parent_;

    private String clientId_ = null;

    private boolean isRendered_ = true;

    private boolean renderSet_ = false;

    private String renderType_ = null;

    private List childrenList_ = null;

    private Map facetMap_ = null;

    private List listeners_ = null;

    private boolean isTransient_ = false;

    private static final String RENDERED = "rendered";

    private static final String RENDERER_TYPE = "rendererType";

    private static final int LIST_NULL_SIZE = 0;

    public Map getAttributes() {
        if (attributesMap_ == null) {
            attributesMap_ = new ComponentAttributesMap_(this);
        }
        return attributesMap_;
    }

    public ValueBinding getValueBinding(String name) {
        ComponentUtils_.assertNotNull(name, "name");
        return (ValueBinding) bindingMap_.get(name);
    }

    public void setValueBinding(String name, ValueBinding valuebinding) {
        ComponentUtils_.assertNotNull(name, "name");

        if (name.equals("id") || name.equals("parent")) {
            throw new IllegalArgumentException();
        }

        if (valuebinding != null) {
            bindingMap_.put(name, valuebinding);
        } else {
            bindingMap_.remove(name);
        }
    }

    public String getClientId(FacesContext context) {
        ComponentUtils_.assertNotNull(context, "context");
        if (clientId_ != null) {
            return clientId_;
        }

        UIComponent component = this;
        String parentId = "";
        while ((component = component.getParent()) != null) {
            if (component instanceof NamingContainer) {
                parentId = component.getClientId(context)
                    + NamingContainer.SEPARATOR_CHAR;
                break;
            }
        }

        clientId_ = parentId + ((id_ != null) ? id_ : createUniqueId(context));
        Renderer renderer = getRenderer(context);
        if (renderer != null) {
            clientId_ = renderer.convertClientId(context, clientId_);
        }
        return clientId_;
    }

    private static String createUniqueId(FacesContext context) {
        return context.getViewRoot().createUniqueId();
    }

    public String getId() {
        return id_;
    }

    public void setId(String id) {
        validateId(id);
        id_ = id;
    }

    public UIComponent getParent() {
        return parent_;
    }

    public void setParent(UIComponent parent) {
        parent_ = parent;
    }

    public boolean isRendered() {
        if (renderSet_) {
            return renderSet_;
        }
        ValueBinding vb = getValueBinding(RENDERED);
        if (vb != null) {
            Boolean value = (Boolean) this.getValueFromBinding(vb);
            renderSet_ = Boolean.TRUE.equals(value);
        }

        return renderSet_;
    }

    public void setRendered(boolean isRendered) {
        isRendered_ = isRendered;
        renderSet_ = true;
    }

    public String getRendererType() {
        if (renderType_ != null) {
            return renderType_;
        }
        ValueBinding vb = getValueBinding(RENDERER_TYPE);
        String result = null;
        if (vb != null) {
            result = (String) this.getValueFromBinding(vb);
        }
        return result;
    }

    public void setRendererType(String renderType) {
        renderType_ = renderType;
    }

    private Object getValueFromBinding(ValueBinding vb) {
        return vb.getValue(getFacesContext());
    }

    public boolean getRendersChildren() {
        boolean result = false;
        Renderer renderer = getRenderer(getFacesContext());
        if (getRendererType() != null && renderer != null) {
            result = renderer.getRendersChildren();
        }
        return result;
    }

    public List getChildren() {
        if (childrenList_ == null) {
            childrenList_ = new ComponentChildrenListWrapper_(this);
        }
        return childrenList_;
    }

    public int getChildCount() {
        return (childrenList_ != null) ? childrenList_.size() : LIST_NULL_SIZE;
    }

    public UIComponent findComponent(String expr) {
        ComponentUtils_.assertNotNull(expr, "expr");
        UIComponent base = this;
        if (expr.charAt(0) == NamingContainer.SEPARATOR_CHAR) {
            getComponentRoot(base);
            expr.substring(1);
        } else {
            for (base = base.getParent(); base.getParent() != null; base = base
                .getParent()) {
                if (base instanceof NamingContainer) {
                    break;
                }
            }
        }

        UIComponent result = null;
        while (expr.length() > 0) {
            String id = null;
            int separator = expr.indexOf(NamingContainer.SEPARATOR_CHAR);
            if (separator >= 0) {
                id = expr.substring(0, separator);
                expr = expr.substring(separator + 1);
            } else {
                id = expr;
                expr = null;
            }

            result = findComponent(base, id);
            if (result == null || expr == null) {
                break;
            }
            if (result instanceof NamingContainer) {
                result = result.findComponent(expr);
                break;
            } else {
                throw new IllegalArgumentException(id);
            }
        }
        return result;
    }

    private UIComponent findComponent(UIComponent base, String id) {
        if (containsSameId(base, id)) {
            return base;
        }

        UIComponent child = null;
        UIComponent result = null;
        for (Iterator itr = base.getFacetsAndChildren(); itr.hasNext();) {
            child = (UIComponent) itr.next();
            if (!(child instanceof NamingContainer)) {
                result = findComponent(child, id);
                if (result != null) {
                    break;
                }
            } else if (containsSameId(child, id)) {
                result = child;
                break;
            }
        }
        return result;
    }

    private static boolean containsSameId(UIComponent base, String id) {
        return id.equals(base.getId());
    }

    private static UIComponent getComponentRoot(UIComponent base) {
        UIComponent parent;
        for (parent = base.getParent(); parent.getParent() != null; parent = parent
            .getParent())
            ;
        return parent;
    }

    public Map getFacets() {
        if (facetMap_ == null) {
            facetMap_ = new ComponentFacetMapWrapper_(this);
        }
        return facetMap_;
    }

    public UIComponent getFacet(String name) {
        return (facetMap_ != null) ? (UIComponent) facetMap_.get(name) : null;
    }

    public Iterator getFacetsAndChildren() {
        return new ComponentFacetAndChildrenIterator_(facetMap_, childrenList_);
    }

    public void broadcast(FacesEvent event) throws AbortProcessingException {
        ComponentUtils_.assertNotNull(event, "event");
        if (listeners_ != null) {
            for (Iterator itr = listeners_.iterator(); itr.hasNext();) {
                FacesListener listener = (FacesListener) itr.next();
                if (event.isAppropriateListener(listener)) {
                    event.processListener(listener);
                }
            }
        }
    }

    public void decode(FacesContext context) {
        ComponentUtils_.assertNotNull(context, "context");
        Renderer renderer = getRendererForEncodeOrDecode(context);
        renderer.decode(context, this);
    }

    public void encodeBegin(FacesContext context) throws IOException {
        ComponentUtils_.assertNotNull(context, "context");
        if (!isRendered()) {
            return;
        }

        Renderer renderer = getRendererForEncodeOrDecode(context);
        renderer.encodeBegin(context, this);
    }

    public void encodeChildren(FacesContext context) throws IOException {
        ComponentUtils_.assertNotNull(context, "context");
        if (!isRendered()) {
            return;
        }
        Renderer renderer = getRendererForEncodeOrDecode(context);
        renderer.encodeChildren(context, this);
    }

    public void encodeEnd(FacesContext context) throws IOException {
        ComponentUtils_.assertNotNull(context, "context");
        if (!isRendered()) {
            return;
        }
        Renderer renderer = getRendererForEncodeOrDecode(context);
        renderer.encodeEnd(context, this);
    }

    private Renderer getRendererForEncodeOrDecode(FacesContext context) {
        Renderer renderer = new NullRenderer();
        renderer = getRenderer(context);
        return renderer;
    }

    protected void addFacesListener(FacesListener listener) {
        ComponentUtils_.assertNotNull(listener, "listener");
        if (listeners_ == null) {
            listeners_ = new ArrayList();
        }
        listeners_.add(listener);
    }

    protected FacesListener[] getFacesListeners(Class clazz) {
        ComponentUtils_.assertNotNull(clazz, "class");
        if (!FacesListener.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(clazz.getName()
                + " is not FacesListener");
        }

        if (listeners_ == null) {
            return (FacesListener[]) Array.newInstance(clazz, 0);
        }

        List result = new ArrayList();
        FacesListener listener = null;
        for (Iterator itr = listeners_.iterator(); itr.hasNext();) {
            listener = (FacesListener) itr.next();
            if (clazz.isAssignableFrom(listener.getClass())) {
                result.add(listener);
            }
        }

        Object[] args = (Object[]) Array.newInstance(clazz, result.size());
        return (FacesListener[]) result.toArray(args);
    }

    protected void removeFacesListener(FacesListener listener) {
        ComponentUtils_.assertNotNull(listener, "listener");

        if (listeners_ != null) {
            listeners_.remove(listener);
        }
    }

    public void queueEvent(FacesEvent event) {
        ComponentUtils_.assertNotNull(event, "event");
        UIComponent parent = getParent();
        if (parent == null) {
            throw new IllegalStateException("parent not found");
        }
        parent.queueEvent(event);
    }

    public void processDecodes(FacesContext context) {
        ComponentUtils_.assertNotNull(context, "context");
        if (!isRendered()) {
            return;
        }
        UIComponent component = null;
        for (Iterator itr = getFacetsAndChildren(); itr.hasNext();) {
            component = (UIComponent) itr.next();
            component.processDecodes(context);
        }
        try {
            decode(context);
        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }
    }

    public void processValidators(FacesContext context) {
        ComponentUtils_.assertNotNull(context, "context");
        if (!isRendered()) {
            return;
        }
        UIComponent component = null;
        for (Iterator itr = getFacetsAndChildren(); itr.hasNext();) {
            component = (UIComponent) itr.next();
            component.processValidators(context);
        }
    }

    public void processUpdates(FacesContext context) {
        ComponentUtils_.assertNotNull(context, "context");
        if (!isRendered()) {
            return;
        }
        UIComponent component = null;
        for (Iterator itr = getFacetsAndChildren(); itr.hasNext();) {
            component = (UIComponent) itr.next();
            component.processUpdates(context);
        }
    }

    public Object processSaveState(FacesContext context) {
        ComponentUtils_.assertNotNull(context, "context");

        if (isTransient()) {
            return null;
        }

        Map facetMap = new HashMap();
        for (Iterator itr = getFacets().keySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            String key = (String) entry.getKey();
            UIComponent component = (UIComponent) entry.getValue();
            if (!component.isTransient()) {
                facetMap.put(key, component.processSaveState(context));
            }
        }

        List children = new ArrayList();
        for (Iterator itr = childrenList_.iterator(); itr.hasNext();) {
            UIComponent component = (UIComponent) itr.next();
            if (!component.isTransient()) {
                children.add(component.processSaveState(context));
            }
        }

        return new SerializableStateHolder(saveState(context), facetMap,
            children);
    }

    public void processRestoreState(FacesContext context, Object state) {
        ComponentUtils_.assertNotNull(context, "context");

        if (state instanceof SerializableStateHolder) {
            throw new IllegalArgumentException();
        }

        SerializableStateHolder holder = (SerializableStateHolder) state;

        Object thisState = holder.getState();
        Map facetMap = holder.getFacetMap();
        List children = holder.getChildren();

        for (Iterator itr = getFacets().keySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            Object facetState = facetMap.get(entry.getKey());
            if (facetState != null) {
                UIComponent component = (UIComponent) entry.getValue();
                component.processRestoreState(context, facetState);
            }
        }

        int index = 0;
        for (Iterator itr = getChildren().iterator(); itr.hasNext(); index++) {
            Object childrenState = children.get(index);
            if (childrenState != null) {
                UIComponent component = (UIComponent) itr.next();
                component.processRestoreState(context, childrenState);
            }
        }

        restoreState(context, thisState);
    }

    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    protected Renderer getRenderer(FacesContext context) {
        Renderer renderer = null;
        String rendererType = getRendererType();
        if (rendererType != null) {
            RenderKit renderKit = RenderKitUtil.getRenderKit(context);
            renderer = renderKit.getRenderer(getFamily(), rendererType);
        }
        return renderer;
    }

    public boolean isTransient() {
        return isTransient_;
    }

    public void setTransient(boolean transientFlag) {
        isTransient_ = transientFlag;
    }

    public Object saveState(FacesContext context) {

        Object[] values = new Object[7];
        values[0] = id_;
        values[1] = ComponentUtils_.convertToBoolean(isRendered_);
        values[2] = renderType_;
        values[3] = clientId_;
        values[4] = saveAttributesMap();
        values[5] = saveAttachedState(context, listeners_);
        values[6] = saveValueBindingMap(context);

        return (Object) values;
    }

    public void restoreState(FacesContext context, Object state) {

        Object[] values = (Object[]) state;
        id_ = (String) values[0];
        isRendered_ = ((Boolean) values[1]).booleanValue();
        renderType_ = (String) values[2];
        clientId_ = (String) values[3];
        restoreAttributeMap(values[4]);
        listeners_ = (List) restoreAttachedState(context, values[5]);
        restoreValueBindingMap(context, values[6]);

    }

    public static Object saveAttachedState(FacesContext context,
        Object attachedObject) {
        ComponentUtils_.assertNotNull(context, "context");

        if (attachedObject == null) {
            return null;
        }
        Object obj = null;
        Object result = null;
        if (attachedObject instanceof List) {
            List attachedList = (List) attachedObject;
            List resultList = new ArrayList(attachedList.size());
            for (Iterator itr = attachedList.iterator(); itr.hasNext();) {
                obj = itr.next();
                resultList.add(new AttachedObjectStateWrapper_(context, obj));
            }
            result = resultList;
        } else {
            result = new AttachedObjectStateWrapper_(context, obj);
        }
        return result;
    }

    public static Object restoreAttachedState(FacesContext context,
        Object stateObject) {
        ComponentUtils_.assertNotNull(context, "context");

        if (stateObject == null) {
            return null;
        }

        Object result = null;
        if (stateObject instanceof List) {
            List list = (List) stateObject;

            List resultList = new ArrayList(list.size());
            for (Iterator itr = list.iterator(); itr.hasNext();) {

                AttachedObjectStateWrapper_ wrapper = (AttachedObjectStateWrapper_) itr
                    .next();
                resultList.add(wrapper.restore(context));

            }
            result = resultList;
        } else if (stateObject instanceof AttachedObjectStateWrapper_) {
            AttachedObjectStateWrapper_ wrapper = (AttachedObjectStateWrapper_) stateObject;
            result = wrapper.restore(context);
        } else {
            throw new IllegalStateException();
        }

        return result;
    }

    private Object saveAttributesMap() {
        return (attributesMap_ != null) ? attributesMap_.getAttributesActual()
            : null;
    }

    private void restoreAttributeMap(Object state) {
        if (state != null) {
            attributesMap_ = new ComponentAttributesMap_(this, (Map) state);
        } else {
            clearAttributeMap();
        }
    }

    private Object saveValueBindingMap(FacesContext context) {
        Map states = null;
        if (bindingMap_ != null) {
            states = new HashMap();
            for (Iterator itr = bindingMap_.entrySet().iterator(); itr
                .hasNext();) {
                Map.Entry entry = (Map.Entry) itr.next();
                states.put(entry.getKey(), saveAttachedState(context, entry
                    .getValue()));
            }
        }
        return states;
    }

    private void restoreValueBindingMap(FacesContext context, Object state) {

        bindingMap_ = null;
        if (state != null) {
            Map stateMap = (Map) state;
            bindingMap_ = new HashMap();
            for (Iterator itr = stateMap.keySet().iterator(); itr.hasNext();) {
                Map.Entry entry = (Map.Entry) itr.next();
                bindingMap_.put(entry.getKey(), restoreAttachedState(context,
                    entry.getValue()));
            }
        }

    }

    private void clearAttributeMap() {
        attributesMap_ = null;
    }

    private void validateId(String id) {

        if (id == null) {
            return;
        }

        if (id.length() < 1) {
            throw new IllegalArgumentException("UIComponentBase");
        }
        char ch;
        for (int i = 0; i < id.length(); i++) {
            ch = id.charAt(i);
            if (i == 0 && !Character.isLetter(ch) && ch != '_') {
                throw new IllegalArgumentException(
                    "The first character is invalid");
            } else if (!Character.isDigit(ch) && !Character.isLetter(ch)
                && ch != '-' && ch != '_') {
                throw new IllegalArgumentException(
                    "Subsequent character is invalid");
            }
        }

    }

    private class SerializableStateHolder implements Serializable {
        private Object state_ = null;
        private Map facetMap_ = null;
        private List children_ = null;

        public SerializableStateHolder(Object state, Map facetMap, List children) {
            state_ = state;
            facetMap_ = facetMap;
            children_ = children;
        }

        public List getChildren() {
            return children_;
        }

        public Map getFacetMap() {
            return facetMap_;
        }

        public Object getState() {
            return state_;
        }
    }

    private static class NullRenderer extends Renderer {
        public NullRenderer() {
        }
    }
}
