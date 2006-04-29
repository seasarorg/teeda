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
package org.seasar.teeda.core.component;

import javax.faces.application.FacesMessage;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author manhole
 */
public class MessageBeanTest extends TeedaTestCase {

    public void testIsEmpty() throws Exception {
        MessageBean messageBean = new MessageBean();
        assertEquals(true, messageBean.isEmpty());
        assertEquals(false, messageBean.isNotEmpty());
    }

    public void testIsNotEmpty() throws Exception {
        getFacesContext().addMessage("fooId", new FacesMessage());
        MessageBean messageBean = new MessageBean();
        assertEquals(false, messageBean.isEmpty());
        assertEquals(true, messageBean.isNotEmpty());
    }

    public void testGetMessagesBySeverity() throws Exception {
        getFacesContext().addMessage("fooId", new FacesMessage(FacesMessage.SEVERITY_WARN, "a", "b"));
        getFacesContext().addMessage("barId", new FacesMessage(FacesMessage.SEVERITY_WARN, "c", "d"));
        MessageBean messageBean = new MessageBean();
        FacesMessage[] messages = messageBean.getMessagesBySeverity(FacesMessage.SEVERITY_WARN);
        assertEquals(FacesMessage.SEVERITY_WARN, messages[0].getSeverity());
        assertEquals(FacesMessage.SEVERITY_WARN, messages[1].getSeverity());
    }
}
