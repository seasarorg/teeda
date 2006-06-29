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
package javax.faces.internal;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

/**
 * @author shot
 */
public class ValueBindingUtil {

    private ValueBindingUtil() {
    }

    public static ValueBinding createValueBinding(String ref) {
        return createValueBinding(FacesContext.getCurrentInstance(), ref);
    }

    public static ValueBinding createValueBinding(FacesContext context, String ref) {
        return context.getApplication().createValueBinding(ref);
    }

    public static Object getValue(FacesContext context, String value) {
        if (value != null && UIComponentTag.isValueReference(value)) {
            ValueBinding vb = context.getApplication()
                    .createValueBinding(value);
            return vb.getValue(context);
        }
        return null;
    }

}
