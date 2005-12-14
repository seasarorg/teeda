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


abstract class AbstractFacesConfigurator implements FacesConfigurator{

    private static final String FACES_CONFIG_1_1 = "-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN";
    
    private static final String FACES_CONFIG_1_0 = "-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.0//EN";
    
    private static final String FACES_1_1_DTD_PATH = "org/seasar/teeda/core/resource/web-facesconfig_1_1.dtd";
    
    private static final String FACES_1_0_DTD_PATH = "org/seasar/teeda/core/resource/web-facesconfig_1_0.dtd";

    protected ResourceResolver resourceResolver_;

    protected TagHandlerRule rule_;

    public AbstractFacesConfigurator(){
        resourceResolver_ = new ClassPathResourceResolver();
        rule_ = new FacesConfigTagHandlerRule();
    }
    
    public FacesConfig configure(){
        SaxHandlerParser parser = createSaxHandlerParser();
        InputStream is = resourceResolver_.getInputStream(getPath());
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
    
    public void setTagHandlerRule(TagHandlerRule rule){
        rule_ = rule;
    }
    
    protected abstract String getPath();
}
