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
package javax.faces.component;

import java.math.BigInteger;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.IntegerConverter;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.event.FacesEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.internal.ConverterResource;
import javax.faces.internal.NormalConverterBuilderImpl;
import javax.faces.render.Renderer;
import javax.faces.validator.ValidatorException;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junitx.framework.ObjectAssert;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockMethodBinding;
import org.seasar.teeda.core.mock.MockUIInput;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.NullRenderer;
import org.seasar.teeda.core.mock.NullValidator;

/**
 * @author manhole
 */
public class UIInputOnlyTest extends TestCase {

    public void setUp() throws Exception {
        S2Container container = new S2ContainerImpl();
        SingletonS2ContainerFactory.setContainer(container);
        SingletonS2ContainerFactory.init();
        ConverterResource.setConverterBuilder(new NormalConverterBuilderImpl(
                container));
    }

    public void testConstants() throws Exception {
        assertEquals("javax.faces.Input", UIInput.COMPONENT_FAMILY);
        assertEquals("javax.faces.Input", UIInput.COMPONENT_TYPE);
        assertEquals("javax.faces.component.UIInput.CONVERSION",
                UIInput.CONVERSION_MESSAGE_ID);
        assertEquals("javax.faces.component.UIInput.REQUIRED",
                UIInput.REQUIRED_MESSAGE_ID);
    }

    public void testGetFamily() {
        assertEquals("javax.faces.Input", new UIInput().getFamily());
    }

    public void testDefaultRendererType() throws Exception {
        assertEquals("javax.faces.Text", new UIInput().getRendererType());
    }

    public void testProcessDecodes_CallValidateWhenImmediateIsTrue()
            throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIInput input = new MockUIInput() {
            public void validate(FacesContext context) {
                calls[0] = true;
            }

            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };
        input.setImmediate(true);
        FacesContext context = getFacesContext();

        // ## Act ##
        input.processDecodes(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(false, context.getRenderResponse());
    }

    public void testProcessDecodes_NotCallValidateWhenImmediateIsFalse()
            throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        UIInput input = new MockUIInput() {
            public void validate(FacesContext context) {
                calls[0] = true;
            }

            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };
        input.setImmediate(false);

        // ## Act ##
        input.processDecodes(context);

        // ## Assert ##
        assertEquals(false, calls[0]);
        assertEquals(false, context.getRenderResponse());
    }

    public void testProcessDecodes_CallRenderResponseWhenNotValid()
            throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        final UIInput input = new MockUIInput() {
            public void validate(FacesContext context) {
                calls[0] = true;
                setValid(false);
            }

            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };
        input.setImmediate(true);

        // ## Act ##
        input.processDecodes(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(true, context.getRenderResponse());
    }

    public void testProcessDecodes_CallRenderResponseWhenRuntimeExceptionThrown()
            throws Exception {

        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        final UIInput input = new MockUIInput() {
            public void validate(FacesContext context) {
                calls[0] = true;
                throw new RuntimeException("for test");
            }

            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };
        input.setImmediate(true);

        // ## Act & Assert ##
        try {
            input.processDecodes(context);
            fail();
        } catch (RuntimeException expected) {
            assertEquals("for test", expected.getMessage());
        }
        assertEquals(true, calls[0]);
        assertEquals(true, context.getRenderResponse());
    }

    public void testProcessValidators_CallValidateWhenImmediateIsFalse()
            throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        UIInput input = new MockUIInput() {
            public void validate(FacesContext context) {
                calls[0] = true;
            }
        };
        input.setImmediate(false);
        input.setValid(true);

        // ## Act ##
        input.processValidators(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(false, context.getRenderResponse());
    }

    public void testProcessValidators_NotCallValidateWhenImmediateIsTrue()
            throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        UIInput input = new MockUIInput() {
            public void validate(FacesContext context) {
                calls[0] = true;
            }
        };
        input.setImmediate(true);

        // ## Act ##
        input.processValidators(context);

        // ## Assert ##
        assertEquals(false, calls[0]);
        assertEquals(false, context.getRenderResponse());
    }

    public void testProcessValidators_RenderResponseIsCalledWhenNotValid()
            throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        final UIInput input = new MockUIInput() {
            public void validate(FacesContext context) {
                calls[0] = true;
                setValid(false);
            }
        };
        input.setImmediate(false);

        // ## Act ##
        input.processValidators(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(true, context.getRenderResponse());
    }

    public void testProcessValidators_RenderResponseIsCalledWhenRuntimeExceptionThrown()
            throws Exception {

        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        final UIInput input = new MockUIInput() {
            public void validate(FacesContext context) {
                calls[0] = true;
                throw new RuntimeException("for test");
            }
        };
        input.setImmediate(false);

        // ## Act & Assert ##
        try {
            input.processValidators(context);
            fail();
        } catch (RuntimeException expected) {
            assertEquals("for test", expected.getMessage());
        }
        assertEquals(true, calls[0]);
        assertEquals(true, context.getRenderResponse());
    }

    public void testProcessUpdates_CallUpdateModel() throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        UIInput input = new MockUIInput() {
            public void updateModel(FacesContext context) {
                calls[0] = true;
            }
        };
        input.setValid(true);

        // ## Act ##
        input.processUpdates(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(false, context.getRenderResponse());
    }

    public void testProcessUpdates_RenderResponseIsCalledWhenNotValid()
            throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        UIInput input = new MockUIInput() {
            public void updateModel(FacesContext context) {
                calls[0] = true;
                setValid(false);
            }
        };

        // ## Act ##
        input.processUpdates(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(true, context.getRenderResponse());
    }

    public void testProcessUpdates_RenderResponseIsCalledWhenRuntimeExceptionThrown()
            throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        UIInput input = new MockUIInput() {
            public void updateModel(FacesContext context) {
                calls[0] = true;
                throw new RuntimeException("for test");
            }
        };

        // ## Act ##
        // ## Act & Assert ##
        try {
            input.processUpdates(context);
            fail();
        } catch (RuntimeException expected) {
            assertEquals("for test", expected.getMessage());
        }
        assertEquals(true, calls[0]);
        assertEquals(true, context.getRenderResponse());
    }

    public void testUpdateModel_DoNothingWhenNotValid() throws Exception {
        // ## Arrange ##
        UIInput input = new UIInput();
        input.setValid(false);
        input.setLocalValueSet(true);

        // ## Act ##
        input.updateModel(getFacesContext());

        // ## Assert ##
        assertTrue("take no further action", true);
        assertEquals(false, input.isValid());
        assertEquals(true, input.isLocalValueSet());
    }

    public void testUpdateModel_DoNothingWhenLocalValueNotSet()
            throws Exception {
        // ## Arrange ##
        UIInput input = new UIInput();
        input.setValid(true);
        input.setLocalValueSet(false);

        // ## Act ##
        input.updateModel(getFacesContext());

        // ## Assert ##
        assertTrue("take no further action", true);
        assertEquals(true, input.isValid());
        assertEquals(false, input.isLocalValueSet());
    }

    public void testUpdateModel_DoNothingWhenValueBindingForValueNotSet()
            throws Exception {
        // ## Arrange ##
        UIInput input = new UIInput();
        input.setValid(true);
        input.setValueBinding("value", null);

        // ## Act ##
        input.updateModel(getFacesContext());

        // ## Assert ##
        assertTrue("take no further action", true);
        assertEquals(true, input.isValid());
        assertEquals(false, input.isLocalValueSet());
    }

    public void testUpdateModel_CallValueBindingSetValue() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        final Object[] params = { null };
        UIInput input = new UIInput();
        input.setValue("a");
        input.setValid(true);
        MockValueBinding vb = new MockValueBinding() {
            public void setValue(FacesContext context, Object obj)
                    throws EvaluationException, PropertyNotFoundException {
                calls[0] = true;
                params[0] = obj;
            }
        };
        FacesContext context = getFacesContext();
        input.setValueBinding("value", vb);

        // ## Act ##
        input.updateModel(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals("a", params[0]);
        assertEquals(null, input.getLocalValue());
        assertEquals(false, input.isLocalValueSet());
    }

    public void testUpdateModel_ValueBindingSetValueFailed() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIInput input = new MockUIInput() {
            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };
        input.setValue("a");
        input.setValid(true);
        MockValueBinding vb = new MockValueBinding() {
            public void setValue(FacesContext context, Object obj)
                    throws EvaluationException, PropertyNotFoundException {
                calls[0] = true;
                throw new EvaluationException("forTest");
            }
        };
        MockFacesContext context = getFacesContext();

        input.setValueBinding("value", vb);
        assertEquals(false, context.getMessages().hasNext());

        // ## Act ##
        input.updateModel(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
        Iterator messages = context.getMessages();
        assertEquals(true, messages.hasNext());
        messages.next();
        assertEquals(false, messages.hasNext());
        assertEquals(false, input.isValid());
        assertEquals("a", input.getLocalValue());
        assertEquals(true, input.isLocalValueSet());
    }

    public void testValidate_DoNothingWhenSubmittedValueIsNull()
            throws Exception {
        // ## Arrange ##
        UIInput input = new MockUIInput() {
            protected Object getConvertedValue(FacesContext context,
                    Object submittedValue) throws ConverterException {
                throw new AssertionFailedError("shouldn't be called");
            }
        };
        input.setSubmittedValue(null);
        input.setValid(true);

        // ## Act ##
        input.validate(getFacesContext());

        // ## Assert ##
        assertTrue("take no further action", true);
        assertEquals(true, input.isValid());
    }

    public void testValidate_SetConvertedValue() throws Exception {
        // ## Arrange ##
        UIInput input = new MockUIInput() {
            protected Object getConvertedValue(FacesContext context,
                    Object submittedValue) throws ConverterException {
                return submittedValue + "_" + "converted";
            }
        };
        // for No ValueChangeEvent
        input.setValue("a_converted");
        input.setSubmittedValue("a");
        input.setValid(true);

        // ## Act ##
        input.validate(getFacesContext());

        // ## Assert ##
        assertEquals("a_converted", input.getValue());
        assertEquals(null, input.getSubmittedValue());
    }

    public void testValidate_NotSetConvertedValueWhenNotValid()
            throws Exception {
        // ## Arrange ##
        UIInput input = new MockUIInput() {
            protected Object getConvertedValue(FacesContext context,
                    Object submittedValue) throws ConverterException {
                setValid(false);
                return submittedValue + "_converted";
            }
        };
        input.setSubmittedValue("a");
        input.setValid(true);

        // ## Act ##
        input.validate(getFacesContext());

        // ## Assert ##
        assertEquals(null, input.getValue());
        assertEquals("a", input.getSubmittedValue());
    }

    public void testVaildate_QueueValueChangeEvent() throws Exception {
        // ## Arrange ##
        final FacesEvent[] params = { null };
        UIInput input = new MockUIInput() {
            public void queueEvent(FacesEvent event) {
                params[0] = event;
            }

            protected Object getConvertedValue(FacesContext context,
                    Object submittedValue) throws ConverterException {
                return submittedValue;
            }
        };
        input.setSubmittedValue("a");
        input.setValue("b");
        input.setValid(true);

        // ## Act ##
        input.validate(getFacesContext());

        // ## Assert ##
        assertNotNull(params[0]);
        ObjectAssert.assertInstanceOf(ValueChangeEvent.class, params[0]);
    }

    public void testVaildate_NotQueueValueChangeEvent() throws Exception {
        // ## Arrange ##
        final FacesEvent[] facesEvent = new FacesEvent[1];
        UIInput input = new MockUIInput() {
            public void queueEvent(FacesEvent event) {
                facesEvent[0] = event;
            }

            protected Object getConvertedValue(FacesContext context,
                    Object submittedValue) throws ConverterException {
                return submittedValue;
            }
        };
        input.setSubmittedValue("a");
        input.setValue("a");
        input.setValid(true);

        // ## Act ##
        input.validate(getFacesContext());

        // ## Assert ##
        assertNull(facesEvent[0]);
    }

    public void testGetConvertedValue_UsingRenderer() throws Exception {
        // ## Arrange ##
        UIInput input = new MockUIInput() {
            protected Renderer getRenderer(FacesContext context) {
                return new NullRenderer() {
                    public Object getConvertedValue(FacesContext context,
                            UIComponent component, Object submittedValue)
                            throws ConverterException {
                        return submittedValue + ":converted";
                    }
                };
            }
        };

        // ## Act ##
        Object convertedValue = input.getConvertedValue(getFacesContext(), "1");

        // ## Assert ##
        assertEquals("1:converted", convertedValue);
    }

    public void testGetConvertedValue_UsingConverter() throws Exception {
        // ## Arrange ##
        UIInput input = new MockUIInput() {
            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };

        input.setConverter(new MockConverter() {
            public Object getAsObject(FacesContext context,
                    UIComponent component, String value)
                    throws ConverterException {
                return value + "@";
            }
        });

        // ## Act ##
        Object convertedValue = input.getConvertedValue(getFacesContext(), "a");

        // ## Assert ##
        assertEquals("a@", convertedValue);
    }

    public void testGetConvertedValue_NoConversionWhenValueBindingGetTypeReturnsNull()
            throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIInput input = new MockUIInput() {
            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };
        input.setConverter(null);
        MockValueBinding vb = new MockValueBinding() {
            public Class getType(FacesContext context)
                    throws EvaluationException, PropertyNotFoundException {
                calls[0] = true;
                return null;
            }
        };
        input.setValueBinding("value", vb);

        // ## Act ##
        Object convertedValue = input.getConvertedValue(getFacesContext(), "a");

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals("a", convertedValue);
    }

    public void testGetConvertedValue_CreateConverterFromValueBindingType()
            throws Exception {
        // ## Arrange ##
        UIInput input = new MockUIInput() {
            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };
        input.setConverter(null);
        MockValueBinding vb = new MockValueBinding() {
            public Class getType(FacesContext context)
                    throws EvaluationException, PropertyNotFoundException {
                return BigInteger.class;
            }
        };
        input.setValueBinding("value", vb);

        final Object[] createConverterParams = { null };
        MockFacesContext context = getFacesContext();
        context.setApplication(new MockApplicationImpl() {
            public Converter createConverter(Class targetClass) {
                createConverterParams[0] = targetClass;
                return new MockConverter() {
                    public Object getAsObject(FacesContext context,
                            UIComponent component, String value)
                            throws ConverterException {
                        return value + "aa";
                    }
                };
            }
        });

        // ## Act ##
        Object convertedValue = input.getConvertedValue(context, "22");

        // ## Assert ##
        assertEquals("22aa", convertedValue);
        assertEquals(BigInteger.class, createConverterParams[0]);
    }

    public void testGetConvertedValue_WithoutAnyConversionWhenNoRendererAndNotStringSubmitted()
            throws Exception {
        // ## Arrange ##
        UIInput input = new MockUIInput() {
            protected Renderer getRenderer(FacesContext context) {
                return null;
            }

            public Converter getConverter() {
                throw new AssertionFailedError("shouldn't be called");
            }
        };
        input.setValueBinding("value", null);

        // ## Act ##
        // not String
        Object convertedValue = input.getConvertedValue(getFacesContext(),
                new Integer(123));

        // ## Assert ##
        assertEquals(new Integer(123), convertedValue);
    }

    public void testGetConvertedValue_WithoutAnyConversion2() throws Exception {
        // ## Arrange ##
        UIInput input = new MockUIInput() {
            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };
        input.setConverter(null);
        input.setValueBinding("value", null);

        // ## Act ##
        Object convertedValue = input.getConvertedValue(getFacesContext(), "a");

        // ## Assert ##
        assertEquals("a", convertedValue);
    }

    public void testValidateValue_DoNothingWhenNotValid() throws Exception {
        // ## Arrange ##
        UIInput input = new UIInput();
        input.setValid(false);
        input.setRequired(true);

        // ## Act ##
        input.validateValue(getFacesContext(), "b");

        // ## Assert ##
        assertTrue("take no further action", true);
        assertEquals(false, input.isValid());
        assertEquals(true, input.isRequired());
    }

    public void testValidateValue_DoNothingWhenNotRequired() throws Exception {
        // ## Arrange ##
        UIInput input = new UIInput();
        input.setValid(true);
        input.setRequired(false);

        // ## Act ##
        input.validateValue(getFacesContext(), "b");

        // ## Assert ##
        assertTrue("take no further action", true);
        assertEquals(true, input.isValid());
        assertEquals(false, input.isRequired());
    }

    public void testValidateValue_LocalValueIsEmpty() throws Exception {
        // ## Arrange ##
        UIInput input = new MockUIInput() {
            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };
        input.setValid(true);
        input.setRequired(true);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        input.validateValue(context, "");

        // ## Assert ##
        Iterator messages = context.getMessages();
        assertEquals(true, messages.hasNext());
        messages.next();
        assertEquals(false, messages.hasNext());

        assertEquals(false, input.isValid());
    }

    public void testValidateValue_CallAddedValidators() throws Exception {
        // ## Arrange ##
        final boolean[] calls = new boolean[2];
        UIInput input = new MockUIInput();
        input.setValid(true);
        input.setRequired(true);
        input.addValidator(new NullValidator() {
            public void validate(FacesContext context, UIComponent component,
                    Object value) throws FacesException {
                calls[0] = true;
            }
        });
        input.addValidator(new NullValidator() {
            public void validate(FacesContext context, UIComponent component,
                    Object value) throws FacesException {
                calls[1] = true;
            }
        });

        MockFacesContext context = getFacesContext();

        // ## Act ##
        input.validateValue(context, "123");

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(true, calls[1]);
        assertEquals(true, input.isValid());
    }

    public void testValidateValue_CallSettedValidator() throws Exception {
        // ## Arrange ##
        UIInput input = new MockUIInput() {
            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };
        input.setValid(true);
        input.setRequired(true);

        MockMethodBinding validatorBinding = new MockMethodBinding();
        input.setValidator(validatorBinding);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        input.validateValue(context, "123");

        // ## Assert ##
        assertEquals(true, validatorBinding.isInvokeCalled());
        assertEquals(true, input.isValid());
    }

    public void testValidateValue_CallValidatorsAndFailed() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false, false };
        UIInput input = new MockUIInput() {
            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };
        input.setValid(true);
        input.setRequired(true);
        input.addValidator(new NullValidator() {
            public void validate(FacesContext context, UIComponent component,
                    Object value) throws FacesException {
                calls[0] = true;
                throw new ValidatorException(new FacesMessage("for test"));
            }
        });
        input.addValidator(new NullValidator() {
            public void validate(FacesContext context, UIComponent component,
                    Object value) throws FacesException {
                calls[1] = true;
            }
        });

        MockMethodBinding validatorBinding = new MockMethodBinding();
        input.setValidator(validatorBinding);

        MockFacesContext context = getFacesContext();

        // ## Act ##
        input.validateValue(context, "123");

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(true, calls[1]);
        assertEquals(true, validatorBinding.isInvokeCalled());
        assertEquals(false, input.isValid());

        Iterator messages = context.getMessages();
        assertEquals(true, messages.hasNext());
        messages.next();
        assertEquals(false, messages.hasNext());
    }

    public final void testGetConvertedValue() throws Exception {
        // ## Arrange ##
        UIViewRoot root = new UIViewRoot();
        UIInput input = new UIInput();
        input.setParent(root);
        input.setSubmittedValue("1000");
        input.setValid(true);
        SingletonS2ContainerFactory.getContainer().register(
                new IntegerConverter() {

                    public Object getAsObject(FacesContext context,
                            UIComponent component, String value)
                            throws ConverterException {
                        return new Integer(value);
                    }

                }, "integerConverter");
        ConverterResource.addConverter("#{a.b}", "integerConverter");
        input.setValueBinding("value", new MockValueBinding() {
            public String getExpressionString() {
                return "#{a.b}";
            }

        });

        // ## Act ##
        input.validate(getFacesContext());

        // ## Assert ##
        assertEquals(new Integer(1000), input.getValue());
    }

    private MockFacesContext getFacesContext() {
        MockFacesContext context = new MockFacesContextImpl();
        return context;
    }

}
