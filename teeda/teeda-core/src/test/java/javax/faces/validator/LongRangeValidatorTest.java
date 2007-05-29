/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package javax.faces.validator;

import java.util.Locale;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;

public class LongRangeValidatorTest extends AbstractValidatorTest {

    //TODO more test for label
    public void testConstants() throws Exception {
        assertEquals("javax.faces.validator.LongRangeValidator.MAXIMUM",
                LongRangeValidator.MAXIMUM_MESSAGE_ID);
        assertEquals("javax.faces.validator.LongRangeValidator.MINIMUM",
                LongRangeValidator.MINIMUM_MESSAGE_ID);
        assertEquals("javax.faces.validator.LongRangeValidator.TYPE",
                LongRangeValidator.TYPE_MESSAGE_ID);
        assertEquals("javax.faces.LongRange", LongRangeValidator.VALIDATOR_ID);
    }

    public void testInstanciation_withMax() throws Exception {
        long l = 123456L;
        LongRangeValidator validator = new LongRangeValidator(l);
        assertTrue(123456d == validator.getMaximum());
    }

    public void testInstanciation_withMaxAndMin() throws Exception {
        long max = 2000L;
        long min = 3L;
        LongRangeValidator validator = new LongRangeValidator(max, min);
        assertTrue(2000L == validator.getMaximum());
        assertTrue(3L == validator.getMinimum());
    }

    public void testGetMaximum_setMax() throws Exception {
        LongRangeValidator validator = new LongRangeValidator(12345678L);
        assertTrue(12345678L == validator.getMaximum());
    }

    public void testGetMaximum_notSetMax() throws Exception {
        LongRangeValidator validator = new LongRangeValidator();
        assertTrue(Long.MAX_VALUE == validator.getMaximum());
    }

    public void testGetMinimum_setMin() throws Exception {
        LongRangeValidator validator = new LongRangeValidator(8L, 7L);
        assertTrue(7L == validator.getMinimum());
    }

    public void testGetMinimum_notSetMin() throws Exception {
        LongRangeValidator validator = new LongRangeValidator();
        assertTrue(Long.MIN_VALUE == validator.getMinimum());
    }

    public void testValidate_lessThanMinWhenBothMaxMinNotNull()
            throws Exception {
        FacesContext context = getFacesContextWithSetMessageBundle("a",
                Locale.ENGLISH);
        LongRangeValidator validator = new LongRangeValidator(2L, 1L);
        try {
            validator.validate(context, context.getViewRoot(), new Integer(0));
            fail();
        } catch (ValidatorException expected) {
            assertEquals("not in range(1,2,a)", expected.getMessage());
            success();
        }
    }

    public void testValidate_moreThanMaxWhenBothMaxMinNotNull()
            throws Exception {
        FacesContext context = getFacesContextWithSetMessageBundle("b",
                Locale.ENGLISH);
        LongRangeValidator validator = new LongRangeValidator(2L, 1L);
        try {
            validator.validate(context, context.getViewRoot(), new Integer(3));
            fail();
        } catch (ValidatorException expected) {
            assertEquals("not in range(1,2,b)", expected.getMessage());
            success();
        }
    }

    public void testValidate_lessThanMinWhenMinNotNull() throws Exception {
        FacesContext context = getFacesContextWithSetMessageBundle("a",
                Locale.ENGLISH);
        LongRangeValidator validator = new LongRangeValidator();
        validator.setMinimum(1);
        try {
            validator.validate(context, context.getViewRoot(), new Integer(0));
            fail();
        } catch (ValidatorException expected) {
            assertEquals("less than min(1,a)", expected.getMessage());
            success();
        }
    }

    public void testValidate_moreThanMaxWhenMaxNotNull() throws Exception {
        FacesContext context = getFacesContextWithSetMessageBundle("a",
                Locale.ENGLISH);
        LongRangeValidator validator = new LongRangeValidator(10);
        try {
            validator.validate(context, context.getViewRoot(), new Float(11));
            fail();
        } catch (ValidatorException expected) {
            assertEquals("more than max(10,a)", expected.getMessage());
            success();
        }
    }

    public void testValidate_valueIsNotNumber() throws Exception {
        FacesContext context = getFacesContextWithSetMessageBundle("hoge",
                Locale.ENGLISH);
        LongRangeValidator validator = new LongRangeValidator(10);
        try {
            validator.validate(context, context.getViewRoot(), "aaa");
            fail();
        } catch (ValidatorException expected) {
            assertEquals("type(hoge) is not double", expected.getMessage());
            success();
        }
    }

    public void testValidate_NoValidate() throws Exception {
        FacesContext context = getFacesContext();
        LongRangeValidator validator = new LongRangeValidator(5, 2);
        validator.validate(context, new MockUIComponent(), "");
    }

    public void testEquals1() throws Exception {
        LongRangeValidator v1 = new LongRangeValidator(2, 1);
        LongRangeValidator v2 = new LongRangeValidator(2, 1);
        assertTrue(v1.equals(v2));
    }

    public void testEquals2() throws Exception {
        LongRangeValidator v1 = new LongRangeValidator(1);
        LongRangeValidator v2 = new LongRangeValidator(1);
        assertTrue(v1.equals(v2));
    }

    public void testEquals3() throws Exception {
        LongRangeValidator v1 = new LongRangeValidator();
        LongRangeValidator v2 = new LongRangeValidator();
        assertTrue(v1.equals(v2));
    }

    public void testEquals4() throws Exception {
        LongRangeValidator v1 = new LongRangeValidator();
        v1.setMinimum(3);
        LongRangeValidator v2 = new LongRangeValidator();
        v2.setMinimum(3);
        assertTrue(v1.equals(v2));
    }

    public void testEquals5() throws Exception {
        LongRangeValidator v1 = new LongRangeValidator(4);
        LongRangeValidator v2 = new LongRangeValidator();
        assertFalse(v1.equals(v2));
    }

    public void testEquals6() throws Exception {
        LongRangeValidator v1 = new LongRangeValidator();
        LongRangeValidator v2 = new LongRangeValidator(6);
        assertFalse(v1.equals(v2));
    }

    public void testEquals7() throws Exception {
        LongRangeValidator v1 = new LongRangeValidator(7);
        LongRangeValidator v2 = new LongRangeValidator(8);
        assertFalse(v1.equals(v2));
    }

    public void testEquals8() throws Exception {
        LongRangeValidator v1 = new LongRangeValidator();
        v1.setMinimum(3);
        LongRangeValidator v2 = new LongRangeValidator();
        assertFalse(v1.equals(v2));
    }

    public void testEquals9() throws Exception {
        LongRangeValidator v1 = new LongRangeValidator();
        LongRangeValidator v2 = new LongRangeValidator();
        v2.setMinimum(9);
        assertFalse(v1.equals(v2));
    }

    public void testEquals10() throws Exception {
        LongRangeValidator v1 = new LongRangeValidator(2);
        v1.setMinimum(8);
        LongRangeValidator v2 = new LongRangeValidator(2);
        v2.setMinimum(9);
        assertFalse(v1.equals(v2));
    }

    public void testEquals12() throws Exception {
        LongRangeValidator v1 = new LongRangeValidator();
        DoubleRangeValidator v2 = new DoubleRangeValidator();
        assertFalse(v1.equals(v2));
    }

    protected FacesContext getFacesContextWithSetMessageBundle(
            String viewRootId, Locale locale) {
        getApplication().setMessageBundle("javax.faces.component.TestMessages");
        MockFacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        root.setId(viewRootId);
        root.setLocale(locale);
        context.setViewRoot(root);
        return context;
    }

    protected Validator createValidator() {
        return new LongRangeValidator();
    }

}
