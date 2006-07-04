package org.seasar.teeda.extension.validator;

import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TRequiredValidatorTest extends TeedaTestCase {

    public void testValidate() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        mock.getAttributes().put("label", "abc");
        TRequiredValidator validator = new TRequiredValidator();
        try {
            validator.validate(getFacesContext(), mock, "");
            fail();
        } catch (ValidatorException expected) {
            assertNotNull(expected.getFacesMessage());
            System.out.println(expected.getFacesMessage().getDetail());
        }
    }

    public void testValidate_for() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        mock.getAttributes().put("label", "abc");
        TRequiredValidator validator = new TRequiredValidator();
        validator.setFor("aaa, bbb");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMIT_VALUE, "aaa");
        try {
            validator.validate(getFacesContext(), mock, "");
            fail();
        } catch (ValidatorException expected) {
            assertNotNull(expected.getFacesMessage());
            System.out.println(expected.getFacesMessage().getDetail());
        }
    }
}
