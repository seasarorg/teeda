package org.seasar.teeda.core.config;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.seasar.teeda.core.config.element.FacesConfig;

public class FacesConfigBuilderImpl implements FacesConfigBuilder{

    private List configurators_ = Collections.synchronizedList(new LinkedList());
    
	public FacesConfigBuilderImpl(){
	}
	
    public FacesConfig createFacesConfigs(){
        List configs = new LinkedList();
        FacesConfigurator configurator = null;
        for(Iterator itr = configurators_.iterator();itr.hasNext();){
            configurator = (FacesConfigurator)itr.next();
            FacesConfig config = configurator.configure();
            if(config != null){
                configs.add(config);
            }
        }
        return FacesConfigUtil.collectAllFacesConfig(configs);
    }

    public void addFacesConfigurator(FacesConfigurator configurator) {
        configurators_.add(configurator);
    }

}
