/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.config.taglib.element.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.core.exception.TagNotFoundRuntimeException;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;

/**
 * @author higa
 *
 */
public class TaglibElementImpl implements TaglibElement {

    private String uri;

    private Map tagElements = new HashMap();

    public TaglibElementImpl() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public TagElement getTagElement(String name) {
        TagElement tagElement = (TagElement) tagElements.get(name);
        if (tagElement == null) {
            throw new TagNotFoundRuntimeException(name);
        }
        return tagElement;
    }

    public void addTagElement(TagElement tagElement) {
        tagElements.put(tagElement.getName(), tagElement);
    }

}
