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
package org.seasar.teeda.core.view;

import org.seasar.teeda.core.config.taglib.TagConfig;
import org.seasar.teeda.core.config.taglib.TaglibManager;
import org.seasar.teeda.core.exception.PrefixNotFoundRuntimeException;
import org.seasar.teeda.core.exception.UriNotFoundRuntimeException;

/**
 * @author higa
 * 
 */
public interface JsfConfig {

	public void addTaglibUri(String prefix, String uri);
	
	public boolean hasTaglibUri(String prefix);
	
	public String getTaglibUri(String prefix) throws PrefixNotFoundRuntimeException;
	
	public String getTaglibPrefix(String uri) throws UriNotFoundRuntimeException;
	
	public TagConfig getTagConfig(String prefix, String tagName);
	
	public TagConfig getTagConfig(String inject);
	
	public void setTaglibManager(TaglibManager taglibManager);
    
    public boolean isAllowJavascript();
    
    public void setAllowJavascript(boolean allowJavascript);
    
}
