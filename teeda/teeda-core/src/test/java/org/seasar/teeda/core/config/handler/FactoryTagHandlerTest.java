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
package org.seasar.teeda.core.config.handler;

import java.util.List;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.FactoryElement;
import org.seasar.teeda.core.config.element.impl.FacesConfigImpl;


public class FactoryTagHandlerTest extends TagHandlerTestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(FactoryTagHandlerTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public FactoryTagHandlerTest(String arg0) {
        super(arg0);
    }

    public void testFactoryTagHandler() throws Exception{
        FacesConfig config = new FacesConfigImpl();
        getContext().push(config);
        FactoryTagHandler handler = new FactoryTagHandler();
        FactoryElement element  = new FactoryElement(){

            public void addApplicationFactory(String applicationFactory) {
            }

            public void addFacesContextFactory(String facesContextFactory) {
            }

            public void addLifecycleFactory(String lifecycleFactory) {
            }

            public void addRenderKitFactory(String renderKitFactory) {
            }

            public List getApplicationFactories() {
                return null;
            }

            public List getFacesContextFactories() {
                return null;
            }

            public List getLifecycleFactories() {
                return null;
            }

            public List getRenderKitFactories() {
                return null;
            }

        };
        getContext().push(element);
        handler.end(getContext(), null);
        FacesConfig c = (FacesConfig)getContext().pop();
        List list = (List)c.getFactoryElements();
        assertTrue(list != null);
        assertEquals(1, list.size());
        assertTrue(list.get(0) instanceof FactoryElement);
    }
     
    public void testFactoryTagHandlerByXMLParse() throws Exception{
        FacesConfig facesConfig = parse("testFactoryTagHandler.xml");
        List list = facesConfig.getFactoryElements();
        assertEquals(1, list.size());
        
        FactoryElement factory = (FactoryElement)list.get(0);
        assertEquals(1, factory.getApplicationFactories().size());
        assertEquals("a", factory.getApplicationFactories().get(0));
        
        assertEquals(1, factory.getFacesContextFactories().size());
        assertEquals("b", factory.getFacesContextFactories().get(0));
        
        assertEquals(1, factory.getLifecycleFactories().size());
        assertEquals("c", factory.getLifecycleFactories().get(0));
        
        assertEquals(1, factory.getRenderKitFactories().size());
        assertEquals("d", factory.getRenderKitFactories().get(0));
    }

}

