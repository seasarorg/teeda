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
package org.seasar.teeda.extension.component;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author higa
 * 
 */
public class TForEach extends UIComponentBase {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.ForEach";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.ForEach";

    private static final String INDEX_SUFFIX = "Index";

    private String pageName;

    private String itemsName;

    public TForEach() {
    }

    public String getItemsName() {
        return itemsName;
    }

    public void setItemsName(String itemsName) {
        if (itemsName != null
                && !itemsName.endsWith(ExtensionConstants.ITEMS_SUFFIX)) {
            throw new IllegalArgumentException(itemsName);
        }
        this.itemsName = itemsName;
    }

    public String getItemName() {
        if (itemsName == null) {
            return null;
        }
        return itemsName.substring(0, itemsName.length()
                - ExtensionConstants.ITEMS_SUFFIX.length());
    }

    public String getIndexName() {
        String itemName = getItemName();
        if (itemName == null) {
            return null;
        }
        return itemName + INDEX_SUFFIX;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    /**
     * @see javax.faces.component.UIComponent#getFamily()
     */
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[3];
        values[0] = super.saveState(context);
        values[1] = pageName;
        values[2] = itemsName;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        pageName = (String) values[1];
        itemsName = (String) values[2];
    }
}