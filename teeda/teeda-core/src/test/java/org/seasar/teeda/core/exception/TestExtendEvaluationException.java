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
package org.seasar.teeda.core.exception;

import javax.faces.el.EvaluationException;
import org.seasar.teeda.core.mock.MockMethodBinding;
import junit.framework.TestCase;


public class TestExtendEvaluationException extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestExtendEvaluationException.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for TestExtendedMethodNotFoundException.
     * @param arg0
     */
    public TestExtendEvaluationException(String arg0) {
        super(arg0);
    }
    
    public void testExtendedMethodNotFoundException(){
        MockMethodBinding mb = new MockMethodBinding("#{aaa}");
        ExtendEvaluationException e = 
            new ExtendEvaluationException(new EvaluationException(), mb);
        assertNotNull(e.getArgs());
        assertNotNull(e.getMessage());
        assertNotNull(e.getMessageCode());
        assertEquals(mb, e.getMethodBinding());
        assertEquals("ETDA0003", e.getMessageCode());
    }

}
