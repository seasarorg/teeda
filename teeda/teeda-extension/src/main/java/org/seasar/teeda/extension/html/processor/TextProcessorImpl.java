/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import javax.faces.context.FacesContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.seasar.teeda.extension.html.TextProcessor;
import org.seasar.teeda.extension.taglib.TextTag;

/**
 * @author higa
 * @author shot
 * @author manhole
 */
public class TextProcessorImpl implements TextProcessor {

    private String value;

    public TextProcessorImpl(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void process(final PageContext pageContext, final Tag parentTag)
            throws JspException {

        final TextTag tag = new TextTag();
        try {
            tag.setPageContext(pageContext);
            tag.setValue(value);
            tag.doStartTag();
            tag.doEndTag();
        } finally {
            tag.release();
        }
    }

    public void composeComponentTree(final FacesContext context,
            final PageContext pageContext, final Tag parentTag)
            throws JspException {

        final TextTag tag = new TextTag();
        try {
            tag.setParent(parentTag);
            tag.setPageContext(pageContext);
            tag.setValue(value);
            tag.findComponent(context);
        } finally {
            tag.release();
        }
    }

}
