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

/**
 * @author manhole
 */
public class THtmlGridTest extends UIComponentBaseTest {

    public void testGetItems() throws Exception {
        // ## Arrange ##
        THtmlGrid htmlGrid = createTHtmlGrid();
        final List someItems = Arrays.asList(new String[] { "v1", "v2", "v3" });
        htmlGrid.setValue(someItems);

        // ## Act ##
        final List items = htmlGrid.getItems();

        // ## Assert ##
        assertEquals(someItems, items);
    }

    public void testEnterRow() throws Exception {
        // ## Arrange ##
        THtmlGrid htmlGrid = createTHtmlGrid();
        htmlGrid.setId("fooooGrid");
        final FacesContext facesContext = getFacesContext();
        final Object object = new Object();

        // ## Act ##
        htmlGrid.enterRow(facesContext, object);

        // ## Assert ##
        final Object rowBean = facesContext.getApplication()
                .getVariableResolver().resolveVariable(facesContext, "foooo");
        assertSame(object, rowBean);
    }

    public void testLeaveRow() throws Exception {
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
