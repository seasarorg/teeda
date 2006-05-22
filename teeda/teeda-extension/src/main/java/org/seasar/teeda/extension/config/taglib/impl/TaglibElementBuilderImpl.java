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
package org.seasar.teeda.extension.config.taglib.impl;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;

import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.SAXParserFactoryUtil;
import org.seasar.framework.xml.SaxHandler;
import org.seasar.framework.xml.SaxHandlerParser;
import org.seasar.teeda.extension.config.taglib.TaglibElementBuilder;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.rule.TaglibTagHandlerRule;

/**
 * @author higa
 *
 */
public class TaglibElementBuilderImpl implements TaglibElementBuilder {

    public static final String PUBLIC_ID_11 = "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN";

    public static final String PUBLIC_ID_12 = "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN";

    protected static final String ROOT_PATH = ClassUtil.getPackageName(
            TaglibElementBuilderImpl.class).replace('.', '/');

    public static final String DTD_PATH_11 = ROOT_PATH
            + "/web-jsptaglibrary_1_1.dtd";

    public static final String DTD_PATH_12 = ROOT_PATH
            + "/web-jsptaglibrary_1_2.dtd";

    private static TaglibTagHandlerRule rule = new TaglibTagHandlerRule();

    public TaglibElement build(InputStream is) {
        SaxHandlerParser parser = createSaxHandlerParser();
        return (TaglibElement) parser.parse(is);
    }

    protected SaxHandlerParser createSaxHandlerParser() {
        SAXParser saxParser = SAXParserFactoryUtil.newSAXParser();
        SaxHandler handler = new SaxHandler(rule);
        handler.registerDtdPath(PUBLIC_ID_11, DTD_PATH_11);
        handler.registerDtdPath(PUBLIC_ID_12, DTD_PATH_12);
        return new SaxHandlerParser(handler, saxParser);
    }
}
