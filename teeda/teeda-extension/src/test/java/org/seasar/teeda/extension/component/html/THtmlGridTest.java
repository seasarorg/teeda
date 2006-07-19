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
package org.seasar.teeda.extension.component.html;

import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBaseTest;
import javax.faces.context.FacesContext;
import javax.faces.el.VariableResolver;

/**
 * @author manhole
 */
public class THtmlGridTest extends UIComponentBaseTest {

    public void testGetItems1() throws Exception {
        // ## Arrange ##
        THtmlGrid htmlGrid = createTHtmlGrid();
        final Object[] someItems = new String[] { "v1", "v2", "v3" };
        htmlGrid.setValue(someItems);

        // ## Act ##
        final Object[] items = htmlGrid.getItems();

        // ## Assert ##
        assertEquals(someItems, items);
    }

    public void testGetItems2() throws Exception {
        // ## Arrange ##
        THtmlGrid htmlGrid = createTHtmlGrid();
        final List someItems = Arrays.asList(new String[] { "v1", "v2", "v3" });
        htmlGrid.setValue(someItems);

        // ## Act ##
        final Object[] items = htmlGrid.getItems();

        // ## Assert ##
        assertEquals(someItems.size(), items.length);
        assertEquals(someItems.get(0), items[0]);
        assertEquals(someItems.get(1), items[1]);
        assertEquals(someItems.get(2), items[2]);
    }

    // TODO
    public void no_testEnterRow() throws Exception {
        // ## Arrange ##
        THtmlGrid htmlGrid = createTHtmlGrid();
        final String prefix = "foooo";
        htmlGrid.setId(prefix + "Grid");
        final FacesContext facesContext = getFacesContext();
        final Object object1 = new Object();
        final Object object2 = new Object();
        htmlGrid.setValue(Arrays.asList(new Object[] { object1, object2 }));

        final VariableResolver variableResolver = facesContext.getApplication()
                .getVariableResolver();

        // ## Act ##
        // ## Assert ##
        {
            htmlGrid.enterRow(facesContext, 0);
            final Object rowBean = variableResolver.resolveVariable(
                    facesContext, prefix);
            assertSame(object1, rowBean);
        }
        {
            htmlGrid.enterRow(facesContext, 1);
            final Object rowBean = variableResolver.resolveVariable(
                    facesContext, prefix);
            assertSame(object2, rowBean);
        }
    }

    // TODO
    public void no_testLeaveRow() throws Exception {
        // ## Arrange ##
        THtmlGrid htmlGrid = createTHtmlGrid();
        htmlGrid.setId("a12345GridX");
        final FacesContext facesContext = getFacesContext();
        final Object object = new Object();
        facesContext.getExternalContext().getRequestMap().put("a12345", object);

        // ## Act ##
        htmlGrid.leaveRow(facesContext);

        // ## Assert ##
        assertEquals(null, facesContext.getExternalContext().getRequestMap()
                .get("a12345"));
    }

    private THtmlGrid createTHtmlGrid() {
        return (THtmlGrid) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new THtmlGrid();
    }

}
