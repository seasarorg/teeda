package org.seasar.teeda.extension.validator;

import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.validator.ByteLengthValidator;

public class ByteLengthValidatorTest extends TeedaTestCase {

    public void testConstants() throws Exception {
        assertEquals("org.seasar.teeda.core.validator.ByteLengthValidator.MAXIMUM",
                ByteLengthValidator.MAXIMUM_MESSAGE_ID);
        assertEquals("org.seasar.teeda.core.validator.ByteLengthValidator.MINIMUM",
                ByteLengthValidator.MINIMUM_MESSAGE_ID);
        assertEquals("teeda.core.ByteLength", ByteLengthValidator.VALIDATOR_ID);
    }

    public void testValidate_greaterThanMaximum() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        ByteLengthValidator validator = new ByteLengthValidator();
        validator.setMaximum(5);
        validator.setMinimum(1);
        validator.setCharSet("Shift_JIS");
        try {
            validator.validate(getFacesContext(), mock, "あああ");
            fail();
        }catch(ValidatorException expected){
            assertNotNull(expected.getMessage());
            success();
        }
    }

    public void testValidate_lowerThanMinimum() throws Exception {
        MockUIComponent mock = new MockUIComponent();
        mock.setId("aaa");
        ByteLengthValidator validator = new ByteLengthValidator();
        validator.setMaximum(5);
        validator.setMinimum(3);
        validator.setCharSet("Shift_JIS");
        try {
            validator.validate(getFacesContext(), mock, "あ");
            fail();
        }catch(ValidatorException expected){
            assertNotNull(expected.getMessage());
            success();
        }
    }

    public void testGetConvertedValueLength() throws Exception {
        ByteLengthValidator validator = new ByteLengthValidator();
        validator.setCharSet("NO_SUCH_ENCODE");
        try {
            validator.getConvertedValueLength("aaaa");
        }catch(ValidatorException expected) {
            success();
        }
    }
}
