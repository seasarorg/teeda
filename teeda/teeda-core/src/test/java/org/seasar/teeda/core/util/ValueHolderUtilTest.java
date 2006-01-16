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
package org.seasar.teeda.core.util;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIColumn;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;

import junit.framework.TestCase;
import junitx.framework.ObjectAssert;

import org.seasar.teeda.core.mock.NullFacesContext;
import org.seasar.teeda.core.unit.AssertUtil;

/**
 * @author manhole
 */
public class ValueHolderUtilTest extends TestCase {

    public void testGetValueForRender_EditableValueHolder() throws Exception {
        // ## Arrange ##
        UIInput editableValueHolder = new UIInput();
        ObjectAssert.assertInstanceOf(EditableValueHolder.class,
                editableValueHolder);

        FacesContext context = new NullFacesContext();

        // ## Act & Assert ##
        assertEquals("", ValueHolderUtil.getValueForRender(context,
                editableValueHolder));
        editableValueHolder.setValue("asdf");
        assertEquals("asdf", ValueHolderUtil.getValueForRender(context,
                editableValueHolder));
        editableValueHolder.setSubmittedValue("bbbb");
        assertEquals("bbbb", ValueHolderUtil.getValueForRender(context,
                editableValueHolder));
    }

    public void testGetValueForRender_ValueHolder() throws Exception {
        // ## Arrange ##
        UIOutput valueHolder = new UIOutput();
        ObjectAssert
                .assertNotInstanceOf(EditableValueHolder.class, valueHolder);
        ObjectAssert.assertInstanceOf(ValueHolder.class, valueHolder);

        FacesContext context = new NullFacesContext();

        // ## Act & Assert ##
        assertEquals("", ValueHolderUtil
                .getValueForRender(context, valueHolder));
        valueHolder.setValue("abc");
        assertEquals("abc", ValueHolderUtil.getValueForRender(context,
                valueHolder));
    }

    public void testGetValueForRender_NotValueHolder() throws Exception {
        // ## Arrange ##
        UIColumn notValueHolder = new UIColumn();
        ObjectAssert.assertNotInstanceOf(ValueHolder.class, notValueHolder);

        FacesContext context = new NullFacesContext();

        // ## Act & Assert ##
        try {
            ValueHolderUtil.getValueForRender(context, notValueHolder);
        } catch (IllegalArgumentException iae) {
            AssertUtil.assertExceptionMessageExist(iae);
        }
    }

}
