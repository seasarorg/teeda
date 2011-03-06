/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.seasar.teeda.extension.taglib.TViewTag;

/**
 * @author higa
 *
 */
public class ViewProcessor extends ElementProcessorImpl {

    private static Map EMPTY_PROPERTIES = new HashMap();

    public ViewProcessor() {
        super(TViewTag.class, EMPTY_PROPERTIES);
    }

    protected void processChildren(final PageContext pageContext,
            final Tag parentTag) throws JspException {
    }
}
