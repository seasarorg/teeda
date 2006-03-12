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

import java.io.Serializable;

/**
 * @author shot
 * @author manhole
 * 
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class SavedState implements Serializable {

    private static final long serialVersionUID = 3688791349478568242L;

    private Object localValue_ = null;

    private Object submittedValue_ = null;

    private boolean valid_ = true;

    private boolean localValueSet_ = false;

    public SavedState() {
    }

    public Object getLocalValue() {
        return localValue_;
    }

    public void setLocalValue(Object localValue) {
        localValue_ = localValue;
    }

    public boolean isLocalValueSet() {
        return localValueSet_;
    }

    public void setLocalValueSet(boolean localValueSet) {
        localValueSet_ = localValueSet;
    }

    public Object getSubmittedValue() {
        return submittedValue_;
    }

    public void setSubmittedValue(Object submittedValue) {
        submittedValue_ = submittedValue;
    }

    public boolean isValid() {
        return valid_;
    }

    public void setValid(boolean valid) {
        valid_ = valid;
    }

}
