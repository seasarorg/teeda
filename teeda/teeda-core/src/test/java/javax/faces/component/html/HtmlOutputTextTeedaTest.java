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
package javax.faces.component.html;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutputTeedaTest;

import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class HtmlOutputTextTeedaTest extends UIOutputTeedaTest {

    public void testSaveAndRestoreState() throws Exception {
        super.testSaveAndRestoreState();
        // ## Arrange ##
        HtmlOutputText htmlOutputText1 = createHtmlOutputText();
        htmlOutputText1.setEscape(false);
        htmlOutputText1.setTitle("foo title");
        htmlOutputText1.setStyle("foo style");
        htmlOutputText1.setStyleClass("foo styleClass");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        Object state = htmlOutputText1.saveState(context);
        HtmlOutputText htmlOutputText2 = createHtmlOutputText();
        htmlOutputText2.restoreState(context, serializeAndDeserialize(state));

        // ## Assert ##
        assertEquals(htmlOutputText1.isEscape(), htmlOutputText2.isEscape());
        assertEquals(htmlOutputText1.getTitle(), htmlOutputText2.getTitle());
        assertEquals(htmlOutputText1.getStyle(), htmlOutputText2.getStyle());
        assertEquals(htmlOutputText1.getStyleClass(), htmlOutputText2
                .getStyleClass());
    }

    private HtmlOutputText createHtmlOutputText() {
        return (HtmlOutputText) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlOutputText();
    }

}
