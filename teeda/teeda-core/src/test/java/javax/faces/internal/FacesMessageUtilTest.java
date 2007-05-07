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
package javax.faces.internal;

import java.util.Iterator;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class FacesMessageUtilTest extends TeedaTestCase {

    public void testGetMesssages() throws Exception {
        MockFacesContext context = getFacesContext();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "a1", "a2"));
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "b1", "b2"));
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "c1", "c2"));
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                "d1", "d2"));
        FacesMessage[] ems = FacesMessageUtil.getWarnMessages();
        assertNotNull(ems);
        assertTrue(ems.length == 1);
        assertEquals("a1", ems[0].getSummary());

        ems = FacesMessageUtil.getInfoMessages();
        assertNotNull(ems);
        assertTrue(ems.length == 1);
        assertEquals("b1", ems[0].getSummary());

        ems = FacesMessageUtil.getErrorMessages();
        assertNotNull(ems);
        assertTrue(ems.length == 1);
        assertEquals("c1", ems[0].getSummary());

        ems = FacesMessageUtil.getFatalMessages();
        assertNotNull(ems);
        assertTrue(ems.length == 1);
        assertEquals("d1", ems[0].getSummary());
    }

    public void testGetAllMesssages() throws Exception {
        MockFacesContext context = getFacesContext();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "a1", "a2"));
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "b1", "b2"));
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "c1", "c2"));
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                "d1", "d2"));
        FacesMessage[] ems = FacesMessageUtil.getAllMessages();
        assertTrue(ems.length == 4);
    }

    public void testGetSimpleErrorMessage() {
        getApplication().setMessageBundle("javax.faces.component.TestMessages");
        MockFacesContext context = getFacesContext();
        MockUIComponent component = new MockUIComponent();
        component.setClientId("a");
        UIViewRoot root = new UIViewRoot();
        root.setLocale(Locale.ENGLISH);
        context.setViewRoot(root);
        FacesMessageUtil.addErrorComponentMessage(context, component, "aaa");
        assertNotNull(context.getMessages("a"));
        Iterator itr = context.getMessages();
        FacesMessage message = (FacesMessage) itr.next();
        assertEquals("AAA", message.getSummary());
    }

    public void testGetParameterizedMessage() {
        getApplication().setMessageBundle("javax.faces.component.TestMessages");
        MockFacesContext context = getFacesContext();
        MockUIComponent component = new MockUIComponent();
        component.setClientId("b");
        UIViewRoot root = new UIViewRoot();
        root.setLocale(Locale.ENGLISH);
        context.setViewRoot(root);
        FacesMessageUtil.addErrorComponentMessage(context, component, "bbb",
                new Object[] { "B1", "B2" });
        assertNotNull(context.getMessages("b"));
        Iterator itr = context.getMessages();
        FacesMessage message = (FacesMessage) itr.next();
        assertEquals("B1,B2", message.getSummary());
        assertEquals("B1,B2 detail", message.getDetail());
    }

    public void testGetParameterizedMessage_escapeNeed() {
        getApplication().setMessageBundle("javax.faces.component.TestMessages");
        MockFacesContext context = getFacesContext();
        MockUIComponent component = new MockUIComponent();
        component.setClientId("b");
        UIViewRoot root = new UIViewRoot();
        root.setLocale(Locale.ENGLISH);
        context.setViewRoot(root);
        FacesMessageUtil.addErrorComponentMessage(context, component, "bbb",
                new Object[] { "<script>&", "</script>" });
        assertNotNull(context.getMessages("b"));
        Iterator itr = context.getMessages();
        FacesMessage message = (FacesMessage) itr.next();
        assertEquals("&lt;script&gt;&amp;,&lt;/script&gt;", message
                .getSummary());
        assertEquals("&lt;script&gt;&amp;,&lt;/script&gt; detail", message
                .getDetail());
    }

    //TODO more efficient way need.
    public void testApplicationResourceBundleMissing() {
        getApplication().setMessageBundle(
                "javax.faces.component.NoFoundMessages");
        MockFacesContext context = getFacesContext();
        MockUIComponent component = new MockUIComponent();
        component.setClientId("c");
        UIViewRoot root = new UIViewRoot();
        root.setLocale(Locale.US);
        context.setViewRoot(root);
        FacesMessageUtil.addErrorComponentMessage(context, component,
                "javax.faces.component.UIInput.CONVERSION");
        assertNotNull(context.getMessages("c"));
        Iterator itr = context.getMessages();
        FacesMessage message = (FacesMessage) itr.next();
        assertNotNull(message.getSummary());
    }

    public void testApplicationResourceAndDefaultResource() {
        getApplication().setMessageBundle("javax.faces.component.TestMessages");
        MockFacesContext context = getFacesContext();
        MockUIComponent component = new MockUIComponent();
        component.setClientId("c");
        UIViewRoot root = new UIViewRoot();
        root.setLocale(Locale.US);
        context.setViewRoot(root);
        FacesMessageUtil.addErrorComponentMessage(context, component,
                "javax.faces.component.UIInput.CONVERSION");
        assertNotNull(context.getMessages("c"));
        Iterator itr = context.getMessages();
        FacesMessage message = (FacesMessage) itr.next();
        assertNotNull(message.getSummary());
    }

    public void testGetTargetFacesMessages_simplyGet() throws Exception {
        MockFacesContext context = getFacesContext();
        FacesMessage facesMessage = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "aaa", null);
        context.addMessage(null, facesMessage);
        FacesMessage[] targetFacesMessages = FacesMessageUtil
                .getTargetFacesMessages(
                        context,
                        new FacesMessage.Severity[] { FacesMessage.SEVERITY_ERROR });
        assertNotNull(targetFacesMessages);
        assertTrue(targetFacesMessages.length == 1);
        assertEquals("aaa", targetFacesMessages[0].getSummary());
    }

    public void testGetTargetFacesMessages_noMessages() throws Exception {
        MockFacesContext context = getFacesContext();
        FacesMessage[] targetFacesMessages = FacesMessageUtil
                .getTargetFacesMessages(
                        context,
                        new FacesMessage.Severity[] { FacesMessage.SEVERITY_ERROR });
        assertNotNull(targetFacesMessages);
        assertTrue(targetFacesMessages.length == 0);
    }

    public void testGetTargetFacesMessages_messagesExistButNotMatch()
            throws Exception {
        MockFacesContext context = getFacesContext();
        FacesMessage facesMessage = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "aaa", null);
        context.addMessage(null, facesMessage);
        FacesMessage[] targetFacesMessages = FacesMessageUtil
                .getTargetFacesMessages(
                        context,
                        new FacesMessage.Severity[] { FacesMessage.SEVERITY_INFO });
        assertNotNull(targetFacesMessages);
        assertTrue(targetFacesMessages.length == 0);
    }

    public void testGetTargetFacesMessages_getMessages() throws Exception {
        MockFacesContext context = getFacesContext();
        FacesMessage facesMessage = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "aaa", null);
        context.addMessage(null, facesMessage);
        facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "bbb",
                null);
        context.addMessage(null, facesMessage);
        FacesMessage[] targetFacesMessages = FacesMessageUtil
                .getTargetFacesMessages(context, new FacesMessage.Severity[] {
                        FacesMessage.SEVERITY_ERROR,
                        FacesMessage.SEVERITY_FATAL });
        assertNotNull(targetFacesMessages);
        assertTrue(targetFacesMessages.length == 2);
        assertEquals("aaa", targetFacesMessages[0].getSummary());
        assertEquals("bbb", targetFacesMessages[1].getSummary());
    }

    public void testHasMessages_noMessage() throws Exception {
        MockFacesContext context = getFacesContext();
        //        FacesMessage facesMessage = new FacesMessage(
        //                FacesMessage.SEVERITY_ERROR, "aaa", null);
        //        context.addMessage(null, facesMessage);
        assertFalse(FacesMessageUtil.hasMessages(context));
    }

    public void testHasMessages_hasMessage() throws Exception {
        MockFacesContext context = getFacesContext();
        FacesMessage facesMessage = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "aaa", null);
        context.addMessage(null, facesMessage);
        assertTrue(FacesMessageUtil.hasMessages(context));
    }

    public void testHasMessagesByClientId_hasMessage() throws Exception {
        MockFacesContext context = getFacesContext();
        FacesMessage facesMessage = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "aaa", null);
        context.addMessage("hoge", facesMessage);
        MockUIComponent c = new MockUIComponent() {

            public String getClientId(FacesContext context) {
                return "hoge";
            }

        };
        assertTrue(FacesMessageUtil.hasMessagesByClientId(context, c));
    }

    public void testHasErrorOrFatalMesssages1() throws Exception {
        MockFacesContext context = getFacesContext();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "c1", "c2"));
        assertTrue(FacesMessageUtil.hasErrorOrFatalMessage(context));
    }

    public void testHasErrorOrFatalMesssages2() throws Exception {
        MockFacesContext context = getFacesContext();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                "c1", "c2"));
        assertTrue(FacesMessageUtil.hasErrorOrFatalMessage(context));
    }

    public void testHasErrorOrFatalMesssages3() throws Exception {
        MockFacesContext context = getFacesContext();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "c1", "c2"));
        assertFalse(FacesMessageUtil.hasErrorOrFatalMessage(context));
    }

}
