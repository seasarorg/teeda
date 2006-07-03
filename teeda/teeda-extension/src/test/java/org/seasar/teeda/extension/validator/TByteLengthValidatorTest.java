package org.seasar.teeda.extension.validator;

import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.validator.TByteLengthValidator;

public class TByteLengthValidatorTest extends TeedaTestCase {

    public void testConstants() throws Exception {
        assertEquals("org.seasar.teeda.core.validator.ByteLengthValidator.MAXIMUM",
                TByteLengthValidator.MAXIMUM_MESSAGE_ID);
        assertEquals("org.seasar.teeda.core.validator.ByteLengthValidator.MINIMUM",
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
        }catch(ValidatorException expected){
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
        }catch(ValidatorException expected){
            assertNotNull(expected.getMessage());
            success();
        }
    }

    public void testGetConvertedValueLength() throws Exception {
        TByteLengthValidator validator = new TByteLengthValidator();
        validator.setCharSet("NO_SUCH_ENCODE");
        try {
            validator.getConvertedValueLength("aaaa");
        }catch(ValidatorException expected) {
            success();
        }
    }
}
