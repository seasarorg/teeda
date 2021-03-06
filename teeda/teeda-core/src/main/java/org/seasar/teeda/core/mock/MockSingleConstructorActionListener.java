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
package org.seasar.teeda.core.mock;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class MockSingleConstructorActionListener implements ActionListener {

    private ActionEvent event_ = null;

    private ActionListener originalListener_;

    public MockSingleConstructorActionListener(ActionListener listener) {
        originalListener_ = listener;
    }

    public void processAction(ActionEvent event)
            throws AbortProcessingException {
        originalListener_.processAction(event);
        event_ = event;
    }

    public ActionEvent getEvent() {
        return event_;
    }

    public ActionListener getOriginal() {
        return originalListener_;
    }
}
