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

import junit.framework.TestCase;


public class InstantiateConverterFailureExceptionTest extends TestCase {

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
     * Constructor for InstantiateConverterFailureExceptionTest.
     * @param arg0
     */
    public InstantiateConverterFailureExceptionTest(String arg0) {
        super(arg0);
    }
    
    public void testInstantiateConverterFailureException(){
        Object[] args = {"hoge", "foo"};
        InstantiateConverterFailureException e = 
            new InstantiateConverterFailureException(args, new Exception());
        assertEquals("ETDA0005", e.getMessageCode());
        assertEquals("foo", e.getArgs()[1]);
    }

}
