/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author Satoshi Kimura
 * @author shot
 * @author manhole
 */
public class ValidatorChain implements Validator, StateHolder {

    //TODO testing
    private boolean transientValue = false;

    private List validators = new LinkedList();

    public int getValidatorSize() {
        return validators.size();
    }

    public Validator getValidator(int index) {
        return (Validator) validators.get(index);
    }

    public void add(Validator validator) {
        validators.add(validator);
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        for (Iterator iterator = validators.iterator(); iterator.hasNext();) {
            Validator validator = (Validator) iterator.next();
            validator.validate(context, component, value);
        }
    }

    public Object saveState(FacesContext context) {
        return UIComponentBase.saveAttachedState(context, validators);
    }

    public void restoreState(FacesContext context, Object state) {
        validators = (List) UIComponentBase
                .restoreAttachedState(context, state);
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }
}
