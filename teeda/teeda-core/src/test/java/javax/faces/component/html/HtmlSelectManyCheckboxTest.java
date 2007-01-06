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
package javax.faces.component.html;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectManyTest;
import javax.faces.context.FacesContext;
import javax.faces.internal.ValidatorResource;
import javax.faces.render.RenderKitFactory;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockRenderKitImpl;
import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class HtmlSelectManyCheckboxTest extends UISelectManyTest {

    public void testSetGetAccesskey() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setAccesskey("foo accesskey");
        assertEquals("foo accesskey", component.getAccesskey());
    }

    public void testSetGetAccesskey_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar accesskey");
        component.setValueBinding("accesskey", vb);
        assertEquals("bar accesskey", component.getAccesskey());
        assertEquals("bar accesskey", component.getValueBinding("accesskey")
                .getValue(context));
    }

    public void testSetGetBorder() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setBorder(76);
        assertEquals(76, component.getBorder());
    }

    public void testSetGetBorder_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, new Integer(41));
        component.setValueBinding("border", vb);
        assertEquals(41, component.getBorder());
        assertEquals(new Integer(41), component.getValueBinding("border")
                .getValue(context));
    }

    public void testSetGetDir() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setDir("foo dir");
        assertEquals("foo dir", component.getDir());
    }

    public void testSetGetDir_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar dir");
        component.setValueBinding("dir", vb);
        assertEquals("bar dir", component.getDir());
        assertEquals("bar dir", component.getValueBinding("dir").getValue(
                context));
    }

    public void testSetGetDisabled() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setDisabled(true);
        assertEquals(true, component.isDisabled());
    }

    public void testSetGetDisabled_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, Boolean.TRUE);
        component.setValueBinding("disabled", vb);
        assertEquals(true, component.isDisabled());
        assertEquals(Boolean.TRUE, component.getValueBinding("disabled")
                .getValue(context));
    }

    public void testSetGetDisabledClass() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setDisabledClass("foo class");
        assertEquals("foo class", component.getDisabledClass());
    }

    public void testSetGetDisabledClass_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar disabledClass");
        component.setValueBinding("disabledClass", vb);
        assertEquals("bar disabledClass", component.getDisabledClass());
        assertEquals("bar disabledClass", component.getValueBinding(
                "disabledClass").getValue(context));
    }

    public void testSetGetEnabledClass() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setEnabledClass("foo class");
        assertEquals("foo class", component.getEnabledClass());
    }

    public void testSetGetEnabledClass_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar enabledClass");
        component.setValueBinding("enabledClass", vb);
        assertEquals("bar enabledClass", component.getEnabledClass());
        assertEquals("bar enabledClass", component.getValueBinding(
                "enabledClass").getValue(context));
    }

    public void testSetGetLang() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setLang("foo lang");
        assertEquals("foo lang", component.getLang());
    }

    public void testSetGetLang_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar lang");
        component.setValueBinding("lang", vb);
        assertEquals("bar lang", component.getLang());
        assertEquals("bar lang", component.getValueBinding("lang").getValue(
                context));
    }

    public void testSetGetLayout() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setLayout("foo layout");
        assertEquals("foo layout", component.getLayout());
    }

    public void testSetGetLayout_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar layout");
        component.setValueBinding("layout", vb);
        assertEquals("bar layout", component.getLayout());
        assertEquals("bar layout", component.getValueBinding("layout")
                .getValue(context));
    }

    public void testSetGetOnblur() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setOnblur("foo onblur");
        assertEquals("foo onblur", component.getOnblur());
    }

    public void testSetGetOnblur_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onblur");
        component.setValueBinding("onblur", vb);
        assertEquals("bar onblur", component.getOnblur());
        assertEquals("bar onblur", component.getValueBinding("onblur")
                .getValue(context));
    }

    public void testSetGetOnchange() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setOnchange("foo onchange");
        assertEquals("foo onchange", component.getOnchange());
    }

    public void testSetGetOnchange_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onchange");
        component.setValueBinding("onchange", vb);
        assertEquals("bar onchange", component.getOnchange());
        assertEquals("bar onchange", component.getValueBinding("onchange")
                .getValue(context));
    }

    public void testSetGetOnclick() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setOnclick("foo onclick");
        assertEquals("foo onclick", component.getOnclick());
    }

    public void testSetGetOnclick_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onclick");
        component.setValueBinding("onclick", vb);
        assertEquals("bar onclick", component.getOnclick());
        assertEquals("bar onclick", component.getValueBinding("onclick")
                .getValue(context));
    }

    public void testSetGetOndblclick() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setOndblclick("foo ondblclick");
        assertEquals("foo ondblclick", component.getOndblclick());
    }

    public void testSetGetOndblclick_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar ondblclick");
        component.setValueBinding("ondblclick", vb);
        assertEquals("bar ondblclick", component.getOndblclick());
        assertEquals("bar ondblclick", component.getValueBinding("ondblclick")
                .getValue(context));
    }

    public void testSetGetOnfocus() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setOnfocus("foo onfocus");
        assertEquals("foo onfocus", component.getOnfocus());
    }

    public void testSetGetOnfocus_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onfocus");
        component.setValueBinding("onfocus", vb);
        assertEquals("bar onfocus", component.getOnfocus());
        assertEquals("bar onfocus", component.getValueBinding("onfocus")
                .getValue(context));
    }

    public void testSetGetOnkeydown() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setOnkeydown("foo onkeydown");
        assertEquals("foo onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeydown_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeydown");
        component.setValueBinding("onkeydown", vb);
        assertEquals("bar onkeydown", component.getOnkeydown());
        assertEquals("bar onkeydown", component.getValueBinding("onkeydown")
                .getValue(context));
    }

    public void testSetGetOnkeypress() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setOnkeypress("foo onkeypress");
        assertEquals("foo onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeypress_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeypress");
        component.setValueBinding("onkeypress", vb);
        assertEquals("bar onkeypress", component.getOnkeypress());
        assertEquals("bar onkeypress", component.getValueBinding("onkeypress")
                .getValue(context));
    }

    public void testSetGetOnkeyup() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setOnkeyup("foo onkeyup");
        assertEquals("foo onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnkeyup_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeyup");
        component.setValueBinding("onkeyup", vb);
        assertEquals("bar onkeyup", component.getOnkeyup());
        assertEquals("bar onkeyup", component.getValueBinding("onkeyup")
                .getValue(context));
    }

    public void testSetGetOnmousedown() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setOnmousedown("foo onmousedown");
        assertEquals("foo onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousedown_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmousedown");
        component.setValueBinding("onmousedown", vb);
        assertEquals("bar onmousedown", component.getOnmousedown());
        assertEquals("bar onmousedown", component
                .getValueBinding("onmousedown").getValue(context));
    }

    public void testSetGetOnmousemove() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setOnmousemove("foo onmousemove");
        assertEquals("foo onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmousemove_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmousemove");
        component.setValueBinding("onmousemove", vb);
        assertEquals("bar onmousemove", component.getOnmousemove());
        assertEquals("bar onmousemove", component
                .getValueBinding("onmousemove").getValue(context));
    }

    public void testSetGetOnmouseout() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setOnmouseout("foo onmouseout");
        assertEquals("foo onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseout_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseout");
        component.setValueBinding("onmouseout", vb);
        assertEquals("bar onmouseout", component.getOnmouseout());
        assertEquals("bar onmouseout", component.getValueBinding("onmouseout")
                .getValue(context));
    }

    public void testSetGetOnmouseover() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setOnmouseover("foo onmouseover");
        assertEquals("foo onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseover_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseover");
        component.setValueBinding("onmouseover", vb);
        assertEquals("bar onmouseover", component.getOnmouseover());
        assertEquals("bar onmouseover", component
                .getValueBinding("onmouseover").getValue(context));
    }

    public void testSetGetOnmouseup() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setOnmouseup("foo onmouseup");
        assertEquals("foo onmouseup", component.getOnmouseup());
    }

    public void testSetGetOnmouseup_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseup");
        component.setValueBinding("onmouseup", vb);
        assertEquals("bar onmouseup", component.getOnmouseup());
        assertEquals("bar onmouseup", component.getValueBinding("onmouseup")
                .getValue(context));
    }

    public void testSetGetOnselect() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setOnselect("foo onselect");
        assertEquals("foo onselect", component.getOnselect());
    }

    public void testSetGetOnselect_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onselect");
        component.setValueBinding("onselect", vb);
        assertEquals("bar onselect", component.getOnselect());
        assertEquals("bar onselect", component.getValueBinding("onselect")
                .getValue(context));
    }

    public void testSetGetReadonly() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setReadonly(true);
        assertEquals(true, component.isReadonly());
    }

    public void testSetGetReadonly_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, Boolean.TRUE);
        component.setValueBinding("readonly", vb);
        assertEquals(true, component.isReadonly());
        assertEquals(Boolean.TRUE, component.getValueBinding("readonly")
                .getValue(context));
    }

    public void testSetGetStyle() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setStyle("foo style");
        assertEquals("foo style", component.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar style");
        component.setValueBinding("style", vb);
        assertEquals("bar style", component.getStyle());
        assertEquals("bar style", component.getValueBinding("style").getValue(
                context));
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setStyleClass("foo styleClass");
        assertEquals("foo styleClass", component.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar styleClass");
        component.setValueBinding("styleClass", vb);
        assertEquals("bar styleClass", component.getStyleClass());
        assertEquals("bar styleClass", component.getValueBinding("styleClass")
                .getValue(context));
    }

    public void testSetGetTabindex() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setTabindex("foo tabindex");
        assertEquals("foo tabindex", component.getTabindex());
    }

    public void testSetGetTabindex_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar tabindex");
        component.setValueBinding("tabindex", vb);
        assertEquals("bar tabindex", component.getTabindex());
        assertEquals("bar tabindex", component.getValueBinding("tabindex")
                .getValue(context));
    }

    public void testSetGetTitle() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setTitle("foo title");
        assertEquals("foo title", component.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar title");
        component.setValueBinding("title", vb);
        assertEquals("bar title", component.getTitle());
        assertEquals("bar title", component.getValueBinding("title").getValue(
                context));
    }

    public void testSetGetLabel() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setLabel("label1");
        assertEquals("label1", component.getLabel());
    }

    public void testSetGetLabel_ValueBinding() throws Exception {
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar label");
        component.setValueBinding("label", vb);
        assertEquals("bar label", component.getLabel());
        assertEquals("bar label", component.getValueBinding("label").getValue(
                context));
    }

    public void testValidate() throws Exception {
        FactoryFinder.setFactory(FactoryFinder.RENDER_KIT_FACTORY,
                "org.seasar.teeda.core.mock.MockRenderKitFactory");
        RenderKitFactory factory = (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        factory.addRenderKit("hoge", new MockRenderKitImpl());
        MockFacesContext facesContext = getFacesContext();
        facesContext.getViewRoot().setRenderKitId("hoge");
        HtmlSelectManyCheckbox component = createHtmlSelectManyCheckbox();
        component.setValid(true);
        component.setValueBinding("value", new MockValueBinding("#{a.b}"));
        final FacesMessage m = new FacesMessage("aaa");
        ValidatorResource.addValidator("#{a.b}", new Validator() {

            public void validate(FacesContext context, UIComponent component,
                    Object value) throws FacesException {
                throw new ValidatorException(m);
            }

        });
        facesContext.getExternalContext().getRequestMap().put("postback",
                new Boolean(true));
        try {
            component.validate(facesContext);
        } catch (ValidatorException e) {
            fail();
        }
        Iterator messages = facesContext.getMessages();
        FacesMessage fm = (FacesMessage) messages.next();
        assertEquals("aaa", fm.getSummary());
    }

    private HtmlSelectManyCheckbox createHtmlSelectManyCheckbox() {
        return (HtmlSelectManyCheckbox) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlSelectManyCheckbox();
    }

}
