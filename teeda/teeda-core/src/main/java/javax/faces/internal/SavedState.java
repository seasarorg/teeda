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

import java.io.Serializable;

import javax.faces.component.EditableValueHolder;

/**
 * @author shot
 * @author manhole
 * 
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class SavedState implements Serializable {

    private static final long serialVersionUID = 3688791349478568242L;

    private Object localValue = null;

    private Object submittedValue = null;

    private boolean valid = true;

    private boolean localValueSet = false;

    public SavedState() {
    }

    public Object getLocalValue() {
        return localValue;
    }

    public void setLocalValue(Object localValue) {
        this.localValue = localValue;
    }

    public boolean isLocalValueSet() {
        return localValueSet;
    }

    public void setLocalValueSet(boolean localValueSet) {
        this.localValueSet = localValueSet;
    }

    public Object getSubmittedValue() {
        return submittedValue;
    }

    public void setSubmittedValue(Object submittedValue) {
        this.submittedValue = submittedValue;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void save(EditableValueHolder holder) {
        setLocalValue(holder.getLocalValue());
        setLocalValueSet(holder.isLocalValueSet());
        setValid(holder.isValid());
        setSubmittedValue(holder.getSubmittedValue());
    }

    public void restore(EditableValueHolder holder) {
        holder.setValue(getLocalValue());
        holder.setLocalValueSet(isLocalValueSet());
        holder.setValid(isValid());
        holder.setSubmittedValue(getSubmittedValue());
    }

}
