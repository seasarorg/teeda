/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.render.html;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.internal.UIComponentUtil;
import javax.faces.render.Renderer;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.ComponentIdLookupStrategy;
import org.seasar.teeda.core.render.DefaultComponentIdLookupStrategy;
import org.seasar.teeda.core.util.LoopIterator;
import org.seasar.teeda.core.util.RenderedComponentIterator;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public abstract class AbstractHtmlRenderer extends Renderer {

    private ComponentIdLookupStrategy idLookupStartegy;

    protected RenderAttributes renderAttributes;

    public AbstractHtmlRenderer() {
        idLookupStartegy = new DefaultComponentIdLookupStrategy();
    }

    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException {
        assertNotNull(context, component);
        return RendererUtil.getConvertedUIOutputValue(context,
                (UIOutput) component, submittedValue);
    }

    protected String getIdForRender(FacesContext context, UIComponent component) {
        return idLookupStartegy.getId(context, component);
    }

    protected UIComponent toNullIfNotRendered(UIComponent component) {
        if (component != null && !component.isRendered()) {
            return null;
        }
        return component;
    }

    protected void encodeComponent(FacesContext context, UIComponent component)
            throws IOException {
        component.encodeBegin(context);
        if (component.getRendersChildren()) {
            component.encodeChildren(context);
        } else {
            encodeDescendantComponent(context, component);
        }
        component.encodeEnd(context);
    }

    protected void encodeDescendantComponent(FacesContext context,
            UIComponent component) throws IOException {
        for (Iterator it = getRenderedChildrenIterator(component); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();
            encodeComponent(context, child);
        }
    }

    // for parameter check.
    protected void assertNotNull(FacesContext context, UIComponent component) {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
    }

    // for styles
    protected String[] splitByComma(String s) {
        String[] split = StringUtil.split(s, ",");
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].trim();
        }
        return split;
    }

    protected LoopIterator toStyleLoopIterator(String s) {
        return new LoopIterator(splitByComma(s));
    }

    protected Iterator getRenderedChildrenIterator(UIComponent component) {
        return new RenderedComponentIterator(component.getChildren());
    }

    protected String getLabelStyleClass(UIComponent component, boolean disabled) {
        if (disabled) {
            return UIComponentUtil.getStringAttribute(component,
                    JsfConstants.DISABLED_CLASS_ATTR);
        } else {
            return UIComponentUtil.getStringAttribute(component,
                    JsfConstants.ENABLED_CLASS_ATTR);
        }
    }

    protected void renderAttributes(UIComponent component, ResponseWriter writer)
            throws IOException {
        final String[] names = getRenderAttributeNames(getClass());
        RendererUtil.renderAttributes(writer, component, names);
        renderAttributes(component.getAttributes(), writer);
    }

    private String[] getRenderAttributeNames(final Class rendererClass) {
        for (Class clazz = rendererClass; !clazz.equals(Object.class); clazz = clazz
                .getSuperclass()) {
            final String[] names = getRenderAttributeNames0(clazz);
            if (names != null) {
                return names;
            }
        }
        return new String[0];
    }

    private String[] getRenderAttributeNames0(Class rendererClass) {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(rendererClass);
        if (!(beanDesc.hasField(JsfConstants.COMPONENT_FAMILY) && beanDesc
                .hasField(JsfConstants.RENDERER_TYPE))) {
            return null;
        }
        final Field family = beanDesc.getField(JsfConstants.COMPONENT_FAMILY);
        final String componentFamily = (String) FieldUtil.get(family, null);
        final Field type = beanDesc.getField(JsfConstants.RENDERER_TYPE);
        final String rendererType = (String) FieldUtil.get(type, null);
        final String[] names = renderAttributes.getAttributeNames(
                componentFamily, rendererType);
        return names;
    }

    protected void renderAttributes(Map attributes, ResponseWriter writer)
            throws IOException {
        for (final Iterator it = attributes.entrySet().iterator(); it.hasNext();) {
            final Map.Entry entry = (Entry) it.next();
            final String key = (String) entry.getKey();
            if (isRenderAttributeName(key)) {
                RendererUtil.renderAttribute(writer, key, entry.getValue());
            }
        }
    }

    private boolean isRenderAttributeName(final String key) {
        return -1 == key.indexOf('.');
    }

    public void setComponentIdLookupStrategy(
            ComponentIdLookupStrategy idLookupStartegy) {
        this.idLookupStartegy = idLookupStartegy;
    }

    public ComponentIdLookupStrategy getComponentIdLookupStrategy() {
        return idLookupStartegy;
    }

    protected boolean containsAttributeForRender(UIComponent component) {
        if (RendererUtil.shouldRenderIdAttribute(component)) {
            return true;
        }
        final String[] names = getRenderAttributeNames(getClass());
        if (RendererUtil.containsAttributesForRender(component, names)) {
            return true;
        }
        return containsAttributeForRender(component.getAttributes());
    }

    protected boolean containsAttributeForRender(Map attributes) {
        for (final Iterator it = attributes.entrySet().iterator(); it.hasNext();) {
            final Map.Entry entry = (Entry) it.next();
            final String key = (String) entry.getKey();
            if (isRenderAttributeName(key)) {
                final Object value = entry.getValue();
                if (RendererUtil.shouldRenderAttribute(key, value)) {
                    return true;
                }
            }
        }
        return false;
    }

    // checkbox, radio
    protected void renderCheckedAttribute(ResponseWriter writer)
            throws IOException {
        RendererUtil.renderAttribute(writer, JsfConstants.CHECKED_ATTR,
                JsfConstants.CHECKED_ATTR);
    }

    protected void renderDisabledAttribute(ResponseWriter writer)
            throws IOException {
        RendererUtil.renderAttribute(writer, JsfConstants.DISABLED_ATTR,
                JsfConstants.DISABLED_ATTR);
    }

    // select/option
    protected void renderSelectedAttribute(ResponseWriter writer)
            throws IOException {
        RendererUtil.renderAttribute(writer, JsfConstants.SELECTED_ATTR,
                JsfConstants.SELECTED_ATTR);
    }

    public void setRenderAttributes(RenderAttributes renderAttributes) {
        this.renderAttributes = renderAttributes;
    }

}
