/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.ForEachContext;
import org.seasar.teeda.extension.component.TForEach;
import org.seasar.teeda.extension.util.AdjustValueHolderUtil;

/**
 * @author higa
 * @author manhole
 */
public class TForEachRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.ForEach";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.ForEach";

    private final static IgnoreAttribute attribute = new IgnoreAttribute();
    static {
        attribute.addAttributeName("tagName");
        attribute.addAttributeName("pageName");
        attribute.addAttributeName("itemsName");
        attribute.addAttributeName("itemName");
        attribute.addAttributeName("indexName");
        attribute.addAttributeName("rowIndex");
        attribute.addAttributeName("rowSize");
        attribute.addAttributeName("omittag");
    }

    public void encodeBegin(final FacesContext context,
            final UIComponent component) throws IOException {
        super.encodeBegin(context, component);
        if (!component.isRendered()) {
            return;
        }
        final TForEach forEach = (TForEach) component;
        if (forEach.isOmittag()) {
            return;
        }
        final String tagName = forEach.getTagName();
        final ResponseWriter writer = context.getResponseWriter();
        writer.startElement(tagName, component);
        renderRemainAttributes(forEach, writer, attribute);
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
        if (!component.isRendered()) {
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
        final int rowSize = (items != null) ? items.length : 0;

        /**
         * TEEDA-305(Seasar-user:7347)
         */
        Map result = Collections.EMPTY_MAP;
        if (rowSize > 0) {
            result = extractProperties(forEach, pageBeanDesc, page);
        }
        /*
         * https://www.seasar.org/issues/browse/TEEDA-150
         * FIXME validationエラー時の対応は済んでいるので、ここでは単純に
         * itemsのサイズを採るだけでOKになった。
         */
        for (int i = 0; i < rowSize; ++i) {
            forEach.enterRow(context, i, forEach);
            forEach.bindRowIndex(context, new Integer(i));
            if (i < items.length) {
                forEach.processItem(pageBeanDesc, page, items[i], i);
            }
            super.encodeChildren(context, forEach);
            forEach.leaveRow(context, forEach);
        }
        if (rowSize > 0) {
            putProperties(forEach, pageBeanDesc, page, result);
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
            forEach.enterRow(context, i, base);
            forEach.bindRowIndex(context, new Integer(i));
            decodeChildren(context, base);
            forEach.leaveRow(context, base);
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

    protected Map extractProperties(final TForEach forEach,
            final BeanDesc pageBeanDesc, final Object page) {
        final Map result = new HashMap();
        final String itemsName = forEach.getItemsName();
        final String indexName = forEach.getIndexName();
        for (int i = 0; i < pageBeanDesc.getPropertyDescSize(); i++) {
            final PropertyDesc pd = pageBeanDesc.getPropertyDesc(i);
            final String propertyName = pd.getPropertyName();
            if (propertyName.equals(itemsName) ||
                    propertyName.equals(indexName) || !pd.isReadable() ||
                    !pd.isWritable()) {
                continue;
            }
            final Object value = pd.getValue(page);
            result.put(propertyName, value);
        }
        return result;
    }

    protected void putProperties(final TForEach forEach,
            final BeanDesc pageBeanDesc, final Object page, final Map result) {
        for (int i = 0; i < pageBeanDesc.getPropertyDescSize(); i++) {
            final PropertyDesc pd = pageBeanDesc.getPropertyDesc(i);
            final String propertyName = pd.getPropertyName();
            if (!result.containsKey(propertyName) || !pd.isWritable()) {
                continue;
            }
            final Object value = result.get(propertyName);
            pd.setValue(page, value);
        }
    }
}
