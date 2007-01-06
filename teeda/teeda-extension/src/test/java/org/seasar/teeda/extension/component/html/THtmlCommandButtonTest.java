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
package org.seasar.teeda.extension.component.html;

import java.util.Map;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.util.TransactionTokenUtil;

/**
 * @author higa
 */
public class THtmlCommandButtonTest extends TeedaTestCase {

    private MyActionListener actionListener = new MyActionListener();

    public void setUp() {
        getApplication().setActionListener(actionListener);
    }

    public void testBroadcase_ok() throws Exception {
        THtmlCommandButton button = new THtmlCommandButton();
        TransactionTokenUtil.renderTokenIfNeed(getFacesContext(), button);
        Map reqMap = getExternalContext().getRequestMap();
        String token = TransactionTokenUtil.getToken(reqMap);
        Map paramMap = getExternalContext().getRequestParameterMap();
        paramMap.put(TransactionTokenUtil.TOKEN, token);

        button.setId("doOnceSubmit");
        FacesEvent event = new ActionEvent(button);
        button.broadcast(event);
        assertTrue(actionListener.called);
        assertFalse(getFacesContext().getRenderResponse());
        assertFalse(getFacesContext().getMessages().hasNext());
    }

    public void testVerify_ng() throws Exception {
        THtmlCommandButton button = new THtmlCommandButton();
        button.setId("doOnceSubmit");
        FacesEvent event = new ActionEvent(button);
        button.broadcast(event);
        assertFalse(actionListener.called);
        assertTrue(getFacesContext().getRenderResponse());
    }

    private static class MyActionListener implements ActionListener {

        private boolean called = false;

        public void processAction(ActionEvent actionEvent)
                throws AbortProcessingException {
            called = true;
        }

    }
}
