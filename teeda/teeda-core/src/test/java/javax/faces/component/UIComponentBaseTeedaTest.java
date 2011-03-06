/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;

import junitx.framework.Assert;
import junitx.framework.ObjectAssert;
import junitx.framework.StringAssert;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.el.impl.ValueBindingImpl;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;
import org.seasar.teeda.core.mock.NullApplication;
import org.seasar.teeda.core.mock.NullELParser;
import org.seasar.teeda.core.unit.TestUtil;

/**
 * @author manhole
 */
public class UIComponentBaseTeedaTest extends AbstractUIComponentTeedaTest {

    public final void testGetClientId_NullId() {
        UIComponentBase component = createUIComponentBase();
        component.setId(null);

        String clientId = component.getClientId(getFacesContext());
        assertEquals(true, StringUtil.isNotBlank(clientId));
        StringAssert.assertStartsWith(UIViewRoot.UNIQUE_ID_PREFIX, clientId);
        assertEquals(null, component.getId());
    }

    public void testGetCliendId() throws Exception {
        // ## Arrange ##
        UIComponentBase component = createUIComponentBase();
        component.setId("foo");

        // ## Act & Assert ##
        assertEquals("foo", component.getClientId(getFacesContext()));
    }

    public void testGetClientId_WithParentNamingContainer() {
        UIComponentBase component = createUIComponentBase();
        component.setId("a");

        MockUIComponentBase parent = new MockUIComponentBaseWithNamingContainer();
        parent.setClientId("b");
        parent.getChildren().add(component);

        String clientId = component.getClientId(getFacesContext());
        assertNotNull(clientId);
        assertEquals("b:a", clientId);
    }

    public void testGetClientId_WithParentNotNamingContainer() {
        UIComponentBase component = createUIComponentBase();
        component.setId("a");

        MockUIComponentBase parent = new MockUIComponentBase();
        ObjectAssert.assertNotInstanceOf(NamingContainer.class, parent);
        parent.setClientId("b");
        parent.getChildren().add(component);

        String clientId = component.getClientId(getFacesContext());
        assertNotNull(clientId);
        assertEquals("a", clientId);
    }

    public void testGetClientId_ParentNamingContainerIdIsChanged()
            throws Exception {
        // ## Arrange ##
        UIComponent parent = createUIComponent();

        FacesContext context = getFacesContext();
        UIComponent child = new MockUIComponentBase();
        parent.setId("a");
        parent.getChildren().add(child);

        // ## Act & Assert ##
        final String clientId1 = child.getClientId(context);
        assertEquals(clientId1, child.getClientId(context));
        parent.setId("b");

        if (parent instanceof NamingContainer) {
            Assert.assertNotEquals(clientId1, child.getClientId(context));
        } else {
            System.out.println(parent.getClass().getName()
                    + " is not NamingContainer");
            Assert.assertEquals(clientId1, child.getClientId(context));
        }
    }

    public void testSaveAndRestoreState() throws Exception {
        UIComponentBase component1 = (UIComponentBase) createUIComponent();
        component1.setId("abc");
        component1.setRendered(true);
        component1.setRendererType("some renderer");
        component1.setTransient(true);
        component1.getAttributes().put("at", "vvv");
        component1.setValueBinding("vbb", new ValueBindingImpl(
                new NullApplication(), "abc", new NullELParser()));

        FacesContext context = getFacesContext();
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.setRenderKitId(RenderKitFactory.HTML_BASIC_RENDER_KIT);
        context.setViewRoot(viewRoot);
        final Object state = component1.saveState(context);
        assertTrue(state instanceof Serializable);

        UIComponentBase component2 = createUIComponentBase();
        component2.restoreState(context, serializeAndDeserialize(state));

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
        assertEquals(component1.getValueBinding("vbb").getExpressionString(),
                component2.getValueBinding("vbb").getExpressionString());
        assertEquals(false, component2.isTransient());
    }

    public void testSaveState_NotSaveChildren() throws Exception {
        // ## Arrange ##
        UIComponentBase component1 = (UIComponentBase) createUIComponent();
        component1.getChildren().add(new UIParameter());
        component1.getFacets().put("a", new UIOutput());
        assertEquals(1, component1.getChildCount());
        assertEquals(1, component1.getFacets().size());

        // ## Act ##
        FacesContext context = getFacesContext();
        Object state = component1.saveState(context);
        assertTrue(state instanceof Serializable);

        UIComponentBase component2 = createUIComponentBase();
        component2.restoreState(context, serializeAndDeserialize(state));

        // ## Assert ##
        assertEquals(0, component2.getChildCount());
        assertEquals(0, component2.getFacets().size());
    }

    private UIComponentBase createUIComponentBase() {
        return (UIComponentBase) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new MockUIComponentBase();
    }

    protected Object serializeAndDeserialize(final Object input)
            throws IOException, ClassNotFoundException {
        return TestUtil.serializeAndDeserialize(input);
    }

}
