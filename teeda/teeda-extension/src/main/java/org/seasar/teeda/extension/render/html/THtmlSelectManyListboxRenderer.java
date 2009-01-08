/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import java.lang.reflect.Array;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.internal.UIComponentUtil;

import org.seasar.teeda.core.render.html.HtmlSelectManyListboxRenderer;
import org.seasar.teeda.core.util.UIValueUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;
import org.seasar.teeda.extension.component.html.THtmlSelectManyListbox;

/**
 * @author shot
 */
public class THtmlSelectManyListboxRenderer extends
        HtmlSelectManyListboxRenderer {

    public static final String COMPONENT_FAMILY = THtmlSelectManyListbox.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlSelectManyListbox.DEFAULT_RENDERER_TYPE;

    protected String[] getValuesForRender(FacesContext context,
            UIComponent htmlSelectManyListbox) {
        final boolean disabled = UIComponentUtil
                .isDisabled(htmlSelectManyListbox);
        if (disabled) {
            final ValueHolder vh = (ValueHolder) htmlSelectManyListbox;
            final Object value = vh.getValue();
            if (value == null) {
                return new String[0];
            }
            final Converter converter = vh.getConverter();
            final int length = Array.getLength(value);
            final String[] values = new String[length];
            for (int i = 0; i < length; ++i) {
                values[i] = UIValueUtil.getValueAsString(context,
                        htmlSelectManyListbox, Array.get(value, i), converter);
            }
            return values;
        } else {
            return ValueHolderUtil.getValuesForRender(context,
                    htmlSelectManyListbox);
        }
    }

}
