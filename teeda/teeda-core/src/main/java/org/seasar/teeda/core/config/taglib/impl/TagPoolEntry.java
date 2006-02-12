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

import javax.servlet.jsp.tagext.Tag;

import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.SLinkedList;

/**
 * @author higa
 *
 */
public class TagPoolEntry {

	private Class tagClass;
	
	private SLinkedList pool = new SLinkedList();
	
	public TagPoolEntry(Class tagClass) {
		if (!Tag.class.isAssignableFrom(tagClass)) {
			throw new IllegalArgumentException(tagClass.toString());
		}
		this.tagClass = tagClass;
	}
	
	public synchronized Tag request() {
		if (!pool.isEmpty()) {
			return (Tag) pool.removeLast();
		}
		return (Tag) ClassUtil.newInstance(tagClass);
	}

	public synchronized void release(Tag tag) {
		pool.addLast(tag);
	}
}
