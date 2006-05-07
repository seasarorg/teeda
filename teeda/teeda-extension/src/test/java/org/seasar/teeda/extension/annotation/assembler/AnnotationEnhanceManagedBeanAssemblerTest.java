package org.seasar.teeda.extension.annotation.assembler;

import java.util.HashMap;
import java.util.Map;

import javax.faces.validator.LengthValidator;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.teeda.core.config.faces.element.ManagedBeanElement;
import org.seasar.teeda.core.config.faces.element.impl.ManagedBeanElementImpl;
import org.seasar.teeda.core.resource.ValidatorResource;
import org.seasar.teeda.core.resource.ValidatorResourceImpl;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.annotation.ConstantValidatorAnnotationHandler;

public class AnnotationEnhanceManagedBeanAssemblerTest extends TeedaTestCase {

    public void testRegisterManagedBean() throws Exception {
        getContainer().register(LengthValidator.class, "length");
        Map map = new HashMap();
        ManagedBeanElement mb = new ManagedBeanElementImpl();
        mb.setManagedBeanName("a");
        mb.setManagedBeanScope("request");
        mb.setManagedBeanClass(A.class.getName());
        AnnotationEnhanceManagedBeanAssembler assembler = new AnnotationEnhanceManagedBeanAssembler(
                map);
        ConstantValidatorAnnotationHandler handler = new ConstantValidatorAnnotationHandler();
        ValidatorResource resource = new ValidatorResourceImpl();
        handler.setValidatorResource(resource);
        assembler.setAnnotationHandler(handler);

        ComponentDef cDef = new ComponentDefImpl(A.class);
        cDef.setComponentName("a");
        assembler.registerManagedBean(cDef, Scope.REQUEST);

        assertEquals(A.class.getName(), getManagedBeanFactory().getManagedBean("a").getClass().getName());
        assertTrue(resource.getValidator("#{a.name}") instanceof LengthValidator);
    }

    public static class A {

        public static final String name_VALIDATOR = "{length, minimum = 2}";

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
