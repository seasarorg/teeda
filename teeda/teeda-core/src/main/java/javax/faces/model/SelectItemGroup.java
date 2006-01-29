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

/**
 * @author shot
 * @author manhole
 */
public class SelectItemGroup extends SelectItem {

    private static final long serialVersionUID = 1L;

    private SelectItem[] selectItems_ = null;

    public SelectItemGroup() {
    }

    public SelectItemGroup(String label) {
        super("", label);
    }

    public SelectItemGroup(String label, String description, boolean disabled,
            SelectItem[] selectItems) {
        super("", label, description, disabled);
        setSelectItems(selectItems);
    }

    public SelectItem[] getSelectItems() {
        return selectItems_;
    }

    public void setSelectItems(SelectItem[] selectItems) {
        assertNotNull("selectItems", selectItems);
        selectItems_ = selectItems;
    }
}
