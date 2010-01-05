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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;
import javax.faces.internal.ValidatorResource;

import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.component.TUISelectItems;
import org.seasar.teeda.extension.util.NullLabelStrategyImpl;
import org.seasar.teeda.extension.validator.TRequiredValidator;

/**
 * @author yone
 */
public class TSelectOneMenuTagTest extends TeedaTestCase {

    protected void setUp() throws Exception {
        NullLabelStrategyImpl helper = new NullLabelStrategyImpl();
        SingletonS2ContainerFactory.getContainer().register(helper);
    }

    public void testSetProperties_required() throws Exception {
        // # Arrange #
        HtmlSelectOneMenu component = createHtmlSelectOneMenu();
        TSelectOneMenuTag tag = new TSelectOneMenuTag();
        String value = "#{hogePage.aaa}";
        tag.setValue(value);
        String items = "#{hogePage.aaaItems}";
        tag.setItems(items);
        register(TRequiredValidator.class, "required");
        ValidatorResource.addValidator(value, "required");

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertEquals(value, component.getValueBinding("value")
                .getExpressionString());

        assertTrue(component.getChildCount() > 0);
        TUISelectItems child = (TUISelectItems) component.getChildren().get(0);
        assertEquals(items, child.getValueBinding("value")
                .getExpressionString());
        assertFalse(child.isNullLabelRequired());
    }

    public void testSetProperties_defaultNullLabelExists() throws Exception {
        // # Arrange #
        getFacesContext().setApplication(new MockApplication2());
        HtmlSelectOneMenu component = createHtmlSelectOneMenu();
        TSelectOneMenuTag tag = new TSelectOneMenuTag();
        String value = "#{hogePage.ccc}";
        tag.setValue(value);
        String items = "#{hogePage.cccItems}";
        tag.setItems(items);

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertEquals(value, component.getValueBinding("value")
                .getExpressionString());

        assertTrue(component.getChildCount() > 0);
        TUISelectItems child = (TUISelectItems) component.getChildren().get(0);
        assertEquals(items, child.getValueBinding("value")
                .getExpressionString());
        assertTrue(child.isNullLabelRequired());
    }

    public void testSetProperties_notrequired() throws Exception {
        // # Arrange #
        HtmlSelectOneMenu component = createHtmlSelectOneMenu();
        TSelectOneMenuTag tag = new TSelectOneMenuTag();
        String value = "#{hogePage.aaa}";
        tag.setValue(value);
        String items = "#{hogePage.aaaItems}";
        tag.setItems(items);

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertEquals(value, component.getValueBinding("value")
                .getExpressionString());

        assertTrue(component.getChildCount() > 0);
        TUISelectItems child = (TUISelectItems) component.getChildren().get(0);
        assertEquals(items, child.getValueBinding("value")
                .getExpressionString());
        assertTrue(child.isNullLabelRequired());
    }

    private HtmlSelectOneMenu createHtmlSelectOneMenu() {
        return (HtmlSelectOneMenu) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlSelectOneMenu();
    }

    public static class MockApplication2 extends MockApplicationImpl {

        public ValueBinding createValueBinding(String ref)
                throws ReferenceSyntaxException {
            MockValueBinding vb = new MockValueBinding(ref);
            vb.setType(int.class);
            return vb;
        }

    }
}