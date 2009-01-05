/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.html.processor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.tagext.Tag;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.taglib.TElementTag;

/**
 * @author higa
 *
 */
public class GenericElementProcessor extends ElementProcessorImpl {

    private static final Map EMPTY_MAP = new HashMap();

    private String tagName;

    public GenericElementProcessor(Class tagClass, Map properties,
            String tagName) {
        super(tagClass, properties);
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    protected Map setupProperties(Tag tag) {
        TElementTag elementTag = (TElementTag) tag;
        elementTag.setTagName(tagName);
        for (Iterator i = getPropertyNameIterator(); i.hasNext();) {
            String propertyName = (String) i.next();
            String value = getProperty(propertyName);
            if (StringUtil.isEmpty(value)) {
                continue;
            }
            elementTag.addAttribute(propertyName, value);
        }
        return EMPTY_MAP;
    }

}
