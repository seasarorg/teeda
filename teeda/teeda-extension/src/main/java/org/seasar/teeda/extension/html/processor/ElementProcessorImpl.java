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
 * @author manhole
 */
public class ElementProcessorImpl implements ElementProcessor {

    private Class tagClass;

    private Map properties;

    private final List children = new ArrayList();

    private StringBuffer buffer;

    public ElementProcessorImpl(final Class tagClass, final Map properties) {
        this.tagClass = tagClass;
        this.properties = properties;
        initializeBuffer();
    }

    protected void initializeBuffer() {
        buffer = new StringBuffer(256);
    }

    public Class getTagClass() {
        return tagClass;
    }

    public String getProperty(final String name) {
        return (String) properties.get(name);
    }

    public void setProperty(final String name, final String value) {
        properties.put(name, value);
    }

    public Iterator getPropertyNameIterator() {
        return properties.keySet().iterator();
    }

    public int getChildSize() {
        return children.size();
    }

    public TagProcessor getChild(final int index) {
        return (TagProcessor) children.get(index);
    }

    protected void addChild(final TagProcessor child) {
        children.add(child);
    }

    public void addElement(final ElementProcessor processor) {
        processText();
        addChild(processor);
    }

    public void addText(final String text) {
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

    public void process(final PageContext pageContext, final Tag parentTag)
            throws JspException {

        final Tag tag = (Tag) ClassUtil.newInstance(tagClass);
        try {
            process(pageContext, tag, parentTag);
        } finally {
            tag.release();
        }
    }

    protected void process(final PageContext pageContext, final Tag tag,
            final Tag parentTag) throws JspException {

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

    protected void processTag(final PageContext pageContext, final Tag tag)
            throws JspException {

        if (Tag.SKIP_BODY != tag.doStartTag()) {
            processChildren(pageContext, tag);
            tag.doEndTag();
        }
    }

    protected void processBodyTag(final PageContext pageContext,
            final BodyTag tag) throws JspException {

        final int evalDoStartTag = tag.doStartTag();
        if (Tag.SKIP_BODY != evalDoStartTag) {
            BodyContent bodyContent = null;
            if (Tag.EVAL_BODY_INCLUDE != evalDoStartTag) {
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

    protected void processIterationTag(final PageContext pageContext,
            final IterationTag tag) throws JspException {

        final int evalDoStartTag = tag.doStartTag();
        if (Tag.SKIP_BODY != evalDoStartTag) {
            do {
                processChildren(pageContext, tag);
            } while (IterationTag.EVAL_BODY_AGAIN == tag.doAfterBody());
        }
        tag.doEndTag();
    }

    protected void processChildren(final PageContext pageContext,
            final Tag parentTag) throws JspException {

        for (int i = 0; i < getChildSize(); i++) {
            final TagProcessor child = getChild(i);
            child.process(pageContext, parentTag);
        }
    }

    public void composeComponentTree(final FacesContext context,
            final PageContext pageContext, final Tag parentTag)
            throws JspException {
        final Tag tag = (Tag) ClassUtil.newInstance(tagClass);
        try {
            if (tag instanceof UIComponentTag) {
                final UIComponentTag componentTag = (UIComponentTag) tag;
                composeComponentTree(context, pageContext, componentTag,
                        parentTag);
            } else {
                setUpTag(context, pageContext, tag, parentTag);
            }
        } finally {
            tag.release();
        }
    }

    protected void setUpTag(final FacesContext context,
            final PageContext pageContext, final Tag tag, final Tag parentTag)
            throws JspException {
        if (parentTag != null) {
            tag.setParent(parentTag);
        }
        tag.setPageContext(pageContext);
        setupProperties(tag);
        composeComponentTreeChildren(context, pageContext, tag);
    }

    protected void composeComponentTree(final FacesContext context,
            final PageContext pageContext, final UIComponentTag tag,
            final Tag parentTag) throws JspException {

        if (parentTag != null) {
            tag.setParent(parentTag);
        }
        tag.setPageContext(pageContext);
        final Map ignoredProps = setupProperties(tag);
        tag.setupFacesContext();
        final UIComponent component = tag.findComponent(context);
        component.getAttributes().putAll(ignoredProps);
        tag.pushUIComponentTag();
        try {
            composeComponentTreeChildren(context, pageContext, tag);
        } finally {
            tag.popUIComponentTag();
        }
    }

    protected void composeComponentTreeChildren(final FacesContext context,
            final PageContext pageContext, final Tag parentTag)
            throws JspException {

        for (int i = 0; i < getChildSize(); i++) {
            final TagProcessor child = getChild(i);
            child.composeComponentTree(context, pageContext, parentTag);
        }
    }

    protected Map setupProperties(final Tag tag) {
        final Map ignoredProperties = new HashMap();
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(tag.getClass());
        for (final Iterator i = getPropertyNameIterator(); i.hasNext();) {
            final String propertyName = (String) i.next();
            final String value = getProperty(propertyName);
            if (StringUtil.isEmpty(value)) {
                continue;
            }
            if (beanDesc.hasPropertyDesc(propertyName)) {
                final PropertyDesc pd = beanDesc.getPropertyDesc(propertyName);
                if (pd.isWritable()) {
                    pd.setValue(tag, value);
                }
            } else {
                ignoredProperties.put(propertyName, value);
            }
        }
        return ignoredProperties;
    }

}
