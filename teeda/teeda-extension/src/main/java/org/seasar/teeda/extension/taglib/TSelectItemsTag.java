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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;
import javax.faces.el.ReferenceSyntaxException;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.core.SelectItemsTag;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.extension.component.TUISelectItems;

/**
 * @author shot
 */
public class TSelectItemsTag extends SelectItemsTag {

    public String getComponentType() {
        return TUISelectItems.COMPONENT_TYPE;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        TUISelectItems items = (TUISelectItems) component;
        String value = getValue();
        if (!isValueReference(value)) {
            throw new ReferenceSyntaxException();
        }
        BindingUtil.setValueBinding(items, JsfConstants.VALUE_ATTR, value);
    }
}
