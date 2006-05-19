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

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.core.application.TeedaStateManager;
import org.seasar.teeda.extension.html.HtmlDesc;
import org.seasar.teeda.extension.html.HtmlDescCache;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.PageDescCache;
import org.seasar.teeda.extension.html.TagProcessor;
import org.seasar.teeda.extension.html.TagProcessorAssembler;
import org.seasar.teeda.extension.html.TagProcessorCache;

/**
 * @author higa
 * 
 */
public class TagProcessorCacheImpl implements TagProcessorCache {

    private Map processors = new HashMap();
    
    private HtmlDescCache htmlDescCache;
    
    private PageDescCache pageDescCache;
    
    private TagProcessorAssembler assembler;
    
    private TeedaStateManager teedaStateManager;
    
    public void setHtmlDescCache(HtmlDescCache htmlDescCache) {
        this.htmlDescCache = htmlDescCache;
    }

    public void setPageDescCache(PageDescCache pageDescCache) {
        this.pageDescCache = pageDescCache;
    }

    public void setAssembler(TagProcessorAssembler assembler) {
        this.assembler = assembler;
    }

    public void setTeedaStateManager(TeedaStateManager teedaStateManager) {
        this.teedaStateManager = teedaStateManager;
    }

    public synchronized TagProcessor getTagProcessor(String viewId) {
        boolean created = false;
        HtmlDesc htmlDesc = htmlDescCache.getHtmlDesc(viewId);
        if (htmlDesc == null || htmlDesc.isModified()) {
            htmlDesc = htmlDescCache.createHtmlDesc(viewId);
            created = true;
        }
        PageDesc pageDesc = pageDescCache.getPageDesc(viewId);
        if (pageDesc == null || pageDesc.isModified()) {
            pageDesc = pageDescCache.createPageDesc(viewId);
            created = true;
        }
        if (created) {
            processors.put(viewId, assembler.assemble(
                    htmlDesc, pageDesc));
            teedaStateManager.removeSerializedView(viewId);
        }
        return (TagProcessor) processors.get(viewId);
    }
}
