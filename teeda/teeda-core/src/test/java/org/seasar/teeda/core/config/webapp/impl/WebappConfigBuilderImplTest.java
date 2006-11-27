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
package org.seasar.teeda.core.config.webapp.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.core.config.webapp.WebappConfigBuilder;
import org.seasar.teeda.core.config.webapp.element.ContextParamElement;
import org.seasar.teeda.core.config.webapp.element.FilterElement;
import org.seasar.teeda.core.config.webapp.element.InitParamElement;
import org.seasar.teeda.core.config.webapp.element.ServletElement;
import org.seasar.teeda.core.config.webapp.element.TaglibElement;
import org.seasar.teeda.core.config.webapp.element.WebappConfig;

/**
 * @author manhole
 */
public class WebappConfigBuilderImplTest extends TestCase {

    public void testContextParam() throws Exception {
        // ## Act ##
        WebappConfig webappConfig = buildWebappConfig("WebappConfigBuilderImplTest-ContextParam-web.xml");

        // ## Assert ##
        List contextParams = webappConfig.getContextParamElements();
        assertEquals(2, contextParams.size());
        assertEquals(true, contextParams.get(0) instanceof ContextParamElement);

        ContextParamElement contextParam = webappConfig
                .getContextParamElementByParamName("javax.faces.CONFIG_FILES");
        assertNotNull(contextParam);

        assertEquals("/WEB-INF/faces-config.xml", contextParam.getParamValue());
        assertEquals("javax.faces.CONFIG_FILES", contextParam.getParamName());

        assertEquals(".html", webappConfig.getContextParamElementByParamName(
                "javax.faces.DEFAULT_SUFFIX").getParamValue());
    }

    public void testFilter() throws Exception {
        // ## Act ##
        WebappConfig webappConfig = buildWebappConfig("WebappConfigBuilderImplTest-Filter-web.xml");

        // ## Assert ##
        List filterElements = webappConfig.getFilterElements();
        assertEquals(2, filterElements.size());
        assertEquals(true, filterElements.get(0) instanceof FilterElement);

        FilterElement filter = webappConfig
                .getFilterElementByFilterName("encodingfilter");
        assertNotNull(filter);
        assertEquals("encodingfilter", filter.getFilterName());
        assertEquals("org.seasar.extension.filter.EncodingFilter", filter
                .getFilterClass());
        InitParamElement initParam = filter
                .getInitParamElementByParamName("encoding");
        assertNotNull(initParam);
        assertEquals("encoding", initParam.getParamName());
        assertEquals("UTF-8", initParam.getParamValue());
    }

    public void testServlet() throws Exception {
        // ## Act ##
        WebappConfig webappConfig = buildWebappConfig("WebappConfigBuilderImplTest-Servlet-web.xml");

        // ## Assert ##
        List servletElements = webappConfig.getServletElements();
        assertEquals(2, servletElements.size());
        assertEquals(true, servletElements.get(0) instanceof ServletElement);

        ServletElement servlet = webappConfig
                .getServletElementByServletName("s2servlet");
        InitParamElement initParam = servlet
                .getInitParamElementByParamName("debug");
        assertNotNull(initParam);
        assertEquals("debug", initParam.getParamName());
        assertEquals("true", initParam.getParamValue());
    }

    public void testTaglib() throws Exception {
        // ## Act ##
        WebappConfig webappConfig = buildWebappConfig("WebappConfigBuilderImplTest-Taglib-web.xml");

        // ## Assert ##
        List taglibElements = webappConfig.getTaglibElements();
        assertEquals(2, taglibElements.size());

        TaglibElement taglib = (TaglibElement) taglibElements.get(0);
        assertEquals("http://www.seasar.org/jsf", taglib.getTaglibUri());
        assertEquals("/WEB-INF/s2jsf.tld", taglib.getTaglibLocation());
    }

    private WebappConfig buildWebappConfig(String webXml) throws IOException {
        // ## Arrange ##
        WebappConfigBuilder builder = new WebappConfigBuilderImpl();
        final String path = getClass().getPackage().getName().replace('.', '/')
                + "/" + webXml;
        InputStream is = ResourceUtil.getResourceAsStream(path);

        // ## Act ##
        try {
            WebappConfig webappConfig = builder.build(is, path);
            assertNotNull(webappConfig);
            return webappConfig;
        } finally {
            InputStreamUtil.close(is);
        }
    }

}
