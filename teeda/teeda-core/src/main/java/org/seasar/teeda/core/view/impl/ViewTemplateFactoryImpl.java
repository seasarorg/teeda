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
package org.seasar.teeda.core.view.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.seasar.framework.util.FileInputStreamUtil;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.teeda.core.exception.PathNotFoundRuntimeException;
import org.seasar.teeda.core.view.TagProcessor;
import org.seasar.teeda.core.view.TagProcessorTreeFactory;
import org.seasar.teeda.core.view.ViewTemplate;
import org.seasar.teeda.core.view.ViewTemplateFactory;

/**
 * @author higa
 * 
 */
public class ViewTemplateFactoryImpl implements ViewTemplateFactory {

    private TagProcessorTreeFactory tagProcessorTreeFactory;

    private ServletContext servletContext;

    private Map viewTemplates = new HashMap();

    public ViewTemplateFactoryImpl(
            TagProcessorTreeFactory tagProcessorTreeFactory,
            ServletContext servletContext) {

        this.tagProcessorTreeFactory = tagProcessorTreeFactory;
        this.servletContext = servletContext;
    }

    public synchronized ViewTemplate getViewTemplate(String path) {
        String realPath = servletContext.getRealPath(path);
        if (realPath != null) {
            File file = new File(realPath);
            if (file.exists()) {
                return getViewTemplateFromRealPath(path, file);
            }
        }
        return getViewTemplateFromResource(path);
    }

    protected ViewTemplate getViewTemplateFromRealPath(String path, File file) {
        ViewTemplate template = (ViewTemplate) viewTemplates.get(path);
        if (template != null && !template.isModified()) {
            return template;
        }
        TagProcessor rootTagProcessor = null;
        InputStream is = new BufferedInputStream(FileInputStreamUtil
                .create(file));
        try {
            rootTagProcessor = tagProcessorTreeFactory
                    .createTagProcessorTree(is);
        } finally {
            InputStreamUtil.close(is);
        }
        template = new ViewTemplateImpl(rootTagProcessor, file);
        viewTemplates.put(path, template);
        return template;
    }

    protected ViewTemplate getViewTemplateFromResource(String path) {
        ViewTemplate template = (ViewTemplate) viewTemplates.get(path);
        if (template != null) {
            return template;
        }
        TagProcessor rootTagProcessor = null;
        InputStream is = servletContext.getResourceAsStream(path);
        if (is == null) {
            throw new PathNotFoundRuntimeException(path);
        }
        try {
            rootTagProcessor = tagProcessorTreeFactory
                    .createTagProcessorTree(is);
        } finally {
            InputStreamUtil.close(is);
        }
        template = new ViewTemplateImpl(rootTagProcessor);
        viewTemplates.put(path, template);
        return template;
    }
}