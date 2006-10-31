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
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;

import org.seasar.teeda.extension.component.TUISelectItems;
import org.seasar.teeda.extension.util.AdjustValueHolderUtil;

/**
 * @author shot
 */
public class THtmlSelectOneMenu extends HtmlSelectOneMenu {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlSelectOneMenu";

    public void validate(FacesContext context) {
        super.validate(context);
        final String id = AdjustValueHolderUtil.getAdjustedValue(this.getId());
        final String saveId = id + "Save";
        final UIComponent parent = getParent();
        Object value = null;
        for (Iterator children = parent.getChildren().iterator(); children
                .hasNext();) {
            UIComponent child = (UIComponent) children.next();
            String childId = child.getId();
            if (saveId.equals(childId) && child instanceof THtmlInputHidden) {
                value = ((THtmlInputHidden) child).getValue();
                break;
            }
        }
        if (value != null) {
            TUISelectItems items = null;
            for (Iterator itr = getChildren().iterator(); itr.hasNext();) {
                final Object o = itr.next();
                if (o instanceof TUISelectItems) {
                    items = (TUISelectItems) o;
                    break;
                }
            }
            if (value.getClass().isArray() || value instanceof List) {
                if (items == null) {
                    items = new TUISelectItems();
                }
                items.setValue(value);
                this.getChildren().add(items);
            }
        }
    }

}
