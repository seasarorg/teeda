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
package javax.faces.internal;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

import javax.faces.model.ResultSetDataModel;

/**
 * @author shot
 * 
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class ResultSetBaseCollection extends AbstractCollection {

    ResultSetDataModel.ResultSetMap map = null;

    public ResultSetBaseCollection(ResultSetDataModel.ResultSetMap map) {
        this.map = map;
    }

    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Iterator iterator() {
        return map.keySet().iterator();
    }

    public int size() {
        return map.size();
    }

    public final boolean add(Object o) {
        throw new UnsupportedOperationException();
    }

    public final boolean addAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public final boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public final boolean removeAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    public final boolean retainAll(Collection c) {
        throw new UnsupportedOperationException();
    }

}
