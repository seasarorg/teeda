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

import org.seasar.teeda.extension.component.TUISelectItems;
import org.seasar.teeda.extension.util.AdjustValueHolderUtil;

/**
 * @author manhole
 */
public class THtmlSelectUtil {

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
