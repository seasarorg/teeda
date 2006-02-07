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
package javax.faces.model;

import java.io.Serializable;

import javax.faces.internal.AssertionUtil;

/**
 * @author shot
 * @author manhole
 */
public class SelectItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object value_ = null;

    private String label_ = null;

    private String description_ = null;

    private boolean disabled_ = false;

    public SelectItem() {
    }

    public SelectItem(Object value) {
        AssertionUtil.assertNotNull("value", value);
        value_ = value;
    }

    public SelectItem(Object value, String label) {
        this(value, label, null);
    }

    public SelectItem(Object value, String label, String description) {
        this(value, label, description, false);
    }

    public SelectItem(Object value, String label, String description,
            boolean disabled) {
        AssertionUtil.assertNotNull("value", value);
        AssertionUtil.assertNotNull("label", label);
        value_ = value;
        label_ = label;
        description_ = description;
        disabled_ = disabled;
    }

    public String getDescription() {
        return description_;
    }

    public void setDescription(String description) {
        description_ = description;
    }

    public boolean isDisabled() {
        return disabled_;
    }

    public void setDisabled(boolean disabled) {
        disabled_ = disabled;
    }

    public String getLabel() {
        return label_;
    }

    public void setLabel(String label) {
        AssertionUtil.assertNotNull("label", label);
        label_ = label;
    }

    public Object getValue() {
        return value_;
    }

    public void setValue(Object value) {
        AssertionUtil.assertNotNull("value", value);
        value_ = value;
    }

}
