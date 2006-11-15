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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;
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

    private StringBuffer buffer;

    public ElementProcessorImpl(Class tagClass, Map properties) {
        this.tagClass = tagClass;
        this.properties = properties;
        initializeBuffer();
    }

    protected void initializeBuffer() {
        buffer = new StringBuffer(100);
    }

    public Class getTagClass() {
        return tagClass;
    }

    public String getProperty(String name) {
        return (String) properties.get(name);
    }

    public void setProperty(String name, String value) {
        properties.put(name, value);
    }

    public Iterator getPropertyNameIterator() {
        return properties.keySet().iterator();
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
        setupProperties(tag);
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

    public void composeComponentTree(final FacesContext context,
            final PageContext pageContext, final UIComponentTag parentTag)
            throws JspException {
        final Tag tag = (Tag) ClassUtil.newInstance(tagClass);
        if (tag instanceof UIComponentTag) {
            try {
                final UIComponentTag componentTag = (UIComponentTag) tag;
                composeComponentTree(context, pageContext, componentTag,
                        parentTag);
            } finally {
                tag.release();
            }
        } else {
            setUpTag(pageContext, tag, parentTag);
        }
    }

    protected void setUpTag(final PageContext pageContext, final Tag tag,
            final UIComponentTag parentTag) throws JspException {
        if (parentTag != null) {
            tag.setParent(parentTag);
        }
        tag.setPageContext(pageContext);
        setupProperties(tag);
    }

    protected void composeComponentTree(FacesContext context,
            PageContext pageContext, UIComponentTag tag,
            UIComponentTag parentTag) throws JspException {

        if (parentTag != null) {
            tag.setParent(parentTag);
        }
        tag.setPageContext(pageContext);
        Map ignoredProps = setupProperties(tag);
        tag.setupFacesContext();
        UIComponent component = tag.findComponent(context);
        component.getAttributes().putAll(ignoredProps);
        tag.pushUIComponentTag();
        try {
            composeComponentTreeChildren(context, pageContext, tag);
        } finally {
            tag.popUIComponentTag();
        }
    }

    protected void composeComponentTreeChildren(FacesContext context,
            PageContext pageContext, UIComponentTag parentTag)
            throws JspException {

        for (int i = 0; i < getChildSize(); i++) {
            TagProcessor child = getChild(i);
            child.composeComponentTree(context, pageContext, parentTag);
        }
    }

    protected Map setupProperties(Tag tag) {
        Map ignoredProperties = new HashMap();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(tag.getClass());
        for (Iterator i = getPropertyNameIterator(); i.hasNext();) {
            String propertyName = (String) i.next();
            String value = getProperty(propertyName);
            if (StringUtil.isEmpty(value)) {
                continue;
            }
            if (beanDesc.hasPropertyDesc(propertyName)) {
                PropertyDesc pd = beanDesc.getPropertyDesc(propertyName);
                pd.setValue(tag, value);
            } else {
                ignoredProperties.put(propertyName, value);
            }
        }
        return ignoredProperties;
    }
}