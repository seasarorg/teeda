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
package org.seasar.teeda.core.util;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.ComponentUtil_;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;
import javax.faces.internal.ConverterResource;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.RenderKitUtil;
import javax.faces.internal.UIComponentUtil;
import javax.faces.internal.UIDefaultAttribute;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;

import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ArrayUtil;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.IntegerConversionUtil;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author manhole
 */
public class RendererUtil {

    private static final Logger logger = Logger.getLogger(RendererUtil.class);

    public static boolean containsAttributesForRender(
            final UIComponent component, final String[] attributeNames) {
        final Map attributes = component.getAttributes();
        for (int i = 0, len = attributeNames.length; i < len; i++) {
            final String attributeName = attributeNames[i];
            /*
             * don't use UIComponent#containsKey method.
             *
             * because when attributeName matches a property of this
             * UIComponent, containsKey returns false. See
             * UIComponent#getAttributes API document.
             */
            final Object value = attributes.get(attributeName);
            if (shouldRenderAttribute(attributeName, value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean renderAttributes(final ResponseWriter writer,
            final UIComponent component, final String[] attributeNames)
            throws IOException {

        boolean somethingDone = false;
        for (int i = 0, len = attributeNames.length; i < len; i++) {
            final String attrName = attributeNames[i];
            if (renderAttribute(writer, component, attrName)) {
                somethingDone = true;
            }
        }
        return somethingDone;
    }

    static boolean renderAttribute(final ResponseWriter writer,
            final UIComponent component, final String attributeName)
            throws IOException {

        final Object value = component.getAttributes().get(attributeName);
        return renderAttribute(writer, attributeName, value, attributeName);
    }

    public static void renderAttribute(final ResponseWriter writer,
            final String attributeName, final Object value) throws IOException {
        renderAttribute(writer, attributeName, value, attributeName);
    }

    public static boolean renderAttribute(final ResponseWriter writer,
            String attributeName, Object value, final String propertyName)
            throws IOException {
        if (!shouldRenderAttribute(attributeName, value)) {
            return false;
        }
        attributeName = toHtmlAttributeName(attributeName);
        value = convertValue(attributeName, value);
        writer.writeAttribute(attributeName, value, propertyName);
        return true;
    }

    private static Object convertValue(final String attributeName,
            final Object value) {
        if (JsfConstants.CHECKED_ATTR.equals(attributeName)) {
            return JsfConstants.CHECKED_ATTR;
        } else if (JsfConstants.SELECTED_ATTR.equals(attributeName)) {
            return JsfConstants.SELECTED_ATTR;
        } else if (JsfConstants.DISABLED_ATTR.equals(attributeName)) {
            return JsfConstants.DISABLED_ATTR;
        }
        return value;
    }

    private static String toHtmlAttributeName(final String attributeName) {
        if (attributeName.equalsIgnoreCase(JsfConstants.STYLE_CLASS_ATTR)) {
            return JsfConstants.CLASS_ATTR;
        } else if (attributeName
                .equalsIgnoreCase(JsfConstants.ACCEPTCHARSET_ATTR)) {
            return JsfConstants.ACCEPT_CHARSET_ATTR;
        } else {
            return attributeName;
        }
    }

    public static boolean shouldRenderAttribute(final String attributeName,
            final Object value) {
        if (isDefaultAttributeValue(value)) {
            return false;
        }
        if (JsfConstants.COLSPAN_ATTR.equals(attributeName)) {
            final Integer integerValue = IntegerConversionUtil.toInteger(value);
            if (integerValue == null) {
                return false;
            }
            if (integerValue.intValue() <= 1) {
                return false;
            }
        }
        if (JsfConstants.ID_ATTR.equals(attributeName)) {
            return shouldRenderIdAttribute(value.toString());
        }
        return true;
    }

    static boolean isDefaultAttributeValue(final Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof Boolean) {
            return UIDefaultAttribute.isDefaultBoolean(((Boolean) value)
                    .booleanValue());
        }
        if (value instanceof Integer) {
            return UIDefaultAttribute
                    .isDefaultInt(((Integer) value).intValue());
        }
        return false;
    }

    public static boolean shouldRenderIdAttribute(final UIComponent component) {
        return shouldRenderIdAttribute(component.getId());
    }

    private static boolean shouldRenderIdAttribute(final String id) {
        if ((id != null) && !id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            return true;
        }
        return false;
    }

    public static void renderIdAttributeIfNecessary(
            final ResponseWriter writer, final UIComponent component,
            final String idValue) throws IOException {
        if (RendererUtil.shouldRenderIdAttribute(component)) {
            RendererUtil.renderAttribute(writer, JsfConstants.ID_ATTR, idValue);
        }
    }

    public static Object getConvertedValue(final FacesContext context,
            final UIInput component, final Object submittedValue) {
        try {
            final Renderer renderer = getRenderer(context, component);
            if (renderer != null) {
                return renderer.getConvertedValue(context, component,
                        submittedValue);
            } else if (submittedValue instanceof String) {
                return getConvertedUIOutputValue(context, component,
                        submittedValue);
            }
        } catch (final ConverterException e) {
            final FacesMessage facesMessage = e.getFacesMessage();
            if (facesMessage != null) {
                context
                        .addMessage(component.getClientId(context),
                                facesMessage);
            } else {
                final Object[] args = new Object[] { UIComponentUtil
                        .getLabel(component) };
                context.addMessage(component.getClientId(context),
                        FacesMessageUtil.getMessage(context,
                                UIInput.CONVERSION_MESSAGE_ID, args));
            }
            component.setValid(false);
        }
        return submittedValue;
    }

    static Renderer getRenderer(final FacesContext context,
            final UIComponent component) {
        final String rendererType = component.getRendererType();
        if (rendererType == null) {
            return null;
        }
        final RenderKit renderKit = RenderKitUtil.getRenderKit(context);
        return renderKit.getRenderer(component.getFamily(), rendererType);
    }

    public static Object getConvertedUIOutputValue(final FacesContext context,
            final UIOutput output, final Object submittedValue)
            throws ConverterException {
        if (submittedValue == null) {
            return null;
        }
        final Converter converter = findConverter(context, output);
        if (converter == null) {
            return submittedValue;
        }
        return converter.getAsObject(context, output, (String) submittedValue);
    }

    public static Object getConvertedUIOutputValues(final FacesContext context,
            final UIOutput output, final Object submittedValue) {
        if (submittedValue == null) {
            return null;
        }
        final Converter converter = findConverter(context, output);
        if (converter == null) {
            return submittedValue;
        }
        final int length = Array.getLength(submittedValue);
        final Class valueType = getValueType(context, output);
        final Object ret = Array.newInstance(valueType, length);
        for (int i = 0; i < length; ++i) {
            final Object target = Array.get(submittedValue, i);
            final String value = (String) target;
            final Object o = converter.getAsObject(context, output, value);
            ArrayUtil.setArrayValue(ret, valueType, o, i);
        }
        return ret;
    }

    public static Converter findConverter(final FacesContext context,
            final UIOutput component) {

        Converter converter = component.getConverter();
        if (converter != null) {
            return converter;
        }
        ValueBinding vb = component.getValueBinding("value");
        if (vb != null) {
            String expression = vb.getExpressionString();
            converter = ConverterResource.getConverter(expression);
            if (converter != null) {
                return converter;
            }
        }
        final Class valueType = getValueType(context, component);
        if (ComponentUtil_.isPerformNoConversion(valueType)) {
            return null;
        }
        try {
            return context.getApplication().createConverter(valueType);
        } catch (final FacesException ex) {
            logger.log(ex);
            return null;
        }
    }

    static Class getValueType(final FacesContext context,
            final UIOutput component) {
        final ValueBinding vb = component.getValueBinding("value");
        if (vb == null) {
            return null;
        }
        final Class valueType = vb.getType(context);
        if (valueType == null) {
            return null;
        }
        if (valueType.isArray()) {
            return valueType.getComponentType();
        } else {
            return valueType;
        }
    }

    public static void renderChild(FacesContext context, UIComponent child)
            throws IOException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("child", child);
        if (!child.isRendered()) {
            return;
        }
        child.encodeBegin(context);
        if (child.getRendersChildren()) {
            child.encodeChildren(context);
        } else {
            renderChildren(context, child);
        }
        child.encodeEnd(context);
    }

    public static void renderChildren(FacesContext context,
            UIComponent component) throws IOException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("child", component);
        if (component.getChildCount() > 0) {
            for (Iterator it = component.getChildren().iterator(); it.hasNext();) {
                UIComponent child = (UIComponent) it.next();
                renderChild(context, child);
            }
        }
    }

    public static void renderHidden(UIComponent component,
            ResponseWriter writer, String name, Object value)
            throws IOException {
        writer.startElement(JsfConstants.INPUT_ELEM, component);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE);
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR, name);
        RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR, value);
        writer.endElement(JsfConstants.INPUT_ELEM);
    }
}
