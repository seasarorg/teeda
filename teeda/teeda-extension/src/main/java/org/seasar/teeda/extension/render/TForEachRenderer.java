/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.render;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.core.util.ForEachContext;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.TForEach;
import org.seasar.teeda.extension.util.AdjustValueHolderUtil;
import org.seasar.teeda.extension.util.TeedaExtensionConfiguration;

/**
 * @author higa
 * @author manhole
 */
public class TForEachRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.ForEach";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.ForEach";

    public void encodeBegin(final FacesContext context,
            final UIComponent component) throws IOException {
        super.encodeBegin(context, component);
        if (!isRendered(context, component)) {
            return;
        }
        final TForEach forEach = (TForEach) component;
        if (forEach.isOmittag()) {
            return;
        }
        final String tagName = forEach.getTagName();
        final ResponseWriter writer = context.getResponseWriter();
        writer.startElement(tagName, component);
        RendererUtil.renderIdAttributeIfNecessary(writer, component,
                getIdForRender(context, forEach));
        renderAttributes(writer, forEach);
    }

    public void encodeChildren(final FacesContext context,
            final UIComponent component) throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        final TForEach forEach = (TForEach) component;
        final ForEachContext foreachContext = ForEachContext.getContext();
        foreachContext.begin();
        try {
            encodeForEachChildren(context, forEach);
        } finally {
            foreachContext.end();
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeBegin(context, component);
        if (!isRendered(context, component)) {
            return;
        }
        final TForEach forEach = (TForEach) component;
        if (forEach.isOmittag()) {
            return;
        }
        final String tagName = forEach.getTagName();
        final ResponseWriter writer = context.getResponseWriter();
        writer.endElement(tagName);
    }

    private void encodeForEachChildren(final FacesContext context,
            final TForEach forEach) throws IOException {
        final Object page = forEach.getPage(context);

        final BeanDesc pageBeanDesc = BeanDescFactory.getBeanDesc(page
                .getClass());
        final Object[] items = forEach.getItems(context);
        final int rowSize = items.length;

        for (int i = 0; i < rowSize; ++i) {
            final Object item = items[i];
            if (item == null) {
                continue;
            }
            final Integer savedIndex = forEach.bindRowIndex(context,
                    new Integer(i));
            final Map savedValues = forEach
                    .itemToPage(pageBeanDesc, page, item);
            forEach.enterRow(context, i, forEach);

            super.encodeChildren(context, forEach);

            forEach.leaveRow(context, forEach);
            forEach.pageToItem(page, pageBeanDesc, item, BeanDescFactory
                    .getBeanDesc(item.getClass()), savedValues);
            forEach.bindRowIndex(context, savedIndex);
        }
    }

    protected boolean isRendered(final FacesContext context,
            final UIComponent component) {
        if (!component.isRendered()) {
            return false;
        }
        final TForEach forEach = (TForEach) component;
        if (forEach.isOmittag()) {
            return false;
        }
        if (!TeedaExtensionConfiguration.getInstance().outputForEachIfEmptyItems) {
            final Object[] items = forEach.getItems(context);
            if (items == null || items.length == 0) {
                return false;
            }
        }
        return true;
    }

    protected void renderAttributes(ResponseWriter writer, TForEach component)
            throws IOException {
        Map attrs = component.getAttributes();
        for (Iterator i = attrs.keySet().iterator(); i.hasNext();) {
            String attrName = (String) i.next();
            if (attrName.indexOf('.') > 0) {
                continue;
            }
            Object value = component.getAttributes().get(attrName);
            RendererUtil.renderAttribute(writer, attrName, value, attrName);
        }
        String[] bindingPropertyNames = component.getBindingPropertyNames();
        for (int i = 0; i < bindingPropertyNames.length; ++i) {
            String name = bindingPropertyNames[i];
            Object value = BindingUtil.getBindingValue(component, name);
            RendererUtil.renderAttribute(writer, name, value, name);
        }
    }

    public void decode(final FacesContext context, final UIComponent component) {
        assertNotNull(context, component);
        decodeAllRows(context, (TForEach) component, component);
    }

    protected void decodeAllRows(final FacesContext context,
            final TForEach forEach, final UIComponent base) {
        forEach.setRowIndex(TForEach.INITIAL_ROW_INDEX);
        final int rowSize = getRowSize(context, forEach);
        forEach.setRowSize(rowSize);
        for (int i = 0; i < rowSize; i++) {
            final Integer savedIndex = forEach.bindRowIndex(context,
                    new Integer(i));
            forEach.enterRow(context, i, base);

            decodeChildren(context, base);

            forEach.leaveRow(context, base);
            forEach.bindRowIndex(context, savedIndex);
        }
    }

    private int getRowSize(final FacesContext context, final TForEach forEach) {
        final Map paramMap = context.getExternalContext()
                .getRequestParameterMap();
        final Map reqParam = AdjustValueHolderUtil.adjustParamMap(paramMap);
        final String clientId = AdjustValueHolderUtil.getAdjustedValue(forEach
                .getClientId(context));
        final String namingPrefix = clientId + NamingContainer.SEPARATOR_CHAR;
        int rowSize = -1;
        for (final Iterator it = reqParam.keySet().iterator(); it.hasNext();) {
            final String name = (String) it.next();
            if (name.startsWith(namingPrefix)) {
                final int pos = name.indexOf(NamingContainer.SEPARATOR_CHAR,
                        namingPrefix.length());
                if (-1 < pos) {
                    final String num = name.substring(namingPrefix.length(),
                            pos);
                    try {
                        final int index = Integer.parseInt(num);
                        rowSize = Math.max(rowSize, index);
                    } catch (final NumberFormatException e) {
                        // TODO
                        e.printStackTrace();
                    }
                }
            }
        }
        rowSize++;
        return rowSize;
    }

    private void decodeChildren(final FacesContext context,
            final UIComponent component) {
        for (final Iterator it = component.getChildren().iterator(); it
                .hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            child.processDecodes(context);
        }
    }

    public boolean getRendersChildren() {
        return true;
    }

}
