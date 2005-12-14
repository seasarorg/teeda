package org.seasar.teeda.core.config;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.webapp.FacesServlet;

import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.xml.SaxHandlerParser;
import org.seasar.teeda.core.config.element.FacesConfig;


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
            InputStream is = resourceResolver_.getInputStream(paths[i]);
            try {
                configs.add(parser.parse(is));
            }finally {
                InputStreamUtil.close(is);
            }
        }
        return FacesConfigUtil.collectAllFacesConfig(configs);
    }
    
    protected String getPath() {
        String paths = context_.getInitParameter(FacesServlet.CONFIG_FILES_ATTR);
        return paths;
    }

}
