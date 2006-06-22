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

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.ValidatorChain;
import javax.faces.validator.Validator;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ValidatorChainTest extends TeedaTestCase {

    public void testValidate() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false, false };
        Validator v1 = new Validator() {
            public void validate(FacesContext context, UIComponent component,
                    Object value) throws FacesException {
                calls[0] = true;
            }
        };
        Validator v2 = new Validator() {
            public void validate(FacesContext context, UIComponent component,
                    Object value) throws FacesException {
                calls[1] = true;
            }
        };
        ValidatorChain chain = new ValidatorChain();
        chain.add(v1);
        chain.add(v2);
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        
        // ## Act ##
        chain.validate(getFacesContext(), component, new Integer(2));
        
        // ## Assert ##
        assertTrue(calls[0]);
        assertTrue(calls[1]);
    }
    
    
    
}
