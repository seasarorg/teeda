package org.seasar.teeda.core.config.element;

import java.util.List;


/**
 * @author Shinpei Ohtani(aka shot)
 * 
 */
public interface MapEntriesElement extends JsfConfig {

    public void setKeyClass(String keyClassName);

    public void setValueClass(String valueClassName);
    
    public void addMapEntry(MapEntryElement mapEntry);

    public String getKeyClass();
    
    public String getValueClass();
    
    public List getMapEntries();

}
