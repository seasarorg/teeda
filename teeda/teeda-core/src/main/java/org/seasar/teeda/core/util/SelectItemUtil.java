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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.model.SelectItem;

/**
 * @author manhole
 */
public class SelectItemUtil {

    public static List accrueSelectItemList(UIComponent component) {
        List l = new ArrayList();
        for (Iterator itChild = component.getChildren().iterator(); itChild
                .hasNext();) {
            UIComponent child = (UIComponent) itChild.next();
            if (child instanceof UISelectItem) {
                UISelectItem uiSelectItem = (UISelectItem) child;
                SelectItem selectItem1 = new SelectItem(uiSelectItem
                        .getItemValue(), uiSelectItem.getItemLabel(),
                        uiSelectItem.getItemDescription());
                SelectItem selectItem = selectItem1;
                l.add(selectItem);
            } else if (child instanceof UISelectItems) {
                UISelectItems uiSelectItems = (UISelectItems) child;
                Object value = uiSelectItems.getValue();
                if (value instanceof SelectItem) {
                    l.add((SelectItem) value);
                } else if (value instanceof SelectItem[]) {
                    // TODO in case of array contains null.
                    SelectItem[] selectItems = (SelectItem[]) value;
                    for (int i = 0; i < selectItems.length; i++) {
                        SelectItem item = selectItems[i];
                        l.add(item);
                    }
                } else if (value instanceof Collection) {
                    // TODO in case of collection contains null.
                    Collection c = (Collection) value;
                    for (Iterator it = c.iterator(); it.hasNext();) {
                        SelectItem item = (SelectItem) it.next();
                        l.add(item);
                    }
                } else if (value instanceof Map) {
                    Map m = (Map) value;
                    for (Iterator it = m.entrySet().iterator(); it.hasNext();) {
                        Map.Entry entry = (Map.Entry) it.next();
                        // TODO in case of key or value is null.
                        l.add(new SelectItem(entry.getValue().toString(), entry
                                .getKey().toString()));
                    }
                } else {
                    // TODO anything to do ?
                }
            } else {
                // TODO anything to do ?
            }
        }
        return l;
    }

}
