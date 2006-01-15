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

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.NullFacesContext;

/**
 * @author manhole
 */
public class ValueHolderUtilTest extends TestCase {

    public void testGetValueForRender() throws Exception {
        // ## Arrange ##
        UIInput component = new UIInput();
        FacesContext context = new NullFacesContext();

        // ## Act & Assert ##
        assertEquals("", ValueHolderUtil.getValueForRender(context, component));
        component.setValue("asdf");
        assertEquals("asdf", ValueHolderUtil.getValueForRender(context,
                component));
        component.setSubmittedValue("bbbb");
        assertEquals("bbbb", ValueHolderUtil.getValueForRender(context,
                component));
    }

}
