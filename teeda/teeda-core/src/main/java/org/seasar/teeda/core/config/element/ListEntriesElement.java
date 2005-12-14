package org.seasar.teeda.core.config.element;

import java.util.List;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public interface ListEntriesElement extends JsfConfig, NullValueHolder{

    public void setValueClass(String valueClassName);
    
    public void addValue(String value);
        
    public String getValueClass();
    
    public List getValues();
        
}
