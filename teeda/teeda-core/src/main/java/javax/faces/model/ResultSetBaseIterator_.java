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

import java.util.Iterator;
import java.util.Map;

/**
 * TODO TEST
 */

class ResultSetBaseIterator_ implements Iterator{

	ResultSetDataModel.ResultSetMap map_ = null;
	Iterator itr_ = null;
	public ResultSetBaseIterator_(ResultSetDataModel.ResultSetMap map){
		super();
		map_ = map;
		itr_ = map_.keySet().iterator();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	public boolean hasNext() {
		return itr_.hasNext();
	}

	public Object next() {
		return itr_.next();
	}
	
	
}
