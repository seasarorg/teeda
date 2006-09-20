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

import javax.faces.application.FacesMessage;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.model.SelectItem;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author higa
 * @author shot
 */
public class TUISelectItems extends UISelectItems {

    private static final String NULL_LABEL_MESSAGE_CODE = "org.seasar.teeda.extension.component.TSelect.NULL_LABEL";

    private String itemLabel = "label";

    private String itemValue = "value";

    private boolean required = false;

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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Object getValue() {
        Object value = super.getValue();
        if (value instanceof SelectItem[] || value instanceof SelectItem) {
            return value;
        }
        List list = new ArrayList();
        if (!required) {
            SelectItem si = new SelectItem();
            si.setValue("");
            FacesMessage mes = FacesMessageUtil.getMessage(FacesContext
                    .getCurrentInstance(), NULL_LABEL_MESSAGE_CODE, null);
            si.setLabel(mes.getSummary());
            list.add(si);
        }
        if (value instanceof Collection) {
            for (Iterator it = ((Collection) value).iterator(); it.hasNext();) {
                Object item = it.next();
                if (item instanceof SelectItem) {
                    list.add(item);
                } else if (item instanceof Map) {
                    Map map = (Map) item;
                    SelectItem si = new SelectItem();
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
        return list;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[4];
        values[0] = super.saveState(context);
        values[1] = itemValue;
        values[2] = itemLabel;
        values[3] = Boolean.valueOf(required);
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        itemValue = (String) values[1];
        itemLabel = (String) values[2];
        required = ((Boolean) values[3]).booleanValue();
    }
}