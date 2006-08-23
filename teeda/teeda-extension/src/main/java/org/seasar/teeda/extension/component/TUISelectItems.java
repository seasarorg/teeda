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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author higa
 * @author shot
 */
public class TUISelectItems extends UISelectItems {

    private String itemLabel = "label";

    private String itemValue = "value";

    private String nullLabel = "nullLabel";

    public String getItemLabel() {
        return itemLabel;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getNullLabel() {
        return nullLabel;
    }

    public void setNullLabel(String nullLabel) {
        this.nullLabel = nullLabel;
    }

    public Object getValue() {
        Object value = super.getValue();
        if (value instanceof SelectItem[] || value instanceof SelectItem) {
            return value;
        }
        List list = new ArrayList();
        if (value instanceof Collection) {
            for (Iterator it = ((Collection) value).iterator(); it.hasNext();) {
                Object item = it.next();
                if (item instanceof SelectItem) {
                    list.add(item);
                } else if (item instanceof Map) {
                    Map map = (Map) item;
                    SelectItem si = new SelectItem();
                    String nullLabelValue = (String) map.get(nullLabel);
                    if (nullLabelValue != null) {
                        si.setLabel(nullLabelValue);
                        list.add(si);
                        continue;
                    }
                    Object itemValueValue = map.get(itemValue);
                    if (itemValueValue != null) {
                        si.setValue(itemValueValue);
                    }
                    Object itemLabelValue = map.get(itemLabel);
                    if (itemLabelValue == null) {
                        itemLabelValue = itemValueValue;
                    }
                    if (itemLabelValue != null) {
                        si.setLabel(itemLabelValue.toString());
                    }
                    list.add(si);
                } else {
                    SelectItem si = new SelectItem();
                    BeanDesc bd = BeanDescFactory.getBeanDesc(item.getClass());
                    if (bd.hasPropertyDesc(nullLabel)) {
                        PropertyDesc pd = bd.getPropertyDesc(nullLabel);
                        String nullLabelValue = (String) pd.getValue(item);
                        if (nullLabelValue != null) {
                            si.setLabel(nullLabelValue);
                            list.add(si);
                            continue;
                        }
                    }
                    PropertyDesc pd = bd.getPropertyDesc(itemValue);
                    Object itemValueValue = pd.getValue(item);
                    if (itemValueValue != null) {
                        si.setValue(itemValueValue);
                    }
                    Object itemLabelValue = null;
                    if (bd.hasPropertyDesc(itemLabel)) {
                        pd = bd.getPropertyDesc(itemLabel);
                        itemLabelValue = pd.getValue(item);
                    }
                    if (itemLabelValue == null) {
                        itemLabelValue = itemValueValue;
                    }
                    if (itemLabelValue != null) {
                        si.setLabel(itemLabelValue.toString());
                    }
                    list.add(si);
                }
            }
        }
        super.setValue(list);
        return list;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[4];
        values[0] = super.saveState(context);
        values[1] = itemValue;
        values[2] = itemLabel;
        values[3] = nullLabel;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        itemValue = (String) values[1];
        itemLabel = (String) values[2];
        nullLabel = (String) values[3];
    }
}