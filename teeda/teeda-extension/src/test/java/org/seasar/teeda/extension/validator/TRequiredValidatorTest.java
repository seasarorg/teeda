package org.seasar.teeda.extension.validator;

import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.exception.ExtendValidatorException;

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
        validator.setTarget("aaa, bbb");
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

    public void testSaveAndRestore() throws Exception {
        TRequiredValidator validator = new TRequiredValidator();
        validator.setTarget("aaa");
        validator.setMessageId("hoge");
        Object state = validator.saveState(getFacesContext());
        validator = new TRequiredValidator();
        validator.restoreState(getFacesContext(), state);
        assertEquals("aaa", validator.getTarget());
        assertEquals("hoge", validator.getMessageId());
    }

    public void testValidate_withMessageId() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        mock.getAttributes().put("label", "abc");
        TRequiredValidator validator = new TRequiredValidator();
        validator.setMessageId("hoge");
        try {
            validator.validate(getFacesContext(), mock, "");
            fail();
        } catch (ExtendValidatorException expected) {
            assertNotNull(expected.getFacesMessage());
            assertEquals("hoge", expected.getMesssageIds()[0]);
        }
    }

}
