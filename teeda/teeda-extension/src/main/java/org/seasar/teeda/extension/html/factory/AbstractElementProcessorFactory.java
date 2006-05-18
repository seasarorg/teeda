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
package org.seasar.teeda.extension.html.factory;


import org.seasar.teeda.extension.config.taglib.TaglibManager;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.html.ElementProcessorFactory;

/**
 * @author higa
 *  
 */
public abstract class AbstractElementProcessorFactory implements ElementProcessorFactory {

	private TaglibManager taglibManager;

    public TaglibManager getTaglibManager() {
        return taglibManager;
    }

    public void setTaglibManager(TaglibManager taglibManager) {
        this.taglibManager = taglibManager;
    }
    
    protected Class getTagClass(String uri, String tagName) {
        TaglibElement taglibElement = taglibManager.getTaglibElement(uri);
        TagElement tagElement = taglibElement.getTagElement(tagName);
        return tagElement.getTagClass();
    }
}