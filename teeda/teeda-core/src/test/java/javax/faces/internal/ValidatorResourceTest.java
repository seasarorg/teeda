package javax.faces.internal;

import javax.faces.validator.DoubleRangeValidator;
import javax.faces.validator.LengthValidator;

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
    
    public void testAddValidator() throws Exception {
        ValidatorResource.addValidator("#{a.name}", new DoubleRangeValidator());
        ValidatorResource.addValidator("#{a.name}", new LengthValidator());
        assertTrue(ValidatorResource.getValidator("#{a.name}") instanceof ValidatorChain);
        ValidatorChain chain = (ValidatorChain) ValidatorResource.getValidator("#{a.name}");
        assertTrue(chain.getValidator(0) instanceof DoubleRangeValidator);
        assertTrue(chain.getValidator(1) instanceof LengthValidator);
    }
}
