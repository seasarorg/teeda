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
package org.seasar.teeda.extension.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.UIComponentUtil;
import javax.faces.validator.ValidatorException;

/**
 * @author higa
 * @author shot
 */
public class TGreaterEqualValidator extends AbstractCompareValidator {

    public static final String GE_MESSAGE_ID = "org.seasar.teeda.extension.validator.TGreaterEqualValidator.GE";

    protected void doValidate(FacesContext context, UIComponent component,
            Object value, UIComponent targetComponent, Object targetValue)
            throws ValidatorException {
        if (targetValue == null) {
            return;
        }
        if (!(value instanceof Comparable)
                || ((Comparable) value).compareTo(targetValue) < 0) {

            Object[] args = { UIComponentUtil.getLabel(targetComponent),
                    UIComponentUtil.getLabel(component) };
            FacesMessage message = FacesMessageUtil.getMessage(context,
                    GE_MESSAGE_ID, args);
            throw new ValidatorException(message, GE_MESSAGE_ID, args);
        }
    }
}
