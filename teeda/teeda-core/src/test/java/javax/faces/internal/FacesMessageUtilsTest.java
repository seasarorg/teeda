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
package javax.faces.internal;

import java.util.Iterator;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class FacesMessageUtilsTest extends TeedaTestCase {

    public void testGetSimpleErrorMessage() {
        getApplication().setMessageBundle("javax.faces.component.TestMessages");
        MockFacesContext context = getFacesContext();
        MockUIComponent component = new MockUIComponent();
        component.setClientId("a");
        UIViewRoot root = new UIViewRoot();
        root.setLocale(Locale.ENGLISH);
        context.setViewRoot(root);
        FacesMessageUtils.addErrorMessage(context, component, "aaa");
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
        FacesMessageUtils.addErrorMessage(context, component, "bbb",
                new Object[] { "B1", "B2" });
        assertNotNull(context.getMessages("b"));
        Iterator itr = context.getMessages();
        FacesMessage message = (FacesMessage) itr.next();
        assertEquals("B1,B2", message.getSummary());
        assertEquals("B1,B2 detail", message.getDetail());
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
        FacesMessageUtils.addErrorMessage(context, component,
                "javax.faces.component.UIInput.CONVERSION");
        assertNotNull(context.getMessages("c"));
        Iterator itr = context.getMessages();
        FacesMessage message = (FacesMessage) itr.next();
        assertNotNull(message.getSummary());
    }

}
