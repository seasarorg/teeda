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
package org.seasar.teeda.extension.el.impl;

import javax.faces.application.FacesMessage;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.el.impl.commons.CommonsExpressionProcessorImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class OgnlValueBindingContextImplTest extends TeedaTestCase {

    public void testCreateValueBinding() throws Exception {
        MockFacesContext context = getFacesContext();
        context.addMessage("hoge", new FacesMessage());
        OgnlValueBindingContextImpl vbContext = new OgnlValueBindingContextImpl();
        vbContext.setContainer(getContainer());
        vbContext.setValueBindingName("org.seasar.teeda.core.el.impl.ValueBindingImpl");
        vbContext.setELParser(new CommonsELParser() {

            public ExpressionProcessor getExpressionProcessor() {
                return new CommonsExpressionProcessorImpl();
            }

        });
        ValueBinding vb = vbContext
                .createValueBinding(
                        getApplication(),
                        "##{@javax.faces.context.FacesContext@getCurrentInstance().getMessages().hasNext()}");
        assertEquals(Boolean.TRUE, vb.getValue(getFacesContext()));
    }
}
