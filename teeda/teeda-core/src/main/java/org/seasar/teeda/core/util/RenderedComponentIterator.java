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
package org.seasar.teeda.core.util;

import java.util.Collection;
import java.util.Iterator;

import javax.faces.component.UIComponent;

/**
 * @author manhole
 */
public class RenderedComponentIterator implements Iterator {

    private Iterator iterator;

    private UIComponent component;

    public RenderedComponentIterator(final Collection collection) {
        iterator = collection.iterator();
    }

    public boolean hasNext() {
        if (component != null) {
            return true;
        }
        while (iterator.hasNext()) {
            final UIComponent c = (UIComponent) iterator.next();
            if (c.isRendered()) {
                component = c;
                return true;
            }
        }
        return false;
    }

    public Object next() {
        if (component != null) {
            final UIComponent c = component;
            component = null;
            return c;
        }
        while (true) {
            final UIComponent c = (UIComponent) iterator.next();
            if (c.isRendered()) {
                return c;
            }
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("remove");
    }

}
