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
package org.seasar.teeda.core.unit;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

/**
 * @author manhole
 */
public class ExceptionAssert {

    public static void assertMessageExist(Throwable th) {
        String message = th.getMessage();
        try {
            Assert.assertNotNull("Throwable should have message", message);
            Assert.assertTrue("Throwable should have message", message.trim()
                    .length() > 0);
        } catch (AssertionFailedError afe) {
            th.printStackTrace();
            throw afe;
        }
    }

    public static void assertThrows(Class expected, ExceptionalClosure closure) {
        Assert.assertNotNull("expected", expected);
        Assert.assertNotNull("closure", closure);
        boolean thrown = false;
        try {
            closure.execute();
        } catch (Throwable th) {
            thrown = true;
            if (expected.isAssignableFrom(th.getClass())) {
                // ok
                assertMessageExist(th);
            } else {
                Assert.fail("Expected <" + expected.getName()
                        + "> is not thrown. But was <" + th + ">");
            }
        }
        if (!thrown) {
            Assert.fail("Expected <" + expected.getName() + "> is not thrown.");
        }
    }

    static void assertContains(String shouldBeContained, String value) {
        Assert.assertEquals(true, value.indexOf(shouldBeContained) > -1);
    }

    static void assertNotEquals(Object expected, Object actual) {
        AssertionFailedError afe = null;
        try {
            Assert.assertEquals(expected, actual);
        } catch (AssertionFailedError e) {
            afe = e;
        }
        if (afe == null) {
            Assert.fail("should not be equals. expected:<" + expected
                    + "> actual:<" + actual + ">");
        }
    }

}
