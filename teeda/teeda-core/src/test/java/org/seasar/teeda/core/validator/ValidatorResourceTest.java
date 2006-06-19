package org.seasar.teeda.core.validator;

import javax.faces.validator.DoubleRangeValidator;

import junit.framework.TestCase;

public class ValidatorResourceTest extends TestCase {

    public void tearDown() {
        ValidatorResource.removeAll();
    }

    public void testGetValidator() throws Exception {
        ValidatorResource.addValidator("#{a.name}", new DoubleRangeValidator());
        assertNotNull(ValidatorResource.getValidator("#{a.name}"));
        assertTrue(ValidatorResource.getValidator("#{a.name}") instanceof DoubleRangeValidator);
    }
}
