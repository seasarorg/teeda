/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.faces.assembler.impl;

import javax.faces.event.ActionListener;

import org.seasar.teeda.core.mock.MockActionListener;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockSingleConstructorActionListener;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class ActionListenerAssemblerTest extends TeedaTestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ActionListenerAssemblerTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for TestActionListenerAssembler.
     * @param arg0
     */
    public ActionListenerAssemblerTest(String arg0) {
        super(arg0);
    }

    public void testSimpleAssembleActionListener() {
        MockApplication application = getApplication();
        String listenerName = "org.seasar.teeda.core.mock.MockActionListener";
        ActionListenerAssembler assembler = new ActionListenerAssembler(
                listenerName, application);
        assembler.assemble();
        assertNotNull(application.getActionListener());
        ActionListener listener = application.getActionListener();
        assertTrue(listener instanceof MockActionListener);
    }

    public void testMarshalAssembleActionListener1() {
        MockApplication application = getApplication();
        String listenerName = "org.seasar.teeda.core.mock.MockSingleConstructorActionListener";
        ActionListenerAssembler assembler = new ActionListenerAssembler(
                listenerName, application);
        assembler.assemble();
        assertNotNull(application.getActionListener());
        ActionListener listener = application.getActionListener();
        assertTrue(listener instanceof MockSingleConstructorActionListener);
    }

    public void testMarshalAssembleActionListener2() {
        MockApplication application = getApplication();
        MockActionListener original = new MockActionListener();
        application.setActionListener(original);
        String listenerName = "org.seasar.teeda.core.mock.MockSingleConstructorActionListener";
        ActionListenerAssembler assembler = new ActionListenerAssembler(
                listenerName, application);
        assembler.assemble();
        assertNotNull(application.getActionListener());
        ActionListener listener = application.getActionListener();
        assertTrue(listener instanceof MockSingleConstructorActionListener);
        MockSingleConstructorActionListener l = (MockSingleConstructorActionListener) listener;
        assertTrue(l.getOriginal() instanceof MockActionListener);
    }

    public void testMarshalAssembleActionListener3() {
        MockApplication application = getApplication();
        MockSingleConstructorActionListener original = new MockSingleConstructorActionListener(
                new MockActionListener());
        application.setActionListener(original);
        String listenerName = "org.seasar.teeda.core.mock.MockActionListener";
        ActionListenerAssembler assembler = new ActionListenerAssembler(
                listenerName, application);
        assembler.assemble();
        assertNotNull(application.getActionListener());
        ActionListener listener = application.getActionListener();
        assertTrue(listener instanceof MockActionListener);
    }

}
