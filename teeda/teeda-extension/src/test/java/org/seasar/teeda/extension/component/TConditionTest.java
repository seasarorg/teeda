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
package org.seasar.teeda.extension.component;

import javax.faces.application.FacesMessage;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class TConditionTest extends TeedaTestCase {

    public void testIsRendered_submittedValueIsNullWhenInitValidationOccured()
            throws Exception {
        MockFacesContext context = getFacesContext();
        context.addMessage(null, new FacesMessage());
        TCondition c = new TCondition();
        c.setRendered(true);
        assertTrue(c.isRendered());
    }

    public void testIsRendered_submittedValueIsNullWhenInitValidationOccured2()
            throws Exception {
        MockFacesContext context = getFacesContext();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "a", "b"));
        TCondition c = new TCondition();
        c.setRendered(false);
        c.setSubmitted(true);
        assertTrue(c.isRendered());
    }

    public void testIsRendered_submittedValueIsNullWhenInitValidationOccured3()
            throws Exception {
        MockFacesContext context = getFacesContext();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                "a", "b"));
        TCondition c = new TCondition();
        c.setRendered(false);
        c.setSubmitted(true);
        assertTrue(c.isRendered());
    }

    public void testIsRendered_submittedValueIsNullWhenInitValidationOccured4()
            throws Exception {
        MockFacesContext context = getFacesContext();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "a", "b"));
        TCondition c = new TCondition();
        c.setRendered(true);
        c.setSubmitted(false);
        assertTrue(c.isRendered());
    }

}
