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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.internal.SerializableStateHolder;
import javax.faces.render.Renderer;

import junit.framework.TestCase;
import junitx.framework.ObjectAssert;

import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockStateHolder;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.NullFacesContext;
import org.seasar.teeda.core.mock.NullRenderer;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.ExceptionAssert;

public class UIComponentBaseOnlyTest extends TestCase {

    public void testGetRendersChildren() {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIComponentBase component = new MockUIComponentBase() {
            protected Renderer getRenderer(FacesContext context) {
                return new NullRenderer() {
                    public boolean getRendersChildren() {
                        calls[0] = true;
                        return false;
                    }
                };
            }
        };
        component.setRendererType("foo");

        // ## Act ##
        component.getRendersChildren();

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testGetRendersChildren_NoRenderer() {
        // ## Arrange ##
        UIComponentBase component = new MockUIComponentBase() {
            protected Renderer getRenderer(FacesContext context) {
                return null;
            }
        };

        // ## Act ##
        // ## Assert ##
        assertEquals(false, component.getRendersChildren());
    }

    public void testEncodeBegin() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIComponentBase component = new MockUIComponentBase() {
            protected Renderer getRenderer(FacesContext context) {
                return new NullRenderer() {
                    public void encodeBegin(FacesContext context,
                            UIComponent component) throws IOException {
                        calls[0] = true;
                    }
                };
            }
        };
        // ## Act ##
        component.encodeBegin(new NullFacesContext());

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testEncodeBegin_RenderFalse() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIComponentBase component = new MockUIComponentBase() {
            protected Renderer getRenderer(FacesContext context) {
                return new NullRenderer() {
                    public void encodeBegin(FacesContext context,
                            UIComponent component) throws IOException {
                        calls[0] = true;
                    }
                };
            }
        };
        component.setRendered(false);

        // ## Act ##
        component.encodeBegin(new NullFacesContext());

        // ## Assert ##
        assertEquals(false, calls[0]);
    }

    public void testEncodeChildren() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIComponentBase component = new MockUIComponentBase() {
            protected Renderer getRenderer(FacesContext context) {
                return new NullRenderer() {
                    public void encodeChildren(FacesContext context,
                            UIComponent component) throws IOException {
                        calls[0] = true;
                    }
                };
            }
        };
        // ## Act ##
        component.encodeChildren(new NullFacesContext());

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testEncodeChildren_RenderFalse() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIComponentBase component = new MockUIComponentBase() {
            protected Renderer getRenderer(FacesContext context) {
                return new NullRenderer() {
                    public void encodeChildren(FacesContext context,
                            UIComponent component) throws IOException {
                        calls[0] = true;
                    }
                };
            }
        };
        component.setRendered(false);

        // ## Act ##
        component.encodeChildren(new NullFacesContext());

        // ## Assert ##
        assertEquals(false, calls[0]);
    }

    public void testEncodeEnd() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIComponentBase component = new MockUIComponentBase() {
            protected Renderer getRenderer(FacesContext context) {
                return new NullRenderer() {
                    public void encodeEnd(FacesContext context,
                            UIComponent component) throws IOException {
                        calls[0] = true;
                    }
                };
            }
        };
        // ## Act ##
        component.encodeEnd(new NullFacesContext());

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testEncodeEnd_RenderFalse() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIComponentBase component = new MockUIComponentBase() {
            protected Renderer getRenderer(FacesContext context) {
                return new NullRenderer() {
                    public void encodeEnd(FacesContext context,
                            UIComponent component) throws IOException {
                        calls[0] = true;
                    }
                };
            }
        };
        component.setRendered(false);

        // ## Act ##
        component.encodeEnd(new NullFacesContext());

        // ## Assert ##
        assertEquals(false, calls[0]);
    }

    public void testProcessSaveState_CallSaveState() throws Exception {
        // ## Arrange ##
        final List callSeq = new ArrayList();
        UIComponentBase component = new MockUIComponentBase() {
            public Object saveState(FacesContext context) {
                callSeq.add("2");
                return "aaa";
            }
        };
        {
            component.getChildren().add(new NullUIComponent() {
                public Object processSaveState(FacesContext context) {
                    callSeq.add("1");
                    return "1";
                }
            });
        }

        // ## Act ##
        component.processSaveState(new NullFacesContext());

        // ## Assert ##
        assertEquals(2, callSeq.size());
        assertEquals("1", callSeq.get(0));
        assertEquals("2", callSeq.get(1));
    }

    public final void testSaveAttachedState_ContextIsNull() throws Exception {
        try {
            UIComponentBase.saveAttachedState(null, new Object());
            fail();
        } catch (NullPointerException npe) {
            String message = npe.getMessage();
            assertNotNull(message);
            assertTrue(message.trim().length() > 0);
        }
    }

    public final void testRestoreAttachedState_IllegalState() throws Exception {
        FacesContext context = new MockFacesContextImpl();
        try {
            UIComponentBase.restoreAttachedState(context, new Object());
            fail();
        } catch (IllegalStateException ise) {
            ExceptionAssert.assertMessageExist(ise);
        } finally {
            context.release();
        }
    }

    public void testSaveAndRestoreAttachedState() {
        MockStateHolder holder = new MockStateHolder();
        holder.setValue("321");
        FacesContext context = new MockFacesContextImpl();
        try {
            Object state = UIComponentBase.saveAttachedState(context, holder);

            ObjectAssert.assertInstanceOf(Serializable.class, state);

            MockStateHolder restored = (MockStateHolder) UIComponentBase
                    .restoreAttachedState(context, state);
            assertEquals("321", restored.getValue());
        } finally {
            context.release();
        }
    }

    //

    public void testProcessDecodes_CallFacetsAndChildren() throws Exception {
        // ## Arrange ##
        final List callSeq = new ArrayList();
        UIComponentBase component = new MockUIComponentBase();
        {
            component.getChildren().add(new NullUIComponent() {
                public void processDecodes(FacesContext context) {
                    callSeq.add("1");
                }
            });
            component.getChildren().add(new NullUIComponent() {
                public void processDecodes(FacesContext context) {
                    callSeq.add("2");
                }
            });
            component.getFacets().put("A", new NullUIComponent() {
                public void processDecodes(FacesContext context) {
                    callSeq.add("3");
                }
            });
            component.getFacets().put("B", new NullUIComponent() {
                public void processDecodes(FacesContext context) {
                    callSeq.add("4");
                }
            });
            component.getChildren().add(new NullUIComponent() {
                public void processDecodes(FacesContext context) {
                    callSeq.add("5");
                }
            });
        }

        // ## Act ##
        component.processDecodes(new NullFacesContext());

        // ## Assert ##
        assertEquals(5, callSeq.size());
    }

    public void testProcessDecodes_RenderFalse() throws Exception {
        // ## Arrange ##
        final List callSeq = new ArrayList();
        UIComponentBase component = new MockUIComponentBase();
        component.setRendered(false);
        {
            component.getFacets().put("B", new NullUIComponent() {
                public void processDecodes(FacesContext context) {
                    callSeq.add("4");
                }
            });
            component.getChildren().add(new NullUIComponent() {
                public void processDecodes(FacesContext context) {
                    callSeq.add("5");
                }
            });
        }

        // ## Act ##
        component.processDecodes(new NullFacesContext());

        // ## Assert ##
        assertEquals(0, callSeq.size());
    }

    public final void testProcessDecodes_DecodeThrowsException_RenderResponseCalled()
            throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        final RuntimeException runtimeException = new RuntimeException(
                "for test");
        UIComponentBase component = new MockUIComponentBase() {
            public void decode(FacesContext context) {
                throw runtimeException;
            }
        };
        FacesContext context = new NullFacesContext() {
            public void renderResponse() {
                calls[0] = true;
            }
        };

        // ## Act ##
        // ## Assert ##
        try {
            component.processDecodes(context);
            fail();
        } catch (RuntimeException e) {
            assertSame(runtimeException, e);
        }
        assertEquals(true, calls[0]);
    }

    public void testProcessDecodes_CallDecode() throws Exception {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##

    }

    public void testProcessValidators_CallFacetsAndChildren() throws Exception {
        // ## Arrange ##
        final List callSeq = new ArrayList();
        UIComponentBase component = new MockUIComponentBase();
        {
            component.getChildren().add(new NullUIComponent() {
                public void processValidators(FacesContext context) {
                    callSeq.add("1");
                }
            });
            component.getChildren().add(new NullUIComponent() {
                public void processValidators(FacesContext context) {
                    callSeq.add("2");
                }
            });
            component.getFacets().put("A", new NullUIComponent() {
                public void processValidators(FacesContext context) {
                    callSeq.add("3");
                }
            });
            component.getFacets().put("B", new NullUIComponent() {
                public void processValidators(FacesContext context) {
                    callSeq.add("4");
                }
            });
        }

        // ## Act ##
        component.processValidators(new NullFacesContext());

        // ## Assert ##
        assertEquals(4, callSeq.size());
    }

    public void testProcessValidators_RenderFalse() throws Exception {
        // ## Arrange ##
        final List callSeq = new ArrayList();
        UIComponentBase component = new MockUIComponentBase();
        component.setRendered(false);
        {
            component.getChildren().add(new NullUIComponent() {
                public void processValidators(FacesContext context) {
                    callSeq.add("1");
                }
            });
            component.getFacets().put("B", new NullUIComponent() {
                public void processValidators(FacesContext context) {
                    callSeq.add("4");
                }
            });
        }

        // ## Act ##
        component.processValidators(new NullFacesContext());

        // ## Assert ##
        assertEquals(0, callSeq.size());
    }

    public void testProcessUpdates_CallFacetsAndChildren() throws Exception {
        // ## Arrange ##
        final List callSeq = new ArrayList();
        UIComponentBase component = new MockUIComponentBase();
        {
            component.getChildren().add(new NullUIComponent() {
                public void processUpdates(FacesContext context) {
                    callSeq.add("1");
                }
            });
            component.getChildren().add(new NullUIComponent() {
                public void processUpdates(FacesContext context) {
                    callSeq.add("2");
                }
            });
            component.getFacets().put("A", new NullUIComponent() {
                public void processUpdates(FacesContext context) {
                    callSeq.add("3");
                }
            });
            component.getFacets().put("B", new NullUIComponent() {
                public void processUpdates(FacesContext context) {
                    callSeq.add("4");
                }
            });
        }

        // ## Act ##
        component.processUpdates(new NullFacesContext());

        // ## Assert ##
        assertEquals(4, callSeq.size());
    }

    public void testProcessUpdates_RenderFalse() throws Exception {
        // ## Arrange ##
        final List callSeq = new ArrayList();
        UIComponentBase component = new MockUIComponentBase();
        component.setRendered(false);
        {
            component.getChildren().add(new NullUIComponent() {
                public void processUpdates(FacesContext context) {
                    callSeq.add("1");
                }
            });
            component.getFacets().put("B", new NullUIComponent() {
                public void processUpdates(FacesContext context) {
                    callSeq.add("4");
                }
            });
        }

        // ## Act ##
        component.processUpdates(new NullFacesContext());

        // ## Assert ##
        assertEquals(0, callSeq.size());
    }

    public void testProcessRestoreState() throws Exception {
        // ## Arrange ##
        final List callSeq = new ArrayList();
        UIComponentBase component = new MockUIComponentBase() {
            public void restoreState(FacesContext context, Object state) {
                callSeq.add("5");
            }
        };
        {
            component.getChildren().add(new NullUIComponent() {
                public void processRestoreState(FacesContext context,
                        Object state) {
                    callSeq.add("1");
                }
            });
            component.getChildren().add(new NullUIComponent() {
                public void processRestoreState(FacesContext context,
                        Object state) {
                    callSeq.add("2");
                }
            });
            component.getFacets().put("A", new NullUIComponent() {
                public void processRestoreState(FacesContext context,
                        Object state) {
                    callSeq.add("3");
                }
            });
            component.getFacets().put("B", new NullUIComponent() {
                public void processRestoreState(FacesContext context,
                        Object state) {
                    callSeq.add("4");
                }
            });
        }
        HashMap m = new HashMap();
        m.put("A", "a");
        m.put("B", "b");

        // ## Act ##
        component.processRestoreState(new NullFacesContext(),
                new SerializableStateHolder(null, m, Arrays
                        .asList(new Object[] { "", "" })));

        // ## Assert ##
        assertEquals(5, callSeq.size());
        assertEquals(
                "last time, call self restoreState. " + callSeq.toString(),
                "5", callSeq.get(4));
    }

}
