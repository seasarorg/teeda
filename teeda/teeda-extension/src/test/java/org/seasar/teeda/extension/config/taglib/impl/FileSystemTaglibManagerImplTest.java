/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import junit.framework.TestCase;

import org.seasar.framework.mock.servlet.MockServletContextImpl;
import org.seasar.teeda.extension.config.taglib.TaglibElementBuilder;

/**
 * @author manhole
 */
public class FileSystemTaglibManagerImplTest extends TestCase {

    public void test1() throws Exception {
        // ## Arrange ##
        FileSystemTaglibManagerImpl taglibManager = new FileSystemTaglibManagerImpl();
        MockServletContextImpl servletContext = new MockServletContextImpl(null);
        taglibManager.setServletContext(servletContext);
        TaglibElementBuilder builder = new TaglibElementBuilderImpl();
        taglibManager.setBuilder(builder);

        // ## Act ##
        taglibManager
                .init("org/seasar/teeda/extension/config/taglib/impl/FileSystemTaglibManagerTest-resource");

        // ## Assert ##
        assertEquals(true, taglibManager.hasTaglibElement("a_uri"));
        assertEquals(true, taglibManager.hasTaglibElement("b_uri"));
        assertEquals(true, taglibManager.hasTaglibElement("c_uri"));
        assertEquals(true, taglibManager.hasTaglibElement("d_uri"));
        assertEquals(false, taglibManager.hasTaglibElement("e_uri"));
    }
}
