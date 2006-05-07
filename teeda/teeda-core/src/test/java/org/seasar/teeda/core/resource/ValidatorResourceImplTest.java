package org.seasar.teeda.core.resource;

import javax.faces.validator.DoubleRangeValidator;

import org.seasar.teeda.core.resource.ValidatorResource;
import org.seasar.teeda.core.resource.ValidatorResourceImpl;

import junit.framework.TestCase;

public class ValidatorResourceImplTest extends TestCase {

    public void testGetValidator() throws Exception {
        ValidatorResource resource = new ValidatorResourceImpl();
        resource.addValidatorResource("#{a.name}", new DoubleRangeValidator());
        assertNotNull(resource.getValidator("#{a.name}"));
        assertTrue(resource.getValidator("#{a.name}") instanceof DoubleRangeValidator);
    }
}
