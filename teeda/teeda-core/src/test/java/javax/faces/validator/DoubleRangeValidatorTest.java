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

import java.util.Iterator;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.internal.FacesMessageUtils;

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
        assertEquals(2d, validator.getMaximum());
    }

    public void testInstanciation_withMaxAndMin() throws Exception {
        double max = 2000d;
        double min = 3d;
        DoubleRangeValidator validator = new DoubleRangeValidator(max, min);
        assertEquals(2000d, validator.getMaximum());
        assertEquals(3d, validator.getMinimum());
    }

    public void testGetMaximum_setMax() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator(5d);
        assertEquals(5d, validator.getMaximum());
    }

    public void testGetMaximum_notSetMax() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator();
        assertEquals(Double.MAX_VALUE, validator.getMaximum());
    }

    public void testGetMinimum_setMin() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator(8d, 7d);
        assertEquals(7d, validator.getMinimum());
    }

    public void testGetMinimum_notSetMin() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator();
        assertEquals(Double.MIN_VALUE, validator.getMinimum());
    }

    public void testValidate_facesContextIsNull() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator();
        try {
            validator.validate(null, new MockUIComponent(), "");
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

    public void testValidate_componentIsNull() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator();
        try {
            validator.validate(new MockFacesContextImpl(), null, "");
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

    public void testValidate_valueIsNull() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator();
        validator.validate(new MockFacesContextImpl(), new MockUIComponent(), null);
        
        //assume nothing happen
        assertTrue(true);
    }

    public void testValidate_lessThanMinWhenBothMaxMinNotNull() throws Exception {
        getApplication().setMessageBundle("javax.faces.component.TestMessages");
        MockFacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        root.setId("a");
        root.setLocale(Locale.ENGLISH);
        context.setViewRoot(root);
        DoubleRangeValidator validator = new DoubleRangeValidator(2d, 1d);
        try {
            validator.validate(getFacesContext(), root, new Integer(0));
            fail();
        }catch(ValidatorException expected){
            assertEquals("not in range(1,2,a)", expected.getMessage());
            assertTrue(true);
        }
    }
}
