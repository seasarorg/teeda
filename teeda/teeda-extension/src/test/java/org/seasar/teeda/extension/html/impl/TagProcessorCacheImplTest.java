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
package org.seasar.teeda.extension.html.impl;

import java.io.File;

import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.framework.unit.S2FrameworkTestCase;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.core.application.impl.TeedaStateManagerImpl;
import org.seasar.teeda.extension.html.TagProcessor;

/**
 * @author higa
 *
 */
public class TagProcessorCacheImplTest extends S2FrameworkTestCase {
	
    public void testUpdateTagProcessor() throws Exception {
        NamingConventionImpl convention = new NamingConventionImpl();
        String rootPath = "/" + ClassUtil.getPackageName(getClass()).replace('.', '/');
        convention.setViewRootPath(rootPath);
        convention.setViewExtension(".html");
        String path = rootPath + "/foo.html";
        
        PageDescCacheImpl pageDescCache = new PageDescCacheImpl();
        pageDescCache.setNamingConvention(convention);
        pageDescCache.setContainer(getContainer());
        register(FooPage.class, "fooPage");
        
        ActionDescCacheImpl actionDescCache = new ActionDescCacheImpl();
        actionDescCache.setNamingConvention(convention);
        actionDescCache.setContainer(getContainer());
        register(FooAction.class, "fooAction");
        
        HtmlDescCacheImpl htmlDescCache = new HtmlDescCacheImpl();
        htmlDescCache.setServletContext(getServletContext());
        
        TagProcessorCacheImpl tagProcessorCache = new TagProcessorCacheImpl();
        tagProcessorCache.setHtmlDescCache(htmlDescCache);
        tagProcessorCache.setPageDescCache(pageDescCache);
        tagProcessorCache.setActionDescCache(actionDescCache);
        tagProcessorCache.setAssembler(new TagProcessorAssemblerImpl());
        tagProcessorCache.setStateManager(new TeedaStateManagerImpl());
        
        TagProcessor processor = tagProcessorCache.updateTagProcessor(path);
        
        assertNotNull("1", processor);
        assertSame("2", processor, tagProcessorCache.updateTagProcessor(path));
        File file = ResourceUtil.getResourceAsFile(ClassUtil.getResourcePath(FooPage.class));
        Thread.sleep(1000);
        file.setLastModified(System.currentTimeMillis());
        assertNotSame("3", processor, tagProcessorCache.updateTagProcessor(path));
    }
}