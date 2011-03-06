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
package org.seasar.teeda.core.context.servlet;

import java.util.Iterator;

import javax.faces.FactoryFinder;
import javax.faces.application.FacesMessage;

import junit.framework.TestCase;

import org.seasar.teeda.core.context.Releaseable;
import org.seasar.teeda.core.mock.MockApplicationFactory;
import org.seasar.teeda.core.mock.MockExternalContextImpl;
import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author manhole
 */
public class ServletFacesContextImplTest extends TestCase {

    private ServletFacesContextImpl context_;

    protected void setUp() throws Exception {
        super.setUp();
        FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
                MockApplicationFactory.class.getName());
        context_ = new ServletFacesContextImpl(new MockExternalContextImpl());
    }

    protected void tearDown() throws Exception {
        context_.release();
        FactoryFinder.releaseFactories();
        super.tearDown();
    }

    public void testGetClientIdsWithMessages_NoMessage() throws Exception {
        // ## Act ##
        Iterator it = context_.getClientIdsWithMessages();

        // ## Assert ##
        assertEquals(false, it.hasNext());
    }

    public void testGetClientIdsWithMessages_Released() throws Exception {
        // ## Arrange ##

        // ## Act ##
        context_.release();
        // ## Assert ##
        try {
            context_.getClientIdsWithMessages();
            fail();
        } catch (IllegalStateException ise) {
            ExceptionAssert.assertMessageExist(ise);
        }
    }

    public void testGetClientIdsWithMessages_Messages() throws Exception {
        // ## Arrange ##
        context_.addMessage(null, new FacesMessage());
        context_.addMessage("1", new FacesMessage());
        context_.addMessage("2", new FacesMessage());
        context_.addMessage("1", new FacesMessage());
        context_.addMessage(null, new FacesMessage());
        context_.addMessage(null, new FacesMessage());

        // ## Act ##
        Iterator it = context_.getClientIdsWithMessages();

        // ## Assert ##
        assertEquals(true, it.hasNext());
        assertEquals(null, it.next());
        assertEquals(true, it.hasNext());
        assertEquals("1", it.next());
        assertEquals(true, it.hasNext());
        assertEquals("2", it.next());
        assertEquals(true, it.hasNext());
        assertEquals("1", it.next());
        assertEquals(true, it.hasNext());
        assertEquals(null, it.next());
        assertEquals(true, it.hasNext());
        assertEquals(null, it.next());
        assertEquals(false, it.hasNext());
    }

    public void testGetMessages_NoMessage() throws Exception {
        // ## Act ##
        Iterator it = context_.getMessages();

        // ## Assert ##
        assertEquals(false, it.hasNext());
    }

    public void testGetMessages_Released() throws Exception {
        // ## Arrange ##

        // ## Act ##
        context_.release();
        // ## Assert ##
        try {
            context_.getMessages();
            fail();
        } catch (IllegalStateException ise) {
            ExceptionAssert.assertMessageExist(ise);
        }
    }

    public void testGetMessages_Messages() throws Exception {
        // ## Arrange ##
        FacesMessage message1 = new FacesMessage();
        FacesMessage message2 = new FacesMessage();
        FacesMessage message3 = new FacesMessage();
        FacesMessage message4 = new FacesMessage();
        FacesMessage message5 = new FacesMessage();
        FacesMessage message6 = new FacesMessage();
        context_.addMessage(null, message1);
        context_.addMessage("1", message2);
        context_.addMessage("2", message3);
        context_.addMessage("1", message4);
        context_.addMessage(null, message5);
        context_.addMessage(null, message6);

        // ## Act ##
        Iterator it = context_.getMessages();

        // ## Assert ##
        assertEquals(true, it.hasNext());
        assertEquals(message1, it.next());
        assertEquals(true, it.hasNext());
        assertEquals(message2, it.next());
        assertEquals(true, it.hasNext());
        assertEquals(message3, it.next());
        assertEquals(true, it.hasNext());
        assertEquals(message4, it.next());
        assertEquals(true, it.hasNext());
        assertEquals(message5, it.next());
        assertEquals(true, it.hasNext());
        assertEquals(message6, it.next());
        assertEquals(false, it.hasNext());
    }

    public void testGetMessagesByClientId_NoMessage() throws Exception {
        // ## Act ##
        Iterator it = context_.getMessages("a");

        // ## Assert ##
        assertEquals(false, it.hasNext());
    }

    public void testGetMessagesByClientId_Released() throws Exception {
        // ## Arrange ##

        // ## Act ##
        context_.release();
        // ## Assert ##
        try {
            context_.getMessages("a");
            fail();
        } catch (IllegalStateException ise) {
            ExceptionAssert.assertMessageExist(ise);
        }
    }

    public void testGetMessagesByClientId_Messages() throws Exception {
        // ## Arrange ##
        FacesMessage message1 = new FacesMessage();
        FacesMessage message2 = new FacesMessage();
        FacesMessage message3 = new FacesMessage();
        FacesMessage message4 = new FacesMessage();
        FacesMessage message5 = new FacesMessage();
        FacesMessage message6 = new FacesMessage();
        context_.addMessage(null, message1);
        context_.addMessage("1", message2);
        context_.addMessage("2", message3);
        context_.addMessage("1", message4);
        context_.addMessage(null, message5);
        context_.addMessage(null, message6);

        // ## Act ##
        // ## Assert ##
        {
            Iterator it = context_.getMessages("a");
            assertEquals(false, it.hasNext());
        }
        {
            Iterator it = context_.getMessages("1");
            assertEquals(true, it.hasNext());
            assertEquals(message2, it.next());
            assertEquals(true, it.hasNext());
            assertEquals(message4, it.next());
            assertEquals(false, it.hasNext());
        }
        {
            Iterator it = context_.getMessages(null);
            assertEquals(true, it.hasNext());
            assertEquals(message1, it.next());
            assertEquals(true, it.hasNext());
            assertEquals(message5, it.next());
            assertEquals(true, it.hasNext());
            assertEquals(message6, it.next());
            assertEquals(false, it.hasNext());
        }
        {
            Iterator it = context_.getMessages("2");
            assertEquals(true, it.hasNext());
            assertEquals(message3, it.next());
            assertEquals(false, it.hasNext());
        }
    }

    public void testEnsureReleased() throws Exception {
        final boolean[] calls = new boolean[] { false };
        ServletFacesContextImpl context = new ServletFacesContextImpl(
                new ReleaseableMockExternalContextImpl() {
                    public void release() {
                        calls[0] = true;
                    }
                });
        context.release();
        assertTrue(calls[0]);
    }

    public static class ReleaseableMockExternalContextImpl extends
            MockExternalContextImpl implements Releaseable {

        public void release() {
        }

    }

}
