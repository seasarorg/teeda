/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import javax.faces.internal.NormalValidatorBuilderImpl;
import javax.faces.internal.ValidatorResource;

import junitx.framework.Assert;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.assembler.AutoBindingDefFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.teeda.core.mock.NullELParser;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author manhole
 */
public abstract class AbstractUIComponentTeedaTest extends TeedaTestCase {

    public void setUp() throws Exception {
        ComponentDef componentDef = new ComponentDefImpl(NullELParser.class);
        componentDef.setAutoBindingDef(AutoBindingDefFactory.NONE);
        getContainer().register(componentDef);
        NormalValidatorBuilderImpl builder = new NormalValidatorBuilderImpl();
        builder.setContainer(getContainer());
        ValidatorResource.setValidatorBuilder(builder);
    }

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
