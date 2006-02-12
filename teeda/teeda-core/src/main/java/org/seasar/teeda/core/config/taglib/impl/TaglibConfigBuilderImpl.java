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
package org.seasar.teeda.core.config.taglib.impl;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;

import org.seasar.framework.util.SAXParserFactoryUtil;
import org.seasar.framework.xml.SaxHandler;
import org.seasar.framework.xml.SaxHandlerParser;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.config.taglib.TaglibConfig;
import org.seasar.teeda.core.config.taglib.TaglibConfigBuilder;
import org.seasar.teeda.core.config.taglib.rule.TaglibTagHandlerRule;

/**
 * @author higa
 * @author shot
 */
public final class TaglibConfigBuilderImpl implements TaglibConfigBuilder {

    public static final String PUBLIC_ID_11 = "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN";

    public static final String PUBLIC_ID_12 = "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN";

    public static final String DTD_PATH_11 = JsfConstants.RESOURCE_PACKAGE_ROOT
            .replaceAll(".", "/")
            + "/" + "web-jsptaglibrary_1_1.dtd";

    public static final String DTD_PATH_12 = JsfConstants.RESOURCE_PACKAGE_ROOT
            .replaceAll(".", "/")
            + "/" + "web-jsptaglibrary_1_2.dtd";

    private static TaglibTagHandlerRule rule = new TaglibTagHandlerRule();

    public TaglibConfig build(InputStream is) {
        SaxHandlerParser parser = createSaxHandlerParser();
        return (TaglibConfig) parser.parse(is);
    }

    private SaxHandlerParser createSaxHandlerParser() {
        SAXParser saxParser = SAXParserFactoryUtil.newSAXParser();
        SaxHandler handler = new SaxHandler(rule);
        handler.registerDtdPath(PUBLIC_ID_11, DTD_PATH_11);
        handler.registerDtdPath(PUBLIC_ID_12, DTD_PATH_12);
        return new SaxHandlerParser(handler, saxParser);
    }
}
