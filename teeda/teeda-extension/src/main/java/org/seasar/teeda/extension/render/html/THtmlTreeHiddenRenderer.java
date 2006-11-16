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
package org.seasar.teeda.extension.render.html;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.seasar.teeda.extension.component.TreeNode;
import org.seasar.teeda.extension.component.html.THtmlInputHidden;
import org.seasar.teeda.extension.util.ComponentHolder;

/**
 * @author shot
 */
public class THtmlTreeHiddenRenderer extends THtmlInputHiddenRenderer {

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.TreeHidden";

    protected String getValueForRender(FacesContext context,
            THtmlInputHidden htmlInputHidden) {
        final Object submittedValue = htmlInputHidden.getSubmittedValue();
        if (submittedValue != null) {
            if (submittedValue instanceof String) {
                return (String) submittedValue;
            }
            return submittedValue.toString();
        }
        final Object value = htmlInputHidden.getValue();
        if (value == null) {
            return "";
        }
        ComponentHolder holder = buildComponentHolder(value);
        if (holder == null) {
            throw new IllegalArgumentException();
        }
        return serialize(holder);
    }

    public Object getConvertedValue(final FacesContext context,
            final UIComponent component, final Object submittedValue)
            throws ConverterException {
        assertNotNull(context, component);
        String s = (String) submittedValue;
        if (s.equals("")) {
            return "";
        }
        final ComponentHolder holder = (ComponentHolder) deserialize(s);
        final String componentClassName = holder.getComponentClassName();
        if (componentClassName == null) {
            return null;
        }
        //final Class componentClass = ClassUtil.forName(componentClassName);
        final List restoredList = holder.getValue();
        return restoredList.get(0);
    }

    private static ComponentHolder buildComponentHolder(Object value) {
        if (value == null) {
            return null;
        }
        ComponentHolder holder = null;
        if (value instanceof TreeNode) {
            holder = buildTreeNodeTypeHolder((TreeNode) value);
        }
        return holder;
    }

    private static ComponentHolder buildTreeNodeTypeHolder(TreeNode node) {
        final List list = new ArrayList();
        list.add(node);
        //list.addAll(node.getChildren());
        ComponentHolder holder = new ComponentHolder();
        holder.setComponentClassName(node.getClass().getName());
        holder.setValue(list);
        return holder;
    }

}
