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
package org.seasar.teeda.core.config.taglib.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.tagext.Tag;

import org.seasar.teeda.core.config.taglib.TagPool;

/**
 * @author higa
 * 
 */
public class TagPoolImpl implements TagPool {

    private Map tagPoolEntries = new HashMap();

    public TagPoolImpl() {
    }

    public synchronized Tag request(Class tagClass) {
        TagPoolEntry entry = (TagPoolEntry) tagPoolEntries.get(tagClass
                .getName());
        if (entry == null) {
            entry = new TagPoolEntry(tagClass);
            tagPoolEntries.put(tagClass.getName(), entry);
        }
        return entry.request();
    }

    public synchronized void release(Tag tag) {
        TagPoolEntry entry = (TagPoolEntry) tagPoolEntries.get(tag.getClass()
                .getName());
        if (entry != null) {
            entry.release(tag);
        } else {
            throw new IllegalStateException(tag.toString());
        }

    }

}
