package org.seasar.teeda.core.config.assembler.impl;

import java.util.Iterator;
import java.util.List;

import javax.faces.FactoryFinder;

import org.seasar.teeda.core.config.assembler.FactoriesAssembler;
import org.seasar.teeda.core.config.assembler.FactoryAssembler;
import org.seasar.teeda.core.config.element.FactoryElement;
import org.seasar.teeda.core.util.IteratorUtil;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class SimpleFactoriesAssembler extends FactoriesAssembler {

    private FactoryAssembler applicationFactory_;
    
    private FactoryAssembler facesContextFactory_;
    
    private FactoryAssembler lifecycleFactory_;
    
    private FactoryAssembler renderKitFactory_;
    
    public SimpleFactoriesAssembler(List factories) {
        super(factories);
    }

    protected void setupChildAssembler() {
        applicationFactory_ = new FactoryAssembler(){
            protected String getFactoryClassName() {
                return FactoryFinder.APPLICATION_FACTORY;
            }
        };
        
        facesContextFactory_ = new FactoryAssembler(){
            protected String getFactoryClassName() {
                return FactoryFinder.FACES_CONTEXT_FACTORY;
            }
        };
        
        lifecycleFactory_ = new FactoryAssembler(){
            protected String getFactoryClassName() {
                return FactoryFinder.LIFECYCLE_FACTORY;
            }
        };
        
        renderKitFactory_ = new FactoryAssembler(){
            protected String getFactoryClassName() {
                return FactoryFinder.RENDER_KIT_FACTORY;
            }
        };
        
        FactoryElement factory = null;
        for(Iterator itr = IteratorUtil.getIterator(getFactories());itr.hasNext();){
            factory = (FactoryElement)itr.next();
            applicationFactory_.setTargetFactories(factory.getApplicationFactories());
            facesContextFactory_.setTargetFactories(factory.getFacesContextFactories());
            lifecycleFactory_.setTargetFactories(factory.getLifecycleFactories());
            renderKitFactory_.setTargetFactories(factory.getRenderKitFactories());
        }
        
    }
    
    protected void assembleApplicationFactory(){
        applicationFactory_.assemble();
    }
    
    protected void assembleFacesContextFactory(){
        facesContextFactory_.assemble();
    }

    protected void assembleLifecycleFactory(){
        lifecycleFactory_.assemble();
    }

    protected void assembleRenderKitFactory(){
        renderKitFactory_.assemble();
    }

}

