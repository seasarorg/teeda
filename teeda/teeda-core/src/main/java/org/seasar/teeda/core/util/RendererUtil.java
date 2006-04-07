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
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;
import javax.faces.internal.FacesMessageUtils;
import javax.faces.internal.UIDefaultAttribute;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

import org.seasar.framework.log.Logger;
import org.seasar.framework.util.IntegerConversionUtil;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author manhole
 */
public class RendererUtil {

    private static Logger logger = Logger.getLogger(RendererUtil.class);

    public static boolean containsAttributesForRender(UIComponent component,
            String[] attributeNames) {
        Map attributes = component.getAttributes();
        for (int i = 0, len = attributeNames.length; i < len; i++) {
            String attributeName = attributeNames[i];
            /*
             * don't use UIComponent#containsKey method.
             * 
             * because when attributeName matches a property of this
             * UIComponent, containsKey returns false. See
             * UIComponent#getAttributes API document.
             */
            Object value = attributes.get(attributeName);
            if (shouldRenderAttribute(attributeName, value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean renderAttributes(ResponseWriter writer,
            UIComponent component, String[] attributeNames) throws IOException {

        boolean somethingDone = false;
        for (int i = 0, len = attributeNames.length; i < len; i++) {
            String attrName = attributeNames[i];
            if (renderAttribute(writer, component, attrName)) {
                somethingDone = true;
            }
        }
        return somethingDone;
    }

    static boolean renderAttribute(ResponseWriter writer,
            UIComponent component, String attributeName) throws IOException {

        Object value = component.getAttributes().get(attributeName);
        return renderAttribute(writer, attributeName, value, attributeName);
    }

    public static void renderAttribute(ResponseWriter writer,
            String attributeName, Object value) throws IOException {
        renderAttribute(writer, attributeName, value, attributeName);
    }

    public static boolean renderAttribute(ResponseWriter writer,
            String attributeName, Object value, String propertyName)
            throws IOException {
        if (!shouldRenderAttribute(attributeName, value)) {
            return false;
        }
        attributeName = toHtmlAttributeName(attributeName);
        writer.writeAttribute(attributeName, value, propertyName);
        return true;
    }

    private static String toHtmlAttributeName(String attributeName) {
        if (attributeName.equalsIgnoreCase(JsfConstants.STYLE_CLASS_ATTR)) {
            return JsfConstants.CLASS_ATTR;
        } else if (attributeName
                .equalsIgnoreCase(JsfConstants.ACCEPTCHARSET_ATTR)) {
            return JsfConstants.ACCEPT_CHARSET_ATTR;
        } else {
            return attributeName;
        }
    }

    static boolean shouldRenderAttribute(String attributeName, Object value) {
        if (isDefaultAttributeValue(value)) {
            return false;
        }
        if (JsfConstants.COLSPAN_ATTR.equals(attributeName)) {
            Integer integerValue = IntegerConversionUtil.toInteger(value);
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

    public static boolean isDefaultAttributeValue(Object value) {
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

    public static boolean shouldRenderIdAttribute(UIComponent component) {
        String id = component.getId();
        return shouldRenderIdAttribute(id);
    }

    private static boolean shouldRenderIdAttribute(String id) {
        if (id != null && !id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            return true;
        }
        return false;
    }

    public static void renderIdAttributeIfNecessary(ResponseWriter writer,
            UIComponent component, String idValue) throws IOException {
        if (RendererUtil.shouldRenderIdAttribute(component)) {
            RendererUtil.renderAttribute(writer, JsfConstants.ID_ATTR, idValue);
        }
    }

    // checkbox, radio
    public static void renderCheckedAttribute(ResponseWriter writer)
            throws IOException {
        renderAttribute(writer, JsfConstants.CHECKED_ATTR,
                JsfConstants.CHECKED_ATTR);
    }

    public static void renderDisabledAttribute(ResponseWriter writer)
            throws IOException {
        renderAttribute(writer, JsfConstants.DISABLED_ATTR,
                JsfConstants.DISABLED_ATTR);
    }

    // select/option
    public static void renderSelectedAttribute(ResponseWriter writer)
            throws IOException {
        renderAttribute(writer, JsfConstants.SELECTED_ATTR,
                JsfConstants.SELECTED_ATTR);
    }

    public static Object getConvertedValue(FacesContext context,
            UIInput component, Object submittedValue) {
        try {
            Renderer renderer = getRenderer(context, component);
            if (renderer != null) {
                return renderer.getConvertedValue(context, component,
                        submittedValue);
            } else if (submittedValue instanceof String) {
                return getConvertedUIOutputValue(context, component,
                        submittedValue);
            }
        } catch (ConverterException e) {
            FacesMessage facesMessage = e.getFacesMessage();
            if (facesMessage != null) {
                context
                        .addMessage(component.getClientId(context),
                                facesMessage);
            } else {
                Object[] args = new Object[] { UIComponentUtil
                        .getLabel(component) };
                context.addMessage(component.getClientId(context),
                        FacesMessageUtils.getMessage(context,
                                UIInput.CONVERSION_MESSAGE_ID, args));
            }
            component.setValid(false);
        }
        return submittedValue;
    }

    public static Renderer getRenderer(FacesContext context,
            UIComponent component) {

        String rendererType = component.getRendererType();
        if (rendererType == null) {
            return null;
        }
        String renderKitId = context.getViewRoot().getRenderKitId();
        RenderKitFactory rkf = (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        RenderKit renderKit = rkf.getRenderKit(context, renderKitId);
        return renderKit.getRenderer(component.getFamily(), rendererType);
    }

    public static Object getConvertedUIOutputValue(FacesContext context,
            UIOutput output, Object submittedValue) throws ConverterException {
        if (submittedValue == null) {
            return null;
        }
        Converter converter = findConverterForSubmittedValue(context, output);
        if (converter == null) {
            return submittedValue;
        }
        return converter.getAsObject(context, output, (String) submittedValue);
    }

    public static Object getConvertedUIOutputValues(FacesContext context,
            UIOutput output, Object submittedValue) {
        if (submittedValue == null) {
            return null;
        }
        Converter converter = findConverterForSubmittedValue(context, output);
        if (converter == null) {
            return submittedValue;
        }
        int length = Array.getLength(submittedValue);
        Class valueType = getValueType(context, output);
        Object ret = Array.newInstance(valueType, length);
        for (int i = 0; i < length; ++i) {
            Object target = Array.get(submittedValue, i);
            String value = (String) target;
            Object o = converter.getAsObject(context, output, value);
            ArrayUtil.setArrayValue(ret, valueType, o, i);
        }
        return ret;
    }

    static Converter findConverterForSubmittedValue(FacesContext context,
            UIOutput component) {

        Converter converter = component.getConverter();
        if (converter != null) {
            return converter;
        }
        Class valueType = getValueType(context, component);
        if (valueType == null) {
            return null;
        }
        if (String.class.equals(valueType) || Object.class.equals(valueType)) {
            return null;
        }
        try {
            return context.getApplication().createConverter(valueType);
        } catch (FacesException ex) {
            logger.log(ex);
            return null;
        }
    }

    static Class getValueType(FacesContext context, UIOutput component) {
        ValueBinding vb = component.getValueBinding("value");
        if (vb == null) {
            return null;
        }
        Class valueType = vb.getType(context);
        if (valueType == null) {
            return null;
        }
        if (valueType.isArray()) {
            return valueType.getComponentType();
        } else {
            return valueType;
        }
    }

}
