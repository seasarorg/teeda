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

import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;

/**
 * @author manhole
 */
public class UIInputTeedaTest extends UIOutputTeedaTest {

    public void testProcessDecodes_CallValidateWhenImmediateIsTrue()
            throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        UIInput input = new UIInput() {
            public void validate(FacesContext context) {
                calls[0] = true;
            }
        };
        input.setImmediate(true);

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
        UIInput input = new UIInput() {
            public void validate(FacesContext context) {
                calls[0] = true;
            }
        };
        input.setImmediate(false);

        // ## Act ##
        input.processDecodes(context);

        // ## Assert ##
        assertEquals(false, calls[0]);
        assertEquals(false, context.getRenderResponse());
    }

    public void testProcessDecodes_RenderResponseIsCalledWhenComponentIsInvalid()
            throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        final UIInput input = new UIInput() {
            public void validate(FacesContext context) {
                calls[0] = true;
                setValid(false);
            }
        };
        input.setImmediate(true);

        // ## Act ##
        input.processDecodes(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(true, context.getRenderResponse());
    }

    public void testProcessDecodes_RenderResponseIsCalledWhenRuntimeExceptionThrown()
            throws Exception {

        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        final UIInput input = new UIInput() {
            public void validate(FacesContext context) {
                calls[0] = true;
                throw new RuntimeException("for test");
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
        UIInput input = new UIInput() {
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
        UIInput input = new UIInput() {
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

    public void testProcessValidators_RenderResponseIsCalledWhenComponentIsInvalid()
            throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        final UIInput input = new UIInput() {
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
        final UIInput input = new UIInput() {
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
        UIInput input = new UIInput() {
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

    public void testProcessUpdates_RenderResponseIsCalledWhenComponentIsInvalid()
            throws Exception {
        // ## Arrange ##
        FacesContext context = getFacesContext();
        final boolean[] calls = { false };
        UIInput input = new UIInput() {
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
        UIInput input = new UIInput() {
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

    // TODO test: validate more tests
    public void testVaildate_QueueValueChangeEvent() throws Exception {
        // ## Arrange ##
        final FacesEvent[] facesEvent = new FacesEvent[1];
        UIInput input = new UIInput() {
            public void queueEvent(FacesEvent event) {
                facesEvent[0] = event;
            }
        };
        input.setSubmittedValue("a");
        input.setValue("b");
        input.setValid(true);

        // ## Act ##
        input.validate(getFacesContext());

        // ## Assert ##
        assertNotNull(facesEvent[0]);
    }

    public void testVaildate_NotQueueValueChangeEvent() throws Exception {
        // ## Arrange ##
        final FacesEvent[] facesEvent = new FacesEvent[1];
        UIInput input = new UIInput() {
            public void queueEvent(FacesEvent event) {
                facesEvent[0] = event;
            }
        };
        input.setSubmittedValue("a");
        input.setValue("a");

        // ## Act ##
        input.validate(getFacesContext());

        // ## Assert ##
        assertNull(facesEvent[0]);
    }

    private UIInput createUIInput() {
        return (UIInput) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIInput();
    }

}
