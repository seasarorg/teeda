package org.seasar.teeda.extension.annotation.assembler;

import java.util.Map;

import org.seasar.framework.container.ComponentDef;
import org.seasar.teeda.core.config.faces.assembler.impl.DefaultManagedBeanAssembler;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.extension.annotation.ValidatorAnnotationHandler;

public class AnnotationEnhanceManagedBeanAssembler extends
        DefaultManagedBeanAssembler {

    private ValidatorAnnotationHandler annotationHandler_;

    public AnnotationEnhanceManagedBeanAssembler(Map managedBeans) {
        super(managedBeans);
    }

    protected void registerManagedBean(ComponentDef componentDef, Scope scope) {
        getAnnotationHandler().registerValidator(componentDef);
        getManagedBeanFactory().registerManagedBean(componentDef, scope);
    }

    public ValidatorAnnotationHandler getAnnotationHandler() {
        return annotationHandler_;
    }

    public void setAnnotationHandler(ValidatorAnnotationHandler annotationHandler) {
        annotationHandler_ = annotationHandler;
    }

}
