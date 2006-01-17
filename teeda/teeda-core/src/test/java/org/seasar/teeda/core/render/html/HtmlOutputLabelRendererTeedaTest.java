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
package org.seasar.teeda.core.render.html;

import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTeedaTest;

/**
 * @author manhole
 */
public class HtmlOutputLabelRendererTeedaTest extends RendererTeedaTest {

    public void testEncodeBegin_WithForComponent() throws Exception {
        HtmlOutputLabel htmlOutputLabel = new HtmlOutputLabel();
        htmlOutputLabel.setFor("forComponentId");

        UIInput forComponent = new UIInput();
        forComponent.setId("forComponentId");

        UIForm parent = new UIForm();
        parent.setId("parentForm");
        parent.getChildren().add(htmlOutputLabel);
        parent.getChildren().add(forComponent);

        HtmlOutputLabelRenderer renderer = createHtmlOutputLabelRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);

        assertEquals("<label for=\"parentForm:forComponentId\">",
                getResponseText());
    }

    private HtmlOutputLabelRenderer createHtmlOutputLabelRenderer() {
        return (HtmlOutputLabelRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlOutputLabelRenderer();
    }

}
