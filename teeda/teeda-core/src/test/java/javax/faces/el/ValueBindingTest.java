/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package javax.faces.el;

import javax.faces.context.FacesContext;

import junit.framework.TestCase;

/**
 * @author shot
 *
 */
public class ValueBindingTest extends TestCase {

    public void test1() throws Exception {
        ValueBinding vb = new ValueBinding() {

            public Class getType(FacesContext context)
                    throws EvaluationException, PropertyNotFoundException {
                return null;
            }

            public Object getValue(FacesContext context)
                    throws EvaluationException, PropertyNotFoundException {
                return null;
            }

            public boolean isReadOnly(FacesContext context)
                    throws EvaluationException, PropertyNotFoundException {
                return false;
            }

            public void setValue(FacesContext context, Object value)
                    throws EvaluationException, PropertyNotFoundException {
            }

        };
        assertNull(vb.getExpressionString());
    }
}
