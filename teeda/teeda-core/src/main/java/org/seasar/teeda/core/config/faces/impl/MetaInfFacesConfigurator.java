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
package org.seasar.teeda.core.config.faces.impl;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.seasar.framework.container.factory.ResourceResolver;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.URLUtil;
import org.seasar.framework.xml.SaxHandlerParser;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.config.faces.AbstractFacesConfigurator;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.util.ClassLoaderUtil;

public class MetaInfFacesConfigurator extends AbstractFacesConfigurator {

    private String path_ = JsfConstants.WEB_INF_LIB;

    public MetaInfFacesConfigurator() {
        setResourceResolver(new MetaInfResourceResolver());
    }

    public FacesConfig configure() {
        String path = getPath();
        SaxHandlerParser parser = createSaxHandlerParser();
        List list = new ArrayList();
        for (;;) {
            InputStream is = resourceResolver_.getInputStream(path);
            if (is == null) {
                break;
            }
            try {
                FacesConfig config = (FacesConfig) parser.parse(is);
                list.add(config);
            } finally {
                InputStreamUtil.close(is);
            }
        }
        return FacesConfigUtil.collectAllFacesConfig(list);
    }

    public String getPath() {
        return path_;
    }

    public void setPath(String path) {
        path_ = path;
    }

    protected static class MetaInfResourceResolver implements ResourceResolver {

        private Iterator resources_;

        private boolean inited_ = false;

        public MetaInfResourceResolver() {
        }

        public InputStream getInputStream(String path) {
            if (!inited_) {
                initialize(path);
                inited_ = true;
            }
            if (hasNext()) {
                URL url = (URL) resources_.next();
                return URLUtil.openStream(url);
            } else {
                return null;
            }
        }

        private boolean hasNext() {
            return resources_ != null && resources_.hasNext();
        }

        private void initialize(String path) {
            ClassLoader loader = ClassLoaderUtil.getClassLoader(this);
            List list = new LinkedList();
            for (Iterator itr = ClassLoaderUtil.getResources(loader,
                    JsfConstants.FACES_CONFIG_RESOURCES); itr.hasNext();) {
                URL url = (URL) itr.next();
                if (url.toExternalForm().indexOf(path) != -1) {
                    list.add(0, url);
                }
            }
            resources_ = list.iterator();
        }

    }
}
