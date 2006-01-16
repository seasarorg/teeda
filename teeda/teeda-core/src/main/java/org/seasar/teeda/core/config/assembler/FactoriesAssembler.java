package org.seasar.teeda.core.config.assembler;

import java.util.List;
import java.util.Collections;

import org.seasar.teeda.core.config.element.FactoryElement;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public abstract class FactoriesAssembler extends AbstractJsfAssembler {

    private List factories_ = Collections.EMPTY_LIST;
    
    public FactoriesAssembler(List factories){
        isAllSuitableJsfElement(factories, FactoryElement.class);
        factories_ = factories;
        setupBeforeAssemble();
    }

    protected final List getFactories(){
        return factories_;
    }
    
    public void assemble() {
        assembleApplicationFactory();
        assembleFacesContextFactory();
        assembleLifecycleFactory();
        assembleRenderKitFactory();
    }

    protected abstract void assembleApplicationFactory();

    protected abstract void assembleFacesContextFactory();

    protected abstract void assembleLifecycleFactory();

    protected abstract void assembleRenderKitFactory();

}