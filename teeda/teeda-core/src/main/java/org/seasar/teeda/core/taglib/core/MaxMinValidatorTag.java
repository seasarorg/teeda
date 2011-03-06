/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.taglib.core;

import javax.faces.webapp.ValidatorTag;

/**
 * @author yone
 */
public abstract class MaxMinValidatorTag extends ValidatorTag {

    private String minimum_ = null;

    private boolean minimumSet = false;

    private String maximum_ = null;

    private boolean maximumSet = false;

    public void setMinimum(String minimum) {
        minimumSet = true;
        this.minimum_ = minimum;
    }

    public String getMinimum() {
        return minimum_;
    }

    public void setMaximum(String maximum) {
        maximumSet = true;
        this.maximum_ = maximum;
    }

    public String getMaximum() {
        return maximum_;
    }

    public boolean isMaximumSet() {
        return maximumSet;
    }

    public void setMaximumSet(boolean maximumSet) {
        this.maximumSet = maximumSet;
    }

    public boolean isMinimumSet() {
        return minimumSet;
    }

    public void setMinimumSet(boolean minimumSet) {
        this.minimumSet = minimumSet;
    }

    public void release() {
        minimum_ = null;
        maximum_ = null;
    }
}
