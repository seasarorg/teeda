package org.seasar.teeda.core.config.assembler.impl;

import java.util.Iterator;
import java.util.Map;

import javax.faces.application.Application;

import org.seasar.teeda.core.config.assembler.ComponentAssembler;
import org.seasar.teeda.core.config.element.ComponentElement;
import org.seasar.teeda.core.util.ApplicationUtil;
import org.seasar.teeda.core.util.IteratorUtil;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class DefaultComponentAssembler extends ComponentAssembler {

    private Application application_;
    public DefaultComponentAssembler(Map components) {
        super(components);
    }

    protected void setupBeforeAssemble() {
        application_ = ApplicationUtil.getApplicationFromFactory();
    }

    public void assemble() {
        String componentType = null;
        String componentClassName = null;
        ComponentElement component = null;
        for(Iterator itr = IteratorUtil.getIterator(getComponents());itr.hasNext();){
            Map.Entry entry = (Map.Entry)itr.next();
            componentType = (String)entry.getKey();
            component = (ComponentElement)entry.getValue();
            componentClassName = component.getComponentClass();
            application_.addComponent(componentType, componentClassName);
        }
    }

}
