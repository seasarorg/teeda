/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.render.html;

import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.render.AbstractRendererTest;
import javax.faces.render.Renderer;

import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.mock.MockViewHandler;
import org.seasar.teeda.core.mock.MockViewHandlerImpl;
import org.seasar.teeda.extension.component.html.THtmlOutputLabel;

/**
 * @author shot
 */
public class THtmlOutputLabelRendererTest extends AbstractRendererTest {

    private THtmlOutputLabelRenderer renderer;

    private MockTHtmlOutputLabel label;

    public void setUp() throws Exception {
        super.setUp();
        renderer = new THtmlOutputLabelRenderer();
        label = new MockTHtmlOutputLabel();
        label.setRenderer(renderer);
    }

    public void testEncode_simple() throws Exception {
        encodeByRenderer(renderer, label);
        assertEquals("<label></label>", getResponseText());
    }

    public void testEncode_simple2() throws Exception {
        label.setId("aaa");
        label.setFor("bbb");
        encodeByRenderer(renderer, label);
        assertEquals("<label id=\"aaa\" for=\"bbb\"></label>",
                getResponseText());
    }

    public void testEncode_simple3() throws Exception {
        label.setId("aaa");
        label.setFor("bbb");
        label.setKey("ccc");
        encodeByRenderer(renderer, label);
        assertEquals("<label id=\"aaa\" for=\"bbb\"></label>",
                getResponseText());
    }

    public void testEncode_simple4() throws Exception {
        label.setId("aaa");
        label.setFor("bbb");
        label.setKey("ccc");
        label.setValue(new Integer(123));
        encodeByRenderer(renderer, label);
        assertEquals("<label id=\"aaa\" for=\"bbb\">123</label>",
                getResponseText());
    }

    public void testEncode_propertiesNameAndKeyExist() throws Exception {
        label.setId("aaa");
        label.setFor("bbb");
        label.setKey("ccc");
        String packageName = ClassUtil.getPackageName(this.getClass());
        label.setPropertiesName(packageName + ".ddd");
        MockViewHandler handler = new MockViewHandlerImpl();
        handler.setLocale(Locale.JAPANESE);
        getApplication().setViewHandler(handler);
        encodeByRenderer(renderer, label);
        assertEquals("<label id=\"aaa\" for=\"bbb\">CCC</label>",
                getResponseText());
    }

    public void testEncode_propertiesNameAndDefaultKeyExist() throws Exception {
        label.setId("aaa");
        label.setFor("bbb");
        label.setKey("no_such_key");
        label.setDefaultKey("ccc");
        String packageName = ClassUtil.getPackageName(this.getClass());
        label.setPropertiesName(packageName + ".ddd");
        MockViewHandler handler = new MockViewHandlerImpl();
        handler.setLocale(Locale.JAPANESE);
        getApplication().setViewHandler(handler);
        encodeByRenderer(renderer, label);
        assertEquals("<label id=\"aaa\" for=\"bbb\">CCC</label>",
                getResponseText());
    }

    public void testEncode_keyNotFoundAtLocaleJapanese() throws Exception {
        label.setId("aaa");
        label.setFor("bbb");
        label.setKey("eee");
        String packageName = ClassUtil.getPackageName(this.getClass());
        label.setPropertiesName(packageName + ".ddd");
        MockViewHandler handler = new MockViewHandlerImpl();
        handler.setLocale(Locale.JAPANESE);
        getApplication().setViewHandler(handler);
        encodeByRenderer(renderer, label);
        assertEquals("<label id=\"aaa\" for=\"bbb\">EEE</label>",
                getResponseText());
    }

    public void testEncode_keyDuplicateWithMultipleProperties()
            throws Exception {
        HotdeployUtil.setHotdeploy(true);
        label.setId("aaa");
        label.setFor("bbb");
        label.setKey("fff");
        String packageName = ClassUtil.getPackageName(this.getClass());
        label.setPropertiesName(packageName + ".ddd");
        MockViewHandler handler = new MockViewHandlerImpl();
        handler.setLocale(Locale.JAPANESE);
        getApplication().setViewHandler(handler);
        encodeByRenderer(renderer, label);
        assertEquals("<label id=\"aaa\" for=\"bbb\">FFF</label>",
                getResponseText());

        handler.setLocale(Locale.ENGLISH);
        getApplication().setViewHandler(handler);
        encodeByRenderer(renderer, label);
        assertEquals(
                "<label id=\"aaa\" for=\"bbb\">FFF</label><label id=\"aaa\" for=\"bbb\">F_DEFAULT</label>",
                getResponseText());
        HotdeployUtil.clearHotdeploy();
    }

    public void testEncode_defaultPropertiesNameExist() throws Exception {
        label.setId("aaa");
        label.setFor("bbb");
        label.setDefaultKey("ccc");
        String packageName = ClassUtil.getPackageName(this.getClass());
        label.setDefaultPropertiesName(packageName + ".ddd");
        MockViewHandler handler = new MockViewHandlerImpl();
        handler.setLocale(Locale.JAPANESE);
        getApplication().setViewHandler(handler);
        encodeByRenderer(renderer, label);
        assertEquals("<label id=\"aaa\" for=\"bbb\">CCC</label>",
                getResponseText());
    }

    public static class MockTHtmlOutputLabel extends THtmlOutputLabel {

        private Renderer renderer;

        public Renderer getRenderer(FacesContext context) {
            if (this.renderer != null) {
                return this.renderer;
            }
            return super.getRenderer(context);
        }

        public void setRenderer(Renderer renderer) {
            this.renderer = renderer;
        }

    }
}
