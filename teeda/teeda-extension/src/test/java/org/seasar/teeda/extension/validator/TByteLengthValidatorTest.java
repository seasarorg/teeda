package org.seasar.teeda.extension.validator;

import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TByteLengthValidatorTest extends TeedaTestCase {

    public void testConstants() throws Exception {
        assertEquals(
                "org.seasar.teeda.core.validator.ByteLengthValidator.MAXIMUM",
                TByteLengthValidator.MAXIMUM_MESSAGE_ID);
        assertEquals(
                "org.seasar.teeda.core.validator.ByteLengthValidator.MINIMUM",
                TByteLengthValidator.MINIMUM_MESSAGE_ID);
        assertEquals("teeda.core.ByteLength", TByteLengthValidator.VALIDATOR_ID);
    }

    public void testValidate_greaterThanMaximum() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setMaximum(5);
        validator.setMinimum(1);
        validator.setCharSet("Shift_JIS");
        try {
            validator.validate(getFacesContext(), mock, "あああ");
            fail();
        } catch (ValidatorException expected) {
            assertNotNull(expected.getMessage());
            success();
        }
    }

    public void testValidate_lowerThanMinimum() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setMaximum(5);
        validator.setMinimum(3);
        validator.setCharSet("Shift_JIS");
        try {
            validator.validate(getFacesContext(), mock, "あ");
            fail();
        } catch (ValidatorException expected) {
            assertNotNull(expected.getMessage());
            success();
        }
    }

    public void testGetConvertedValueLength() throws Exception {
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setCharSet("NO_SUCH_ENCODE");
        try {
            validator.getConvertedValueLength("aaaa");
        } catch (ValidatorException expected) {
            success();
        }
    }

    public void testValidate_validateTargetPointed() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setFor("aaa");
        validator.setMinimum(4);
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "aaa");
        try {
            validator.validate(getFacesContext(), new MockUIComponent(), "111");
            fail();
        } catch (ValidatorException expected) {
            assertNotNull(expected.getMessage());
        }
    }

    public void testValidate_validateTargetNotPointed() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setFor("aaa");
        validator.setMinimum(4);
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "bbb");
        try {
            validator.validate(getFacesContext(), new MockUIComponent(), "111");
            success();
        } catch (ValidatorException e) {
            fail();
        }
    }

    public void testSaveAndRestore() throws Exception {
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setCharSet("Shift_JIS");
        validator.setFor("aaa");
        Object state = validator.saveState(getFacesContext());
        validator = new TByteLengthValidator();
        validator.restoreState(getFacesContext(), state);
        assertEquals("Shift_JIS", validator.getCharSet());
        assertEquals("aaa", validator.getFor());
    }

}
