package org.seasar.teeda.core.config.element;


/**
 * @author Shinpei Ohtani(aka shot)
 */
public interface MapEntryElement extends JsfConfig, NullValueHolder {

    public void setKey(String key);
    
    public void setValue(String value);
    
    public String getKey();
    
    public String getValue();
        
}
