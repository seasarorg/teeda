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
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ActionDescCache;
import org.seasar.teeda.extension.html.HtmlDesc;
import org.seasar.teeda.extension.html.HtmlDescCache;
import org.seasar.teeda.extension.html.HtmlPathCache;
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

    private Map cache = new HashMap();

    private HtmlPathCache htmlPathCache;

    private HtmlDescCache htmlDescCache;

    private PageDescCache pageDescCache;

    private ActionDescCache actionDescCache;

    private TagProcessorAssembler assembler;

    private TeedaStateManager stateManager;

    public void setHtmlPathCache(HtmlPathCache htmlPathCache) {
        this.htmlPathCache = htmlPathCache;
    }

    public void setHtmlDescCache(HtmlDescCache htmlDescCache) {
        this.htmlDescCache = htmlDescCache;
    }

    public void setPageDescCache(PageDescCache pageDescCache) {
        this.pageDescCache = pageDescCache;
    }

    public void setActionDescCache(ActionDescCache actionDescCache) {
        this.actionDescCache = actionDescCache;
    }

    public void setAssembler(TagProcessorAssembler assembler) {
        this.assembler = assembler;
    }

    public void setStateManager(TeedaStateManager stateManager) {
        this.stateManager = stateManager;
    }

    public synchronized TagProcessor updateTagProcessor(String viewId) {
        boolean created = false;
        HtmlDesc htmlDesc = htmlDescCache.getHtmlDesc(viewId);
        if (htmlDesc == null) {
            String name = htmlPathCache.getName(viewId);
            htmlPathCache.setPath(name, viewId);
        }
        if (htmlDesc == null || htmlDesc.isModified()) {
            htmlDesc = htmlDescCache.createHtmlDesc(viewId);
            if (htmlDesc != null) {
                created = true;
            }
        }
        PageDesc pageDesc = pageDescCache.getPageDesc(viewId);
        if (pageDesc == null || pageDesc.isModified()) {
            pageDesc = pageDescCache.createPageDesc(viewId);
            if (pageDesc != null) {
                created = true;
            }
        }
        ActionDesc actionDesc = actionDescCache.getActionDesc(viewId);
        if (actionDesc == null || actionDesc.isModified()) {
            actionDesc = actionDescCache.createActionDesc(viewId);
            if (actionDesc != null) {
                created = true;
            }
        }
        if (created) {
            cache.put(viewId, assembler
                    .assemble(htmlDesc, pageDesc, actionDesc));
            stateManager.removeSerializedView(viewId);
        }
        return getTagProcessor(viewId);
    }

    public synchronized TagProcessor getTagProcessor(String viewId) {
        return (TagProcessor) cache.get(viewId);
    }
}
