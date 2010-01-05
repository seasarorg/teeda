/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.tagext.Tag;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.taglib.TConditionTag;

/**
 * @author higa
 */
public class ConditionElementProcessor extends ElementProcessorImpl {

    private String tagName;

    public ConditionElementProcessor(Class tagClass, Map properties,
            String tagName) {
        super(tagClass, properties);
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    protected Map setupProperties(Tag tag) {
        TConditionTag conditionTag = (TConditionTag) tag;
        conditionTag.setTagName(tagName);
        Map unboundProperties = super.setupProperties(tag);
        for (Iterator i = unboundProperties.keySet().iterator(); i.hasNext();) {
            String propertyName = (String) i.next();
            String value = getProperty(propertyName);
            if (StringUtil.isEmpty(value)) {
                continue;
            }
            conditionTag.addAttribute(propertyName, value);
        }
        return Collections.EMPTY_MAP;
    }

}
