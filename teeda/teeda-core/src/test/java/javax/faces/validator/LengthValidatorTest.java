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

/**
 * @author shot
 */
public class LengthValidatorTest extends AbstractValidatorTest {

    //TODO more test for label
    public void testConstants() throws Exception {
        assertEquals("javax.faces.validator.LengthValidator.MAXIMUM",
                LengthValidator.MAXIMUM_MESSAGE_ID);
        assertEquals("javax.faces.validator.LengthValidator.MINIMUM",
                LengthValidator.MINIMUM_MESSAGE_ID);
        assertEquals("javax.faces.Length", LengthValidator.VALIDATOR_ID);
    }

    public void testGetMaximum_maxNotSet() throws Exception {
        LengthValidator validator = new LengthValidator();
        assertEquals(-1, validator.getMaximum());
    }

    public void testGetMaximum_getMax() throws Exception {
        LengthValidator validator = new LengthValidator(5);
        assertEquals(5, validator.getMaximum());
    }

    public void testGetMinimum_minNotSet() throws Exception {
        LengthValidator validator = new LengthValidator();
        assertEquals(-1, validator.getMinimum());
    }

    public void testGetMinimum_getMin() throws Exception {
        LengthValidator validator = new LengthValidator(5, 2);
        assertEquals(2, validator.getMinimum());
    }

    public void testValidate_lengthLessThanMin() throws Exception {
        FacesContext context = getFacesContextWithSetMessageBundle("a",
                Locale.ENGLISH);
        LengthValidator validator = new LengthValidator(5, 2);
        try {
            validator.validate(context, context.getViewRoot(), "b");
            fail();
        } catch (ValidatorException expected) {
            assertEquals("length less than min(2,a)", expected.getMessage());
            success();
        }
    }

    public void testValidate_lengthMoreThanMax() throws Exception {
        FacesContext context = getFacesContextWithSetMessageBundle("a",
                Locale.ENGLISH);
        LengthValidator validator = new LengthValidator(5, 2);
        try {
            validator.validate(context, context.getViewRoot(), "123456");
            fail();
        } catch (ValidatorException expected) {
            assertEquals("length more than max(5,a)", expected.getMessage());
            success();
        }
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
        return new LengthValidator();
    }

}
