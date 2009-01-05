/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.teeda.extension.component.TForEachTeedaTest;

/**
 * @author manhole
 */
public class THtmlGridTeedaTest extends TForEachTeedaTest {

    public void testSaveAndRestoreState() throws Exception {
        super.testSaveAndRestoreState();

        final THtmlGrid grid1 = createTHtmlGrid();
        {
            grid1.setScrollHorizontal(true);
            grid1.setScrollVertical(true);
            grid1.setStyle("aaStyle");
            grid1.setStyleClass("aaClass");
        }
        final FacesContext context = getFacesContext();
        final Object decoded = serializeAndDeserialize(grid1.saveState(context));

        final THtmlGrid grid2 = createTHtmlGrid();
        assertEquals(false, grid2.isScrollHorizontal());
        assertEquals(false, grid2.isScrollVertical());
        grid2.restoreState(context, decoded);
        assertEquals(true, grid2.isScrollHorizontal());
        assertEquals("aaStyle", grid2.getStyle());
        assertEquals("aaClass", grid2.getStyleClass());
    }

    private THtmlGrid createTHtmlGrid() {
        return (THtmlGrid) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new THtmlGrid();
    }

}
