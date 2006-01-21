/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.faces.context.ExternalContext;

import org.seasar.framework.container.factory.ResourceResolver;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.JarFileUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.config.AbstractFacesConfigurator;

public class MetaInfFacesConfigurator extends AbstractFacesConfigurator {

    private ExternalContext externalContext_;

    private String path_ = JsfConstants.WEB_INF_LIB;

    public MetaInfFacesConfigurator(ExternalContext externalContext) {
        externalContext_ = externalContext;
        setResourceResolver(new WebAppJarResourceResolver(externalContext_));
    }

    public String getPath() {
        return path_;
    }

    public void setPath(String path) {
        path_ = path;
    }

    protected static class WebAppJarResourceResolver implements
            ResourceResolver {

        private ExternalContext context_;

        public WebAppJarResourceResolver(ExternalContext context) {
            context_ = context;
        }

        public InputStream getInputStream(String path) {

            Set resourcePaths = context_.getResourcePaths(path);
            if (resourcePaths == null) {
                return null;
            }
            String jarPath = null;
            InputStream is = null;
            try {
                for (Iterator itr = resourcePaths.iterator(); itr.hasNext();) {
                    jarPath = (String) itr.next();
                    if (isJarFile(jarPath)) {
                        JarFile jar = createJarInstance(jarPath);
                        JarEntry jarEntry = jar
                                .getJarEntry(JsfConstants.FACES_CONFIG_RESOURCES);
                        if (jarEntry != null) {
                            is = JarFileUtil.getInputStream(jar, jarEntry);
                        }
                    }
                }
                return is;
            } finally {
                InputStreamUtil.close(is);
            }
        }

        public static boolean isJarFile(String path) {
            return (path != null) && path.endsWith(JsfConstants.JAR_POSTFIX);
        }

        protected static JarFile createJarInstance(String path) {
            try {
                return new JarFile(path);
            } catch (IOException e) {
                return null;
            }
        }
    }
}
