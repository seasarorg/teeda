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
package org.seasar.teeda.core.config;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.seasar.framework.container.factory.ClassPathResourceResolver;
import org.seasar.framework.container.factory.ResourceResolver;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.SAXParserFactoryUtil;
import org.seasar.framework.xml.SaxHandler;
import org.seasar.framework.xml.SaxHandlerParser;
import org.seasar.framework.xml.TagHandlerRule;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.rule.FacesConfigTagHandlerRule;

/**
 * @author shot
 */
public abstract class AbstractFacesConfigurator implements FacesConfigurator{

    private static final String FACES_CONFIG_1_1 = "-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN";
    
    private static final String FACES_CONFIG_1_0 = "-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.0//EN";
    
    private static final String FACES_1_1_DTD_PATH = "org/seasar/teeda/core/resource/web-facesconfig_1_1.dtd";
    
    private static final String FACES_1_0_DTD_PATH = "org/seasar/teeda/core/resource/web-facesconfig_1_0.dtd";

    protected ResourceResolver resourceResolver_ = new ClassPathResourceResolver();

    protected TagHandlerRule rule_ = new FacesConfigTagHandlerRule();

    public AbstractFacesConfigurator(){
    }
    
    public FacesConfig configure(){
        String path = getPath();
        SaxHandlerParser parser = createSaxHandlerParser();
        InputStream is = resourceResolver_.getInputStream(path);
        if(is == null){
            return null;
        }
        try {
            return (FacesConfig) parser.parse(is);
        }finally {
            InputStreamUtil.close(is);
        }
    }
    
    protected final SaxHandlerParser createSaxHandlerParser(){
        final SAXParserFactory factory = SAXParserFactoryUtil.newInstance();
        factory.setValidating(true);

        final SAXParser saxParser = SAXParserFactoryUtil.newSAXParser(factory);

        final SaxHandler handler = new SaxHandler(rule_);
        handler.registerDtdPath(FACES_CONFIG_1_1, FACES_1_1_DTD_PATH);
        handler.registerDtdPath(FACES_CONFIG_1_0, FACES_1_0_DTD_PATH);
        
        SaxHandlerParser parser = new SaxHandlerParser(handler, saxParser);
        return parser;
    }
    
    public void setResourceResolver(ResourceResolver resourceResolver){
        resourceResolver_ = resourceResolver;
    }
    
    public ResourceResolver getResourceResolver(){
    	return resourceResolver_;
    }
    
    public void setTagHandlerRule(TagHandlerRule rule){
        rule_ = rule;
    }
    
    public TagHandlerRule getTagHandlerRule(){
    	return rule_;
    }
    
    protected abstract String getPath();
    
}
