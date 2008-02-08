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
package org.seasar.teeda.core.mock;

import javax.faces.model.DataModelEvent;
import javax.faces.model.DataModelListener;

public class MockDataModelListener implements DataModelListener {

    private DataModelEvent event_ = null;

    private String name_ = null;

    public MockDataModelListener() {
    }

    public MockDataModelListener(String name) {
        name_ = name;
    }

    public void rowSelected(DataModelEvent event) {
        setDataModelEvent(event);
    }

    private void setDataModelEvent(DataModelEvent event) {
        event_ = event;
    }

    public DataModelEvent getDataModelEvent() {
        return event_;
    }

    public String toString() {
        return name_;
    }
}
