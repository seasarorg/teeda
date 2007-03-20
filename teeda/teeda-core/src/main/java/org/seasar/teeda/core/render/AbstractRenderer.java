/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.render;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.internal.IgnoreAttribute;
import javax.faces.internal.UIComponentUtil;
import javax.faces.render.Renderer;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ArrayUtil;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.LoopIterator;
import org.seasar.teeda.core.util.RenderedComponentIterator;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public abstract class AbstractRenderer extends Renderer {

    private ComponentIdLookupStrategy idLookupStartegy;

    public AbstractRenderer() {
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

    protected void renderRemainAttributes(final UIComponent component,
            final ResponseWriter writer, final IgnoreAttribute ignore)
            throws IOException {
        final Map map = getAllAttributesAndProperties(component, ignore);
        renderAttributes(map, writer);
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

    protected Map getAllAttributesAndProperties(final UIComponent component,
            final IgnoreAttribute ignore) {
        final Map map = new HashMap(64);
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(component
                .getClass());
        final Object[] attributeNames = ignore.getAttributeNames();
        for (int i = 0; i < beanDesc.getPropertyDescSize(); i++) {
            final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
            final String propertyName = propertyDesc.getPropertyName();
            if (ArrayUtil.contains(attributeNames, propertyName)) {
                continue;
            }
            if (propertyDesc.hasReadMethod()) {
                final Object value = propertyDesc.getValue(component);
                map.put(propertyName, value);
            }
        }
        for (Iterator itr = component.getAttributes().entrySet().iterator(); itr
                .hasNext();) {
            Map.Entry entry = (Entry) itr.next();
            final String name = (String) entry.getKey();
            if (!isRenderAttributeName(name)) {
                continue;
            }
            if (ArrayUtil.contains(attributeNames, name)) {
                continue;
            }
            map.put(name, entry.getValue());
        }
        return map;
    }

    /*
     * "."を含む名称は、FWなどが内部的に使っていることが多いため、
     * Teedaとしては画面に出力しないことにする。
     */
    protected boolean isRenderAttributeName(final String key) {
        return -1 == key.indexOf('.');
    }

    public void setComponentIdLookupStrategy(
            ComponentIdLookupStrategy idLookupStartegy) {
        this.idLookupStartegy = idLookupStartegy;
    }

    public ComponentIdLookupStrategy getComponentIdLookupStrategy() {
        return idLookupStartegy;
    }

    protected boolean containsAttributeForRender(final UIComponent component,
            final IgnoreAttribute ignore) {
        if (RendererUtil.shouldRenderIdAttribute(component)) {
            return true;
        }
        final Map map = getAllAttributesAndProperties(component, ignore);
        final Set keys = map.keySet();
        final String[] names = new String[keys.size()];
        keys.toArray(names);
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

    protected void renderJavaScriptElement(final ResponseWriter writer,
            final String scriptBody) throws IOException {
        if (StringUtil.isBlank(scriptBody)) {
            return;
        }
        writer.write(JsfConstants.LINE_SP);
        writer.startElement(JsfConstants.SCRIPT_ELEM, null);
        writer.writeAttribute(JsfConstants.LANGUAGE_ATTR,
                JsfConstants.JAVASCRIPT_VALUE, null);
        writer.writeAttribute(JsfConstants.TYPE_ATTR,
                JsfConstants.TEXT_JAVASCRIPT_VALUE, null);
        writer.write(JsfConstants.LINE_SP);
        writer.write("<!--");
        writer.write(JsfConstants.LINE_SP);
        writer.write(scriptBody);
        writer.write(JsfConstants.LINE_SP);
        writer.write("//-->");
        writer.write(JsfConstants.LINE_SP);
        writer.endElement(JsfConstants.SCRIPT_ELEM);
    }

    protected void renderIncludeJavaScript(final ResponseWriter writer,
            final String path) throws IOException {
        if (StringUtil.isBlank(path)) {
            return;
        }
        writer.startElement(JsfConstants.SCRIPT_ELEM, null);
        writer.writeAttribute(JsfConstants.LANGUAGE_ATTR,
                JsfConstants.JAVASCRIPT_VALUE, null);
        writer.writeAttribute(JsfConstants.TYPE_ATTR,
                JsfConstants.TEXT_JAVASCRIPT_VALUE, null);
        writer.writeAttribute(JsfConstants.SRC_ATTR, path, null);
        writer.endElement(JsfConstants.SCRIPT_ELEM);
    }

    protected void renderStyleSheet(final ResponseWriter writer,
            final String path) throws IOException {
        if (StringUtil.isBlank(path)) {
            return;
        }
        writer.startElement(JsfConstants.LINK_ELEM, null);
        writer.writeAttribute(JsfConstants.TYPE_ATTR,
                JsfConstants.TEXT_CSS_VALUE, null);
        writer.writeAttribute(JsfConstants.REL_ATTR,
                JsfConstants.STYLESHEET_VALUE, null);
        writer.writeAttribute(JsfConstants.HREF_ATTR, path, null);
        writer.endElement(JsfConstants.LINK_ELEM);
    }
}
