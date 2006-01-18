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

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;

import junitx.framework.Assert;
import junitx.framework.ObjectAssert;
import junitx.framework.StringAssert;

import org.apache.commons.lang.StringUtils;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockUIComponentWithNamingContainer;

/**
 * @author manhole
 */
public class UIComponentBaseTeedaTest extends AbstractUIComponentTeedaTest {

    public void testGetCliendId() throws Exception {
        // ## Arrange ##
        UIComponentBase component = createUIComponentBase();
        component.setId("foo");

        // ## Act & Assert ##
        assertEquals("foo", component.getClientId(getFacesContext()));
    }

    public void testGetClientId_WhenIdIsNull() {
        UIComponentBase component = createUIComponentBase();
        component.setId(null);

        String clientId = component.getClientId(getFacesContext());
        assertEquals(false, StringUtils.isBlank(clientId));
        StringAssert.assertStartsWith(UIViewRoot.UNIQUE_ID_PREFIX, clientId);
    }

    public void testGetClientId_WithParentNamingContainer() {
        UIComponentBase component = createUIComponentBase();
        component.setId("a");

        MockUIComponent parent = new MockUIComponentWithNamingContainer();
        parent.setClientId("b");
        component.setParent(parent);

        String clientId = component.getClientId(getFacesContext());
        assertNotNull(clientId);
        assertEquals("b:a", clientId);
    }

    public void testGetClientId_WithParentNotNamingContainer() {
        UIComponentBase component = createUIComponentBase();
        component.setId("a");

        MockUIComponent parent = new MockUIComponent();
        ObjectAssert.assertNotInstanceOf(NamingContainer.class, parent);
        parent.setClientId("b");
        component.setParent(parent);

        String clientId = component.getClientId(getFacesContext());
        assertNotNull(clientId);
        assertEquals("a", clientId);
    }

    // FIXME is it OK?
    public void testGetClientId_ParentNamingContainerIdIsChanged()
            throws Exception {
        // ## Arrange ##
        UIComponent child = createUIComponent();
        FacesContext context = getFacesContext();
        UIData namingContainer = new UIData();
        ObjectAssert.assertInstanceOf(NamingContainer.class, namingContainer);
        namingContainer.setId("a");
        namingContainer.getChildren().add(child);

        // ## Act & Assert ##
        final String clientId1 = child.getClientId(context);
        assertEquals(clientId1, child.getClientId(context));
        namingContainer.setId("b");

        Assert.assertNotEquals(clientId1, child.getClientId(context));
    }

    public void testSaveAndRestoreState() throws Exception {
        UIComponentBase component1 = (UIComponentBase) createUIComponent();
        component1.setId("abc");
        component1.setRendered(true);
        component1.setRendererType("some renderer");

        FacesContext context = getFacesContext();
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.setRenderKitId(RenderKitFactory.HTML_BASIC_RENDER_KIT);
        context.setViewRoot(viewRoot);
        Object state = component1.saveState(context);
        assertTrue(state instanceof Serializable);

        UIComponentBase component2 = createUIComponentBase();
        component2.restoreState(context, state);

        assertEquals(component1.getAttributes().size(), component2
                .getAttributes().size());
        assertEquals(component1.getChildCount(), component2.getChildCount());
        assertEquals(component1.getClientId(context), component2
                .getClientId(context));
        assertEquals(component1.getFacets().size(), component2.getFacets()
                .size());
        // assertEquals(component1.getFacetsAndChildren(), component2
        // .getFacetsAndChildren());
        assertEquals(component1.getId(), component2.getId());
        assertEquals(component1.getParent(), component2.getParent());
        assertEquals(component1.getRendererType(), component2.getRendererType());
        assertEquals(component1.getRendersChildren(), component2
                .getRendersChildren());
    }

    private UIComponentBase createUIComponentBase() {
        return (UIComponentBase) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new MockUIComponentBase();
    }

}
