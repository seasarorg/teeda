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
import java.util.LinkedList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.webapp.FacesServlet;

import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.xml.SaxHandlerParser;
import org.seasar.teeda.core.config.faces.AbstractFacesConfigurator;
import org.seasar.teeda.core.config.faces.element.FacesConfig;


public class ConfigFilesFacesConfigurator extends AbstractFacesConfigurator {

    private static final String FACES_CONFIG_DELIMETER = ",";
    private ExternalContext context_;
    public ConfigFilesFacesConfigurator(ExternalContext context){
        context_ = context;
    }
    
    public FacesConfig configure() {
        List configs = new LinkedList();
        String path = getPath();
        if(path == null){
            return null;
        }
        String[] paths = StringUtil.split(path, FACES_CONFIG_DELIMETER);
        
        for(int i = 0;i < paths.length;i++){
            SaxHandlerParser parser = createSaxHandlerParser();
            InputStream is = resourceResolver_.getInputStream(paths[i].trim());
            try {
                configs.add(parser.parse(is));
            }finally {
                InputStreamUtil.close(is);
            }
        }
        return FacesConfigUtil.collectAllFacesConfig(configs);
    }
    
    public String getPath() {
        String paths = context_.getInitParameter(FacesServlet.CONFIG_FILES_ATTR);
        return paths;
    }

}
