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
package javax.faces.component;

import javax.faces.context.FacesContext;

import junitx.framework.Assert;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author manhole
 */
public abstract class AbstractUIComponentTeedaTest extends TeedaTestCase {

    public final void testGetClientId_ConsecutiveCallsReturnSameValue()
            throws Exception {
        // ## Arrange ##
        UIComponent component = createUIComponent();
        FacesContext context = getFacesContext();

        // ## Act & Assert ##
        String clientId1 = component.getClientId(context);

        assertEquals(clientId1, component.getClientId(context));
        assertEquals(clientId1, component.getClientId(context));
    }

    public final void testGetClientId_IdIsChanged() throws Exception {
        // ## Arrange ##
        UIComponent component = createUIComponent();
        FacesContext context = getFacesContext();
        component.setId("a");

        // ## Act & Assert ##
        String clientId1 = component.getClientId(context);
        assertEquals(clientId1, component.getClientId(context));
        component.setId("b");
        Assert.assertNotEquals(clientId1, component.getClientId(context));
    }

    protected abstract UIComponent createUIComponent();

}
