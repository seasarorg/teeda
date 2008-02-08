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
package org.seasar.teeda.core.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.iterators.IteratorChain;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.EmptyIterator;
import org.seasar.framework.util.EnumerationIterator;

public class IteratorUtil {

    private static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    private IteratorUtil() {
    }

    public static Iterator getIterator(Collection collection) {
        if (collection != null && !collection.isEmpty()) {
            return collection.iterator();
        } else {
            return EMPTY_ITERATOR;
        }
    }

    public static Iterator getEntryIterator(Map map) {
        if (map != null && !map.isEmpty()) {
            return map.entrySet().iterator();
        } else {
            return EMPTY_ITERATOR;
        }
    }

    public static Iterator getCompositeIterator(Map map1, Map map2) {
        IteratorChain chain = new IteratorChain();
        chain.addIterator(map1.keySet().iterator());
        chain.addIterator(map2.keySet().iterator());
        return chain;
    }

    public static Iterator getResourcesIterator(ClassLoader loader, String path) {
        try {
            Enumeration e = loader.getResources(path);
            return new EnumerationIterator(e);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

}
