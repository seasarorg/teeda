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
package org.seasar.teeda.extension.render;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.extension.component.TForEach;
import org.seasar.teeda.extension.util.AdjustValueHolderUtil;

/**
 * @author higa
 * @author manhole
 */
public class TForEachRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.ForEach";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.ForEach";

    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        final TForEach forEach = (TForEach) component;
        final Object page = forEach.getPage(context);

        final BeanDesc pageBeanDesc = BeanDescFactory.getBeanDesc(page
                .getClass());
        /**
         * TEEDA-305
         */
        final Map result = extractProperties(pageBeanDesc, page);
        final Object[] items = forEach.getItems(context);
        /*
         * https://www.seasar.org/issues/browse/TEEDA-150
         * FIXME validationエラー時の対応は済んでいるので、ここでは単純に
         * itemsのサイズを採るだけでOKになった。
         */
        final int rowSize = (items != null) ? items.length : 0;
        for (int i = 0; i < rowSize; ++i) {
            forEach.enterRow(context, i);
            if (i < items.length) {
                forEach.processItem(pageBeanDesc, page, items[i], i);
            }
            super.encodeChildren(context, component);
            forEach.leaveRow(context);
        }
        putProperties(pageBeanDesc, page, result);
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        final TForEach forEach = (TForEach) component;
        final int rowSize = getRowSize(context, forEach);
        forEach.setRowSize(rowSize);
        for (int i = 0; i < rowSize; i++) {
            forEach.enterRow(context, i);
            decodeChildren(context, component);
            forEach.leaveRow(context);
        }
    }

    private int getRowSize(FacesContext context, final TForEach forEach) {
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
                    String num = name.substring(namingPrefix.length(), pos);
                    try {
                        int index = Integer.parseInt(num);
                        rowSize = Math.max(rowSize, index);
                    } catch (NumberFormatException e) {
                        // TODO
                        e.printStackTrace();
                    }
                }
            }
        }
        rowSize++;
        return rowSize;
    }

    private void decodeChildren(FacesContext context, UIComponent component) {
        for (final Iterator it = component.getChildren().iterator(); it
                .hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            child.processDecodes(context);
        }
    }

    public boolean getRendersChildren() {
        return true;
    }

    protected Map extractProperties(final BeanDesc pageBeanDesc,
            final Object page) {
        final Map result = new HashMap();
        for (int i = 0; i < pageBeanDesc.getPropertyDescSize(); i++) {
            final PropertyDesc pd = pageBeanDesc.getPropertyDesc(i);
            final String propertyName = pd.getPropertyName();
            if (propertyName.endsWith("Items") || !pd.hasReadMethod()) {
                continue;
            }
            result.put(propertyName, pd.getValue(page));
        }
        return result;
    }

    protected void putProperties(final BeanDesc pageBeanDesc,
            final Object page, final Map result) {
        for (int i = 0; i < pageBeanDesc.getPropertyDescSize(); i++) {
            final PropertyDesc pd = pageBeanDesc.getPropertyDesc(i);
            final String propertyName = pd.getPropertyName();
            if (!result.containsKey(propertyName) || !pd.hasWriteMethod()) {
                continue;
            }
            Object value = result.get(propertyName);
            pd.setValue(page, value);
        }

    }
}
