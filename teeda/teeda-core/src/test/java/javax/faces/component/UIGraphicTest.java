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

import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class UIGraphicTest extends UIComponentBaseTest {

    public final void testSetGetValue() {
        UIGraphic graphic = createUIGraphic();
        graphic.setValue("aaa");
        assertEquals("aaa", graphic.getValue());
        assertEquals("aaa", graphic.getUrl());
    }

    public final void testSetGetValue_ValueBinding() {
        UIGraphic graphic = createUIGraphic();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bbbbb");
        graphic.setValueBinding("value", vb);
        assertEquals("bbbbb", graphic.getUrl());
        assertEquals("bbbbb", graphic.getValue());
    }

    public void testSetGetUrl() throws Exception {
        UIGraphic graphic = createUIGraphic();
        graphic.setUrl("abc");
        assertEquals("abc", graphic.getValue());
        assertEquals("abc", graphic.getUrl());
    }

    public final void testSetGetUrl_ValueBinding() {
        UIGraphic graphic = createUIGraphic();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "b");
        graphic.setValueBinding("url", vb);
        assertEquals("b", graphic.getUrl());
        assertEquals("b", graphic.getValue());
    }

    private UIGraphic createUIGraphic() {
        return (UIGraphic) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIGraphic();
    }

}
