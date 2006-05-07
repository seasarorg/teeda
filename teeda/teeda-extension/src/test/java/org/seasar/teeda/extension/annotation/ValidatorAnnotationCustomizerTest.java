package org.seasar.teeda.extension.annotation;

import junit.framework.TestCase;

import org.seasar.framework.container.ComponentDef;
import org.seasar.teeda.core.mock.NullComponentDefImpl;
import org.seasar.teeda.core.resource.ValidatorResource;

public class ValidatorAnnotationCustomizerTest extends TestCase {

    public void testCustomize() throws Exception {
        final boolean[] calls = new boolean[] { false };
        ValidatorAnnotationCustomizer customizer = new ValidatorAnnotationCustomizer();
        customizer
                .setValidatorAnnotationHandler(new ValidatorAnnotationHandler() {

                    public void registerValidator(ComponentDef componentDef) {
                        calls[0] = true;
                    }

                    public void setValidatorResource(ValidatorResource resources) {
                    }

                    public ValidatorResource getValidatorResource() {
                        return null;
                    }

                    public void addIgnoreSuffix(String suffix) {
                    }

                });
        customizer.customize(new NullComponentDefImpl());
        assertTrue(calls[0]);

    }

}
