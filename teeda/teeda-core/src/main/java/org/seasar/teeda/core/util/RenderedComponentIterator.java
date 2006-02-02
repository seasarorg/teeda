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
package org.seasar.teeda.core.util;

import java.util.Collection;
import java.util.Iterator;

import javax.faces.component.UIComponent;

/**
 * @author manhole
 */
public class RenderedComponentIterator implements Iterator {

    private Iterator iterator_;

    public RenderedComponentIterator(Collection c) {
        iterator_ = c.iterator();
    }

    private UIComponent component_;

    public boolean hasNext() {
        if (component_ != null) {
            return true;
        }
        while (iterator_.hasNext()) {
            UIComponent component = (UIComponent) iterator_.next();
            if (component.isRendered()) {
                component_ = component;
                return true;
            }
        }
        return false;
    }

    public Object next() {
        if (component_ != null) {
            UIComponent component = component_;
            component_ = null;
            return component;
        }
        while (true) {
            UIComponent component = (UIComponent) iterator_.next();
            if (component.isRendered()) {
                return component;
            }
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("remove");
    }

}
