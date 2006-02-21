/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class DoubleRangeValidatorTest extends TeedaTestCase {

    public void testConstants() throws Exception {
        assertEquals("javax.faces.validator.DoubleRangeValidator.MAXIMUM",
                DoubleRangeValidator.MAXIMUM_MESSAGE_ID);
        assertEquals("javax.faces.validator.DoubleRangeValidator.MINIMUM",
                DoubleRangeValidator.MINIMUM_MESSAGE_ID);
        assertEquals("javax.faces.validator.DoubleRangeValidator.TYPE",
                DoubleRangeValidator.TYPE_MESSAGE_ID);
        assertEquals("javax.faces.DoubleRange",
                DoubleRangeValidator.VALIDATOR_ID);
    }

    public void testInstanciation_withMax() throws Exception {
        double d = 2d;
        DoubleRangeValidator validator = new DoubleRangeValidator(d);
        assertTrue(2d == validator.getMaximum());
    }

    public void testInstanciation_withMaxAndMin() throws Exception {
        double max = 2000d;
        double min = 3d;
        DoubleRangeValidator validator = new DoubleRangeValidator(max, min);
        assertTrue(2000d == validator.getMaximum());
        assertTrue(3d == validator.getMinimum());
    }

    public void testGetMaximum_setMax() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator(5d);
        assertTrue(5d == validator.getMaximum());
    }

    public void testGetMaximum_notSetMax() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator();
        assertTrue(Double.MAX_VALUE == validator.getMaximum());
    }

    public void testGetMinimum_setMin() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator(8d, 7d);
        assertTrue(7d == validator.getMinimum());
    }

    public void testGetMinimum_notSetMin() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator();
        assertTrue(Double.MIN_VALUE == validator.getMinimum());
    }

    public void testValidate_facesContextIsNull() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator();
        try {
            validator.validate(null, new MockUIComponent(), "");
            fail();
        } catch (NullPointerException expected) {
            success();
        }
    }

    public void testValidate_componentIsNull() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator();
        try {
            validator.validate(new MockFacesContextImpl(), null, "");
            fail();
        } catch (NullPointerException expected) {
            success();
        }
    }

    public void testValidate_valueIsNull() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator();
        validator.validate(new MockFacesContextImpl(), new MockUIComponent(),
                null);

        // assume nothing happen
        success();
    }

    public void testValidate_lessThanMinWhenBothMaxMinNotNull()
            throws Exception {
        FacesContext context = getFacesContextWithSetMessageBundle("a",
                Locale.ENGLISH);
        DoubleRangeValidator validator = new DoubleRangeValidator(2d, 1d);
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
        DoubleRangeValidator validator = new DoubleRangeValidator(2d, 1d);
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
        DoubleRangeValidator validator = new DoubleRangeValidator();
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
        DoubleRangeValidator validator = new DoubleRangeValidator(10);
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
        DoubleRangeValidator validator = new DoubleRangeValidator(10);
        try {
            validator.validate(context, context.getViewRoot(), "aaa");
            fail();
        } catch (ValidatorException expected) {
            assertEquals("type(hoge) is not double", expected.getMessage());
            success();
        }
    }

    public void testEquals1() throws Exception {
        DoubleRangeValidator v1 = new DoubleRangeValidator(2, 1);
        DoubleRangeValidator v2 = new DoubleRangeValidator(2, 1);
        assertTrue(v1.equals(v2));
    }

    public void testEquals2() throws Exception {
        DoubleRangeValidator v1 = new DoubleRangeValidator(1);
        DoubleRangeValidator v2 = new DoubleRangeValidator(1);
        assertTrue(v1.equals(v2));
    }

    public void testEquals3() throws Exception {
        DoubleRangeValidator v1 = new DoubleRangeValidator();
        DoubleRangeValidator v2 = new DoubleRangeValidator();
        assertTrue(v1.equals(v2));
    }

    public void testEquals4() throws Exception {
        DoubleRangeValidator v1 = new DoubleRangeValidator();
        v1.setMinimum(3);
        DoubleRangeValidator v2 = new DoubleRangeValidator();
        v2.setMinimum(3);
        assertTrue(v1.equals(v2));
    }

    public void testEquals5() throws Exception {
        DoubleRangeValidator v1 = new DoubleRangeValidator(4);
        DoubleRangeValidator v2 = new DoubleRangeValidator();
        assertFalse(v1.equals(v2));
    }

    public void testEquals6() throws Exception {
        DoubleRangeValidator v1 = new DoubleRangeValidator();
        DoubleRangeValidator v2 = new DoubleRangeValidator(6);
        assertFalse(v1.equals(v2));
    }

    public void testEquals7() throws Exception {
        DoubleRangeValidator v1 = new DoubleRangeValidator(7);
        DoubleRangeValidator v2 = new DoubleRangeValidator(8);
        assertFalse(v1.equals(v2));
    }

    public void testEquals8() throws Exception {
        DoubleRangeValidator v1 = new DoubleRangeValidator();
        v1.setMinimum(3);
        DoubleRangeValidator v2 = new DoubleRangeValidator();
        assertFalse(v1.equals(v2));
    }

    public void testEquals9() throws Exception {
        DoubleRangeValidator v1 = new DoubleRangeValidator();
        DoubleRangeValidator v2 = new DoubleRangeValidator();
        v2.setMinimum(9);
        assertFalse(v1.equals(v2));
    }

    public void testEquals10() throws Exception {
        DoubleRangeValidator v1 = new DoubleRangeValidator(2);
        v1.setMinimum(8);
        DoubleRangeValidator v2 = new DoubleRangeValidator(2);
        v2.setMinimum(9);
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
}
