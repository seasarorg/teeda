package org.seasar.teeda.core.config.element;

import java.util.List;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public interface PropertyHolder {

    public void addPropertyElement(PropertyElement property);
    
    public List getPropertyElements();
}
