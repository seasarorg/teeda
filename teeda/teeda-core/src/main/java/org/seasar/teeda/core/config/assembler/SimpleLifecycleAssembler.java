package org.seasar.teeda.core.config.assembler;

import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.event.PhaseListener;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.teeda.core.util.ClassUtil;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class SimpleLifecycleAssembler extends LifecycleAssembler {

    private LifecycleChildAssembler child_;
    
    public SimpleLifecycleAssembler(List lifecycles) {
        super(lifecycles);
    }

    protected void setupChildAssembler() {
        child_ = new LifecycleChildAssembler(getLifecycles(), getExternalContext()){

            protected void doAssemble(String targetName) {
                PhaseListener phaseListener = (PhaseListener)ClassUtil.newInstance(targetName);
                getLifecycle().addPhaseListener(phaseListener);
            }
            
        };
    }

    public void assemble() {
        child_.assemble();
    }

    public ExternalContext getExternalContext(){
        S2Container container = SingletonS2ContainerFactory.getContainer();
        ExternalContext externalContext = (ExternalContext)container.getComponent(ExternalContext.class);
        return externalContext;
    }
}
