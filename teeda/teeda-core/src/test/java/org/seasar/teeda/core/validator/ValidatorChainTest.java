package org.seasar.teeda.core.validator;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class ValidatorChainTest extends TeedaTestCase {

    public void testValidate() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false, false };
        Validator v1 = new Validator() {
            public void validate(FacesContext context, UIComponent component,
                    Object value) throws FacesException {
                calls[0] = true;
            }
        };
        Validator v2 = new Validator() {
            public void validate(FacesContext context, UIComponent component,
                    Object value) throws FacesException {
                calls[1] = true;
            }
        };
        ValidatorChain chain = new ValidatorChain();
        chain.add(v1);
        chain.add(v2);
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        
        // ## Act ##
        chain.validate(getFacesContext(), component, new Integer(2));
        
        // ## Assert ##
        assertTrue(calls[0]);
        assertTrue(calls[1]);
    }
    
    
    
}
