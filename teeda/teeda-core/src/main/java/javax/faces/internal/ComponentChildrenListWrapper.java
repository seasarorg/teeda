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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;

/**
 * @author shot
 * 
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class ComponentChildrenListWrapper extends AbstractList implements
        Serializable {

    private static final long serialVersionUID = 3617294519188666163L;

    private List list_ = new ArrayList();

    private UIComponent component_ = null;

    public ComponentChildrenListWrapper(UIComponent component) {
        component_ = component;
    }

    public Object get(int num) {
        return list_.get(num);
    }

    public Object remove(int num) {
        UIComponent child = (UIComponent) list_.get(num);
        if (child != null) {
            child.setParent(null);
        }
        return child;
    }

    public int size() {
        return list_.size();
    }

    public void add(int num, Object obj) {
        assertUIComponent(obj);
        setNewParent((UIComponent) obj);
        list_.add(num, obj);
    }

    public boolean add(Object obj) {
        assertUIComponent(obj);
        setNewParent((UIComponent) obj);
        return list_.add(obj);
    }

    public boolean addAll(Collection collection) {
        boolean changed = false;
        Object obj = null;
        for (Iterator itr = collection.iterator(); itr.hasNext();) {
            obj = itr.next();
            assertUIComponent(obj);
            add((UIComponent) obj);
            changed = true;
        }
        return changed;
    }

    private void setNewParent(UIComponent child) {
        UIComponent parent = child.getParent();
        if (parent != null) {
            removeFromParent(parent, child);
        }
        child.setParent(component_);
    }

    private void removeFromParent(UIComponent parent, UIComponent child) {
        parent.getChildren().remove(child);
    }

    private static void assertUIComponent(Object obj) {
        AssertionUtil.assertNotNull("value", obj);
        if (!(obj instanceof UIComponent)) {
            throw new ClassCastException("value");
        }
    }
}
