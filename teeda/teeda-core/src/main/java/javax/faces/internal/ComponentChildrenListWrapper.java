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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 * 
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class ComponentChildrenListWrapper extends AbstractList implements
        Serializable {

    private static final long serialVersionUID = 3617294519188666163L;

    private final List list = new ArrayList();

    private final UIComponent parent;

    public ComponentChildrenListWrapper(final UIComponent parent) {
        this.parent = parent;
    }

    public Object get(final int index) {
        return list.get(index);
    }

    public Object remove(final int index) {
        final UIComponent child = (UIComponent) list.remove(index);
        if (child != null) {
            child.setParent(null);
        }
        return child;
    }

    public int size() {
        return list.size();
    }

    public void add(final int index, final Object child) {
        assertUIComponent(child);
        setNewParent((UIComponent) child);
        list.add(index, child);
    }

    public boolean add(final Object child) {
        assertUIComponent(child);
        setNewParent((UIComponent) child);
        return list.add(child);
    }

    public boolean addAll(final Collection children) {
        boolean changed = false;
        for (final Iterator it = children.iterator(); it.hasNext();) {
            final Object child = it.next();
            assertUIComponent(child);
            add(child);
            changed = true;
        }
        return changed;
    }

    public Iterator iterator() {
        return list.iterator();
    }

    private void setNewParent(final UIComponent child) {
        child.setParent(parent);
    }

    private void assertUIComponent(final Object obj) {
        AssertionUtil.assertNotNull("value", obj);
        if (!(obj instanceof UIComponent)) {
            throw new ClassCastException(obj.getClass().getName());
        }
    }

}
