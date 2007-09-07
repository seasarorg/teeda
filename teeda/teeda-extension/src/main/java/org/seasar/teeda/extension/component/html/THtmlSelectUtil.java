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
package org.seasar.teeda.extension.component.html;

import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.internal.SelectItemsIterator;
import javax.faces.model.SelectItem;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.extension.component.TUISelectItems;
import org.seasar.teeda.extension.util.AdjustValueHolderUtil;

/**
 * @author manhole
 * @author shot
 */
public class THtmlSelectUtil {

    public static void takeOverLabel(final FacesContext context,
            final UISelectOne select, Object page, final String labelName) {
        if (select == null || page == null || labelName == null) {
            return;
        }
        final Object selected = select.getValue();
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(page.getClass());
        if (!beanDesc.hasPropertyDesc(labelName) || selected == null) {
            return;
        }
        final PropertyDesc labelPd = beanDesc.getPropertyDesc(labelName);
        for (final SelectItemsIterator it = new SelectItemsIterator(select); it
                .hasNext();) {
            final SelectItem item = (SelectItem) it.next();
            final Object v = item.getValue();
            if (v == null) {
                continue;
            }
            if (v.toString().equals(selected.toString())) {
                final String l = item.getLabel();
                if (labelPd.isWritable()) {
                    labelPd.setValue(page, l);
                }
                break;
            }
        }
    }

    public static void validate(final UIComponent component) {
        final Object value = findSavedValue(component);
        if (value == null) {
            return;
        }
        if (value.getClass().isArray() || (value instanceof List)) {
            final TUISelectItems items = getItems(component);
            if (items == null) {
                /*
                 * 例外の方が良い?
                 * TagでTUISelectItemsをぶら下げているのだし。
                 */
                return;
            }
            items.setValue(value);
        }
    }

    private static TUISelectItems getItems(final UIComponent component) {
        TUISelectItems items = null;
        for (final Iterator it = component.getChildren().iterator(); it
                .hasNext();) {
            final Object o = it.next();
            if (o instanceof TUISelectItems) {
                items = (TUISelectItems) o;
                break;
            }
        }
        return items;
    }

    private static Object findSavedValue(final UIComponent component) {
        final String id = AdjustValueHolderUtil.getAdjustedValue(component
                .getId());
        final String saveId = id + "Save";
        final UIComponent parent = component.getParent();
        for (final Iterator it = parent.getChildren().iterator(); it.hasNext();) {
            final UIComponent brotherOrChild = (UIComponent) it.next();
            final String childId = brotherOrChild.getId();
            if (saveId.equals(childId)
                    && (brotherOrChild instanceof THtmlItemsSaveHidden)) {
                final Object found = ((THtmlItemsSaveHidden) brotherOrChild)
                        .getValue();
                return found;
            }
        }
        return null;
    }

}
