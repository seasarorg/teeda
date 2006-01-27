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
package org.seasar.teeda.core.application;

import java.io.Serializable;

/**
 * @author higa
 * 
 */
public class TreeStructure implements Serializable {
	
	static final long serialVersionUID = 0L;
	
	private String componentClassName;

	private String componentId;

	private TreeStructure[] children;

	private Object[] facets;

	public TreeStructure(String componentClassName, String componentId) {
		this.componentClassName = componentClassName;
		this.componentId = componentId;
	}

	public String getComponentClassName() {
		return componentClassName;
	}

	public String getComponentId() {
		return componentId;
	}

	public TreeStructure[] getChildren() {
		return children;
	}
	
	public void setChildren(TreeStructure[] children) {
		this.children = children;
	}

	public Object[] getFacets() {
		return facets;
	}

	public void setFacets(Object[] facets) {
		this.facets = facets;
	}
}