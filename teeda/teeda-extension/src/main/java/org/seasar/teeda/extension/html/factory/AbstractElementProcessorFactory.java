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

import java.util.Map;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.extension.config.taglib.TaglibManager;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.ElementProcessorFactory;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.processor.ElementProcessorImpl;

/**
 * @author higa
 *
 */
public abstract class AbstractElementProcessorFactory implements
        ElementProcessorFactory {

    private TaglibManager taglibManager;

    public TaglibManager getTaglibManager() {
        return taglibManager;
    }

    public void setTaglibManager(TaglibManager taglibManager) {
        this.taglibManager = taglibManager;
    }

    public ElementProcessor createProcessor(ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        return createProcessor(elementNode, pageDesc, actionDesc, getUri(),
                getTagName());
    }

    public boolean isLeaf() {
        return false;
    }

    protected Class getTagClass(String uri, String tagName) {
        TaglibElement taglibElement = taglibManager.getTaglibElement(uri);
        TagElement tagElement = taglibElement.getTagElement(tagName);
        return tagElement.getTagClass();
    }

    protected ElementProcessor createProcessor(ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc, String uri, String tagName) {
        Class tagClass = getTagClass(uri, tagName);
        Map props = createProperties(elementNode, pageDesc, actionDesc);
        return new ElementProcessorImpl(tagClass, props);
    }

    protected Map createProperties(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        Map props = elementNode.copyProperties();
        customizeProperties(props, elementNode, pageDesc, actionDesc);
        return props;
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        renameProperty(properties, JsfConstants.CLASS_ATTR,
                JsfConstants.STYLE_CLASS_ATTR);
    }

    protected void renameProperty(Map properties, String from, String to) {
        if (properties.containsKey(from)) {
            Object value = properties.remove(from);
            properties.put(to, value);
        }
    }

    protected String getBindingExpression(String componentName,
            String targetName) {
        return BindingUtil.getExpression(componentName, targetName);
    }

    protected abstract String getUri();

    protected abstract String getTagName();

}