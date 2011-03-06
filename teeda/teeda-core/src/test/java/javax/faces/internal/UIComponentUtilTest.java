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
package javax.faces.internal;

import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.internal.web.foo.FooPage;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author manhole
 * @author shot
 */
public class UIComponentUtilTest extends TeedaTestCase {

    public void testIsDisabled() throws Exception {
        {
            UIComponent component = new MockUIComponentBase();
            assertEquals(false, UIComponentUtil.isDisabled(component));
        }
        {
            HtmlInputText component = new HtmlInputText();
            component.setDisabled(true);
            assertEquals(true, UIComponentUtil.isDisabled(component));
        }
        {
            HtmlInputText component = new HtmlInputText();
            component.setDisabled(false);
            assertEquals(false, UIComponentUtil.isDisabled(component));
        }
        {
            HtmlInputText component = new HtmlInputText();
            component.getAttributes().put("disabled", Boolean.TRUE);
            assertEquals(true, UIComponentUtil.isDisabled(component));
        }
    }

    public void testGetStringAttribute() throws Exception {
        HtmlInputText component = new HtmlInputText();
        component.getAttributes().put("onblur", "aaaa");
        assertEquals("aaaa", UIComponentUtil.getStringAttribute(component,
                "onblur"));
    }

    public void testGetPrimitiveBooleanAttribute() throws Exception {
        HtmlInputText component = new HtmlInputText();
        component.getAttributes().put("readonly", Boolean.TRUE);
        assertEquals(true, UIComponentUtil.getPrimitiveBooleanAttribute(
                component, "readonly"));
    }

    public void testGetPrimitiveIntAttribute() throws Exception {
        HtmlInputText component = new HtmlInputText();
        assertEquals(UIDefaultAttribute.DEFAULT_INT, UIComponentUtil
                .getPrimitiveIntAttribute(component, "size"));
        component.setSize(321);
        assertEquals(321, UIComponentUtil.getPrimitiveIntAttribute(component,
                "size"));
    }

    public void testGetLabel_labelReturned() {
        MockHtmlInputText component = new MockHtmlInputText();
        component.setId("aaa");
        component.setLabel("bbb");
        assertEquals("bbb", UIComponentUtil.getLabel(component));
    }

    public void testGetLabel_labelProperties() {
        NamingConventionImpl nc = (NamingConventionImpl) getContainer()
                .getComponent(NamingConvention.class);
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        register(FooPage.class, "foo_fooPage");
        FacesConfigOptions.setDefaultSuffix(".html");
        getFacesContext().getViewRoot().setViewId(
                nc.getViewRootPath() + "/foo/foo.html");
        MockHtmlInputText component = new MockHtmlInputText();
        component.setId("aaa");
        assertEquals("AAA", UIComponentUtil.getLabel(component));
    }

    public void testGetLabel_idReturned() {
        NamingConventionImpl nc = (NamingConventionImpl) getContainer()
                .getComponent(NamingConvention.class);
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        getFacesContext().getViewRoot().setViewId(
                nc.getViewRootPath() + "/foo/foo.html");
        FacesConfigOptions.setDefaultSuffix(".html");
        UIComponent component = new HtmlInputText();
        component.setId("xxx");
        assertEquals("xxx", UIComponentUtil.getLabel(component));
    }

    public void testFindDescendant() {
        UIViewRoot root = new UIViewRoot();
        UIForm form = new UIForm();
        form.getChildren().add(new UIInput());
        root.getChildren().add(form);
        UICommand command = new UICommand();
        root.getChildren().add(command);
        assertSame(command, UIComponentUtil.findDescendant(root,
                UICommand.class));
    }

    public void testCollectDescendants() throws Exception {
        UIViewRoot root = new UIViewRoot();
        UIForm form = new UIForm();
        UIInput input = new UIInput();
        input.setId("a1");
        form.getChildren().add(input);

        UIInput input2 = new UIInput();
        input2.setId("a2");
        form.getChildren().add(input2);

        UIInput input3 = new UIInput();
        input3.setId("a3");
        form.getChildren().add(input3);

        root.getChildren().add(form);

        List list = UIComponentUtil.collectDescendants(root, UIInput.class);
        assertNotNull(list);
        assertTrue(list.size() == 3);
    }

    public void testCallValidators_justNormalValidatorCall() throws Exception {
        UIInput input = new UIInput();
        input.setId("aaa");
        final boolean[] call = { false };
        input.addValidator(new Validator() {

            public void validate(FacesContext context, UIComponent component,
                    Object value) throws FacesException {
                call[0] = true;
                throw new ValidatorException(new FacesMessage() {

                    private static final long serialVersionUID = 1L;

                    public String getSummary() {
                        return "hoge";
                    }

                });
            }

        });

        UIComponentUtil.callValidators(getFacesContext(), input, "123");

        assertTrue(call[0]);
        Iterator messages = getFacesContext().getMessages();
        assertNotNull(messages);
        FacesMessage m = (FacesMessage) messages.next();
        assertEquals("hoge", m.getSummary());
    }

    public void testCallValidators_methodValidatorCall0() throws Exception {
        UIInput input = new UIInput();
        input.setId("aaa");
        final boolean[] call = { false };
        input.setValidator(new MethodBinding() {

            public Class getType(FacesContext context)
                    throws MethodNotFoundException {
                return null;
            }

            public Object invoke(FacesContext context, Object[] params)
                    throws EvaluationException, MethodNotFoundException {
                call[0] = true;
                return null;
            }

        });

        UIComponentUtil.callValidators(getFacesContext(), input, "123");
        assertTrue(call[0]);
    }

    public void testCallValidators_methodValidatorCall1() throws Exception {
        UIInput input = new UIInput();
        input.setId("aaa");
        final boolean[] call = { false };
        input.setValidator(new MethodBinding() {

            public Class getType(FacesContext context)
                    throws MethodNotFoundException {
                return null;
            }

            public Object invoke(FacesContext context, Object[] params)
                    throws EvaluationException, MethodNotFoundException {
                call[0] = true;
                ValidatorException e = new ValidatorException(
                        new FacesMessage() {

                            private static final long serialVersionUID = 1L;

                            public String getSummary() {
                                return "hoge";
                            }

                        });
                throw new EvaluationException(e);
            }

        });

        UIComponentUtil.callValidators(getFacesContext(), input, "123");

        assertTrue(call[0]);
        Iterator messages = getFacesContext().getMessages();
        assertNotNull(messages);
        FacesMessage m = (FacesMessage) messages.next();
        assertEquals("hoge", m.getSummary());
    }

    public void testCallValidators_methodValidatorCall2() throws Exception {
        UIInput input = new UIInput();
        input.setId("aaa");
        final boolean[] call = { false };
        input.setValidator(new MethodBinding() {

            public Class getType(FacesContext context)
                    throws MethodNotFoundException {
                return null;
            }

            public Object invoke(FacesContext context, Object[] params)
                    throws EvaluationException, MethodNotFoundException {
                call[0] = true;
                throw new EvaluationException(new Exception() {

                    public String getMessage() {
                        return "aaa";
                    }

                });
            }

        });

        try {
            UIComponentUtil.callValidators(getFacesContext(), input, "123");
            fail();
        } catch (EvaluationException expected) {
            assertEquals("aaa", expected.getCause().getMessage());
            success();
        }
    }

    public static class MockHtmlInputText extends HtmlInputText {

        private String label_;

        public String getLabel() {
            return label_;
        }

        public void setLabel(String label) {
            label_ = label;
        }
    }

}
