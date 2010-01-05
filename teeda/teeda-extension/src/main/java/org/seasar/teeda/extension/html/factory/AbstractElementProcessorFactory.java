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
package org.seasar.teeda.extension.html.factory;

import java.util.Iterator;
import java.util.Map;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.extension.ExtensionConstants;
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
 * @author shot
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

        String uri = getUri();
        if (StringUtil.isEmpty(uri)) {
            uri = elementNode.getNamespaceURI();
        }
        String tagName = getTagName();
        if (StringUtil.isEmpty(tagName)) {
            tagName = elementNode.getLocalName();
        }
        return createProcessor(elementNode, pageDesc, actionDesc, uri, tagName);
    }

    public boolean isLeaf() {
        return false;
    }

    protected Class getTagClass(String uri, String tagName) {
        TaglibElement taglibElement = taglibManager.getTaglibElement(uri);
        TagElement tagElement = taglibElement.getTagElement(tagName);
        return tagElement.getTagClass();
    }

    protected boolean hasTaglibElement(String uri) {
        return taglibManager.hasTaglibElement(uri);
    }

    protected ElementProcessor createProcessor(ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc, String uri, String tagName) {
        Class tagClass = getTagClass(uri, tagName);
        Map props = createProperties(elementNode, pageDesc, actionDesc);
        ElementProcessor processor = new ElementProcessorImpl(tagClass, props);
        customizeProcessor(processor, elementNode, pageDesc, actionDesc);
        return processor;
    }

    protected void customizeProcessor(ElementProcessor processor,
            ElementNode elementNode, PageDesc pageDesc, ActionDesc actionDesc) {
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
        customizeDynamicProperties(properties, elementNode, pageDesc,
                actionDesc);
    }

    protected void customizeDynamicProperties(Map properties,
            ElementNode elementNode, PageDesc pageDesc, ActionDesc actionDesc) {
        /*
         * "aaa-xxxxx"というidのとき、value属性は"aaa"でバインドし、
         * value以外の属性は"aaaXxxx"でバインドする。
         */
        final String s = elementNode.getProperty(JsfConstants.ID_ATTR);
        final String id;
        if (s == null) {
            return;
        }
        final int pos = s.indexOf('-');
        if (-1 < pos) {
            id = s.substring(0, pos) +
                    StringUtil.capitalize(s.substring(pos + 1));
        } else {
            id = s;
        }
        customizeDynamicProperties(id, properties, elementNode, pageDesc,
                actionDesc);
    }

    protected void customizeDynamicProperties(String base, Map properties,
            ElementNode elementNode, PageDesc pageDesc, ActionDesc actionDesc) {
        if (base == null) {
            return;
        }
        for (Iterator i = properties.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            if (JsfConstants.ID_ATTR.equals(key)) {
                continue;
            }
            customizeDynamicProperty(base, key, properties, elementNode,
                    pageDesc, actionDesc);
        }
    }

    protected void customizeDynamicProperty(String base, String name,
            Map properties, ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (pageDesc == null) {
            customizeLabelProperty(name, properties, pageDesc);
            return;
        }
        final String pageName = pageDesc.getPageName();
        if (name.equals(JsfConstants.STYLE_CLASS_ATTR) &&
                !StringUtil.isEmpty(base)) {
            String s = base + StringUtil.capitalize(JsfConstants.CLASS_ATTR);
            if (pageDesc.hasDynamicProperty(s)) {
                properties.put(name, getBindingExpression(pageName, s));
                return;
            }
        }
        String propName = base + StringUtil.capitalize(name);
        if (pageDesc.hasDynamicProperty(propName)) {
            properties.put(name, getBindingExpression(pageName, propName));
            return;
        }
        customizeLabelProperty(name, properties, pageDesc);
    }

    protected void customizeDynamicPropertyIfNotExists(String base,
            String name, Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {

        if (!properties.containsKey(name)) {
            customizeDynamicProperty(base, name, properties, elementNode,
                    pageDesc, actionDesc);
        }
    }

    protected void customizeLabelProperty(final String name,
            final Map properties, PageDesc pageDesc) {
        final String value = (String) properties.get(name);
        if (pageDesc == null || value == null ||
                !value.endsWith(ExtensionConstants.LABEL_ATTRIBUTE_SUFFIX)) {
            return;
        }
        properties.put(name, getLabelExpression(value, pageDesc));
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

    protected String getLabelExpression(final String attributeValue,
            PageDesc pageDesc) {
        final String pageName = pageDesc.getPageName();
        final String labelName = attributeValue.substring(0, attributeValue
                .length() -
                ExtensionConstants.LABEL_ATTRIBUTE_SUFFIX.length());
        return "#{labelProvider." + pageName + "." + labelName + "}";
    }

    protected abstract String getUri();

    protected abstract String getTagName();

}