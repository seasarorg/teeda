/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.seasar.framework.util.AssertionUtil;

public class LoopIterator implements Iterator {

    private static final int INITIAL_INDEX = 0;

    private Object[] values_;

    private int index_ = INITIAL_INDEX;

    public LoopIterator(Object[] values) {
        AssertionUtil.assertNotNull("values is null.", values);
        values_ = values;
    }

    public boolean hasNext() {
        if (values_.length > 0) {
            return true;
        }
        return false;
    }

    public Object next() {
        if (values_.length < 1) {
            throw new NoSuchElementException("next");
        }
        if (values_.length <= index_) {
            reset();
        }
        Object value = values_[index_];
        index_++;
        return value;
    }

    public void remove() {
        // XXX should be supported?
        throw new UnsupportedOperationException("remove");
    }

    public void reset() {
        index_ = INITIAL_INDEX;
    }

}
