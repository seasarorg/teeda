package org.seasar.teeda.extension.validator;

import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class RequiredValidatorTest extends TeedaTestCase {

    public void testValidate() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        mock.getAttributes().put("label", "abc");
        RequiredValidator validator = new RequiredValidator();
        try {
            validator.validate(getFacesContext(), mock, "");
            fail();
        }catch(ValidatorException expected){
            assertNotNull(expected.getFacesMessage());
            System.out.println(expected.getFacesMessage().getDetail());
        }
    }
}
