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
package org.seasar.teeda.core.config.faces.handler;

import org.seasar.framework.xml.SaxHandler;
import org.seasar.framework.xml.SaxHandlerParser;
import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.framework.xml.TagHandlerRule;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.faces.rule.FacesConfigTagHandlerRule;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.xml.sax.Attributes;

public abstract class TagHandlerTestCase extends TeedaTestCase {

    private TagHandlerContext context_;

    public TagHandlerTestCase() {
        super();
    }

    public TagHandlerTestCase(String name) {
        super(name);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        context_ = null;
    }

    private static final String FACES_CONFIG_1_1 = "-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN";

    // private static final String FACES_CONFIG_1_0 = "-//Sun Microsystems,
    // Inc.//DTD JavaServer Faces Config 1.0//EN";

    private static final String FACES_1_1_DTD_PATH = "org/seasar/teeda/core/resource/web-facesconfig_1_1.dtd";

    // private static final String FACES_1_0_DTD_PATH =
    // "org/seasar/teeda/core/resource/web-facesconfig_1_0.dtd";

    public FacesConfig parse(String path) {
        TagHandlerRule rule = new FacesConfigTagHandlerRule();
        SaxHandler handler = new SaxHandler(rule);
        handler.registerDtdPath(FACES_CONFIG_1_1, FACES_1_1_DTD_PATH);
        // handler.registerDtdPath(FACES_CONFIG_1_0, FACES_1_0_DTD_PATH);

        SaxHandlerParser parser = new SaxHandlerParser(handler);
        Object o = parser.parse(convertPath(path));
        return (FacesConfig) o;
    }

    protected final TagHandlerContext getContext() {
        if (context_ == null) {
            context_ = new TagHandlerContext();
        }
        return context_;
    }

    protected static class NullAttributes implements Attributes {

        public int getLength() {
            return 0;
        }

        public String getURI(int arg0) {
            return null;
        }

        public String getLocalName(int arg0) {
            return null;
        }

        public String getQName(int arg0) {
            return null;
        }

        public String getType(int arg0) {
            return null;
        }

        public String getValue(int arg0) {
            return null;
        }

        public int getIndex(String arg0, String arg1) {
            return 0;
        }

        public int getIndex(String arg0) {
            return 0;
        }

        public String getType(String arg0, String arg1) {
            return null;
        }

        public String getType(String arg0) {
            return null;
        }

        public String getValue(String arg0, String arg1) {
            return null;
        }

        public String getValue(String arg0) {
            return null;
        }

    }
}
