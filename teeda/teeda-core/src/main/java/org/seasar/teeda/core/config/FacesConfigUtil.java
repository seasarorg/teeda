package org.seasar.teeda.core.config;

import java.util.Iterator;
import java.util.List;

import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.impl.FacesConfigWrapperImpl;


public class FacesConfigUtil {

    private FacesConfigUtil(){
    }
    
    public static FacesConfig collectAllFacesConfig(List configs){
        isAllFacesConfig(configs);
        FacesConfig wrappedFacesConfig = new FacesConfigWrapperImpl(configs);
        return wrappedFacesConfig;
    }
    
    public static void isAllFacesConfig(List configs){
        if(configs == null){
            throw new IllegalArgumentException();
        }
        for(Iterator itr = configs.iterator();itr.hasNext();){
            if(!(itr.next() instanceof FacesConfig)){
                throw new IllegalStateException();
            }
        }
    }
}
