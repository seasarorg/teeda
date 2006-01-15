package org.seasar.teeda.core.config.assembler.impl;

import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.event.PhaseListener;

import org.seasar.teeda.core.config.assembler.LifecycleAssembler;
import org.seasar.teeda.core.config.assembler.LifecycleChildAssembler;
import org.seasar.teeda.core.util.ClassUtil;
import org.seasar.teeda.core.util.DIContainerUtil;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class SimpleLifecycleAssembler extends LifecycleAssembler {

    private LifecycleChildAssembler child_;

    public SimpleLifecycleAssembler(List lifecycles) {
        super(lifecycles);
    }

    protected void setupChildAssembler() {
        child_ = new LifecycleChildAssembler(getLifecycles(),
                getExternalContext()) {

            protected void doAssemble(String targetName) {
                PhaseListener phaseListener = (PhaseListener)ClassUtil
                        .newInstance(targetName);
                getLifecycle().addPhaseListener(phaseListener);
            }

        };
    }

    public void assemble() {
        child_.assemble();
    }

}
