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

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

import javax.faces.model.ResultSetDataModel;

/**
 * @author shot
 * 
 * This class might be changed without a previous notice. Please do not use it
 * excluding the JSF specification part.
 */
public class ResultSetBaseCollection extends AbstractCollection {

    ResultSetDataModel.ResultSetMap map_ = null;

    public ResultSetBaseCollection(ResultSetDataModel.ResultSetMap map) {
        map_ = map;
    }

    public boolean contains(Object o) {
        return map_.containsKey(o);
    }

    public boolean isEmpty() {
        return map_.isEmpty();
    }

    public Iterator iterator() {
        return map_.keySet().iterator();
    }

    public int size() {
        return map_.size();
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
