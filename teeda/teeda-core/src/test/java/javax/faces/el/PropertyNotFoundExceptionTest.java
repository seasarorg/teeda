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
package javax.faces.el;

import junit.framework.TestCase;

/**
 * @author shot
 */
public class PropertyNotFoundExceptionTest extends TestCase {

    public void test1() throws Exception {
        PropertyNotFoundException e = new PropertyNotFoundException();
        assertNotNull(e);
    }

    public void test2() throws Exception {
        PropertyNotFoundException e = new PropertyNotFoundException("aaa");
        assertEquals("aaa", e.getMessage());
    }

    public void test3() throws Exception {
        PropertyNotFoundException e = new PropertyNotFoundException(
                new Throwable());
        assertNotNull(e.getCause());
    }

    public void test4() throws Exception {
        PropertyNotFoundException e = new PropertyNotFoundException("aaa",
                new Throwable());
        assertEquals("aaa", e.getMessage());
        assertNotNull(e.getCause());
    }

}
