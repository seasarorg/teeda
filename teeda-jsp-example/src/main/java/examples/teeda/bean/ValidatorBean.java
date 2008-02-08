/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package examples.teeda.bean;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.UIComponentUtil;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.ValidatorException;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 */
public class ValidatorBean {

    private String name_;

    public String getName() {
        return name_;
    }

    public void setName(String name) {
        name_ = name;
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws FacesException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);

        if (value == null) {
            return;
        }

        int length = value.toString().length();
        if (length < 2) {
            Object[] args = { new Integer(2),
                    UIComponentUtil.getLabel(component) };
            throw new ValidatorException(FacesMessageUtil.getMessage(context,
                    LengthValidator.MINIMUM_MESSAGE_ID, args));
        }
    }

}
