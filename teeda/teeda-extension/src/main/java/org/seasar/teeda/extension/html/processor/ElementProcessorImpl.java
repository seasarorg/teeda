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
package org.seasar.teeda.extension.html.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.Tag;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.TagProcessor;

/**
 * @author higa
 *  
 */
public class ElementProcessorImpl implements ElementProcessor {

    private Class tagClass;

    private Map properties;

    private List children = new ArrayList();

    private Set customPropertyNames = new HashSet();

    private StringBuffer buffer;
    
    private int childTextSize = 0;

    public ElementProcessorImpl(Class tagClass, Map properties) {
        this.tagClass = tagClass;
        this.properties = properties;
        renameProperties();
        initializeBuffer();
    }

    protected void initializeBuffer() {
        buffer = new StringBuffer(100);
    }

    public Class getTagClass() {
        return tagClass;
    }

    protected String getProperty(String name) {
        return (String) properties.get(name);
    }

    protected Iterator getPropertyNameIterator() {
        return properties.keySet().iterator();
    }

    protected void renameProperties() {
        renameProperty(JsfConstants.CLASS_ATTR, JsfConstants.STYLE_CLASS_ATTR);
    }

    protected void renameProperty(String from, String to) {
        if (properties.containsKey(from)) {
            Object value = properties.remove(from);
            properties.put(to, value);
        }
    }

    public int getChildSize() {
        return children.size();
    }

    public TagProcessor getChild(int index) {
        return (TagProcessor) children.get(index);
    }

    protected void addChild(TagProcessor child) {
        children.add(child);
    }

    public void addElement(ElementProcessor processor) {
        processText();
        addChild(processor);
    }

    public void addText(String text) {
        buffer.append(text);
    }

    protected void processText() {
        if (buffer.length() > 0) {
            addChild(new TextProcessorImpl(buffer.toString()));
            initializeBuffer();
        }
    }

    public void endElement() {
        processText();
    }
    
    public int getChildTextSize() {
        return childTextSize;
    }
    
    public void incrementChildTextSize() {
        ++childTextSize;
    }
    
    public void decrementChildTextSize() {
        --childTextSize;
    }

    public void process(PageContext pageContext, Tag parentTag)
            throws JspException {

        Tag tag = (Tag) ClassUtil.newInstance(tagClass);
        try {
            process(pageContext, tag, parentTag);
        } finally {
            tag.release();
        }
    }

    protected void process(PageContext pageContext, Tag tag, Tag parentTag)
            throws JspException {

        if (parentTag != null) {
            tag.setParent(parentTag);
        }
        tag.setPageContext(pageContext);
        setProperties(tag);
        if (tag instanceof BodyTag) {
            processBodyTag(pageContext, (BodyTag) tag);
        } else if (tag instanceof IterationTag) {
            processIterationTag(pageContext, (IterationTag) tag);
        } else {
            processTag(pageContext, tag);
        }
    }

    protected void processTag(PageContext pageContext, Tag tag)
            throws JspException {

        if (Tag.SKIP_BODY != tag.doStartTag()) {
            processChildren(pageContext, tag);
            tag.doEndTag();
        }
    }

    protected void processBodyTag(PageContext pageContext, BodyTag tag)
            throws JspException {

        int evalDoStartTag = tag.doStartTag();
        if (BodyTag.SKIP_BODY != evalDoStartTag) {
            BodyContent bodyContent = null;
            if (BodyTag.EVAL_BODY_INCLUDE != evalDoStartTag) {
                bodyContent = pageContext.pushBody();
                tag.setBodyContent(bodyContent);
                tag.doInitBody();
            }
            do {
                processChildren(pageContext, tag);
            } while (IterationTag.EVAL_BODY_AGAIN == tag.doAfterBody());
            if (bodyContent != null) {
                pageContext.popBody();
            }
        }
        tag.doEndTag();
    }

    protected void processIterationTag(PageContext pageContext, IterationTag tag)
            throws JspException {

        int evalDoStartTag = tag.doStartTag();
        if (BodyTag.SKIP_BODY != evalDoStartTag) {
            do {
                processChildren(pageContext, tag);
            } while (IterationTag.EVAL_BODY_AGAIN == tag.doAfterBody());
        }
        tag.doEndTag();
    }

    protected void processChildren(PageContext pageContext, Tag parentTag)
            throws JspException {

        for (int i = 0; i < getChildSize(); i++) {
            TagProcessor child = getChild(i);
            child.process(pageContext, parentTag);
        }
    }

    protected void setProperties(Tag tag) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(tag.getClass());
        for (Iterator i = getPropertyNameIterator(); i.hasNext();) {
            String propertyName = (String) i.next();
            if (!beanDesc.hasPropertyDesc(propertyName)
                    || isCustomProperty(propertyName)) {
                continue;
            }
            String value = getProperty(propertyName);
            if (StringUtil.isEmpty(value)) {
                continue;
            }
            PropertyDesc pd = beanDesc.getPropertyDesc(propertyName);
            pd.setValue(tag, value);
        }
    }

    protected void addCustomPropertyName(String name) {
        customPropertyNames.add(name);
    }

    protected boolean isCustomProperty(String name) {
        return customPropertyNames.contains(name);
    }
}