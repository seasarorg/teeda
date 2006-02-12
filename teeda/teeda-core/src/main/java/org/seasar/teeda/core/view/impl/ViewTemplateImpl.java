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
package org.seasar.teeda.core.view.impl;

import java.io.File;

import org.seasar.teeda.core.view.TagProcessor;
import org.seasar.teeda.core.view.ViewTemplate;

/**
 * @author higa
 *  
 */
public class ViewTemplateImpl implements ViewTemplate {

	private TagProcessor rootTagProcessor;

	private File file;

	private long lastModified;

	public ViewTemplateImpl(TagProcessor rootTagProcessor) {
		this(rootTagProcessor, null);
	}

	public ViewTemplateImpl(TagProcessor rootTagProcessor, File file) {
		this.rootTagProcessor = rootTagProcessor;
		if (file != null) {
			this.file = file;
			this.lastModified = file.lastModified();
		}
	}

	public TagProcessor getRootTagProcessor() {
		return rootTagProcessor;
	}
	
	public long getLastModified() {
		return file.lastModified();
	}

	public boolean isModified() {
		if (file != null) {
			return getLastModified() > lastModified;
		}
		return false;
	}
}