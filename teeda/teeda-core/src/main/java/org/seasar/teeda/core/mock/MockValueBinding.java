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
package org.seasar.teeda.core.mock;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ValueBinding;

public class MockValueBinding extends ValueBinding implements StateHolder {

    private Object obj_ = null;

    private boolean transientValue_ = false;

    private Class type_;

    private String ref_;

    public MockValueBinding() {
    }

    public MockValueBinding(String ref) {
        ref_ = ref;
    }

    public Object getValue(FacesContext context) throws EvaluationException,
            PropertyNotFoundException {
        return obj_;
    }

    public void setValue(FacesContext context, Object obj)
            throws EvaluationException, PropertyNotFoundException {
        obj_ = obj;
    }

    public boolean isReadOnly(FacesContext context) throws EvaluationException,
            PropertyNotFoundException {
        return false;
    }

    public Class getType(FacesContext context) throws EvaluationException,
            PropertyNotFoundException {
        return type_;
    }

    public void setType(Class type) {
        type_ = type;
    }

    public boolean isTransient() {
        return transientValue_;
    }

    public void setTransient(boolean transientValue) {
        transientValue_ = transientValue;
    }

    public Object saveState(FacesContext context) {
        return null;
    }

    public void restoreState(FacesContext context, Object state) {
    }

}
