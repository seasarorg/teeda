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
package org.seasar.teeda.ajax.json;

import java.util.Iterator;
import java.util.List;

/**
 * @author shot
 * 
 */
public class JSONStringBuffer {

    private static final int DEFAULT_CAPACITY = 100;
    
    private StringBuffer buf = null;

    public JSONStringBuffer() {
        this(DEFAULT_CAPACITY);
    }

    public JSONStringBuffer(int capacity) {
        createBuffer(capacity);
        addStartBrace();
    }

    public void append(String s) {
        buf.append("\"" + s + "\"");
    }

    public void append(Object o) {
        if (o == null) {
            buf.append("null");
            appendComma();
            return;
        }
        if (o instanceof Number) {
            buf.append(o);
            appendComma();
        } else if (o instanceof List) {
            appendList((List)o);
        } else {
            append(o.toString());
            appendComma();
        }
    }

    public void appendColon() {
        buf.append(":");
    }

    public void appendComma() {
        buf.append(",");
    }
    
    protected void appendList(List list) {
        addStartListBrace();
        for(Iterator itr = list.iterator(); itr.hasNext();) {
            append(itr.next());
        }
        String res = buf.substring(0, buf.length() - 1);
        createBuffer();
        buf.append(res);
        addEndListBrace();
    }
    
    private void addStartBrace() {
        buf.append("{");
    }

    private void addEndBrace() {
        buf.append("}");
    }

    private void addStartListBrace() {
        buf.append("[");
    }

    private void addEndListBrace() {
        buf.append("]");
    }

    private synchronized void createBuffer() {
        createBuffer(DEFAULT_CAPACITY);
    }
    private synchronized void createBuffer(int capacity) {
        buf = new StringBuffer(capacity);
    }
    
    public String toString() {
        String result = buf.toString();
        if(result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        createBuffer();
        buf.append(result);
        addEndBrace();
        return buf.toString();
    }
}
