/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * @author manhole
 */
public class AssertUtilTest extends TestCase {

    public void testAssertThrows_FailNoThrown() throws Exception {
        try {
            ExceptionAssert.assertThrows(NullPointerException.class,
                    new ExceptionalClosure() {
                        public void execute() throws Throwable {
                        }
                    });
            fail();
        } catch (AssertionFailedError afe) {
        }
    }

    public void testAssertThrows_FailAnotherThrown() throws Exception {
        try {
            ExceptionAssert.assertThrows(NullPointerException.class,
                    new ExceptionalClosure() {
                        public void execute() throws Throwable {
                            throw new IllegalStateException("for test");
                        }
                    });
            fail();
        } catch (AssertionFailedError afe) {
        }
    }

    public void testAssertExceptionMessageExist_Success() throws Exception {
        ExceptionAssert.assertMessageExist(new NullPointerException("c"));
    }

    public void testAssertExceptionMessageExist_Fail() throws Exception {
        ExceptionAssert.assertThrows(AssertionFailedError.class,
                new ExceptionalClosure() {
                    public void execute() {
                        ExceptionAssert
                                .assertMessageExist(new NullPointerException());
                    }
                });
    }

    public void testAssertNotEquals_Success() throws Exception {
        ExceptionAssert.assertNotEquals("a", "b");
        ExceptionAssert.assertNotEquals(null, "b");
        ExceptionAssert.assertNotEquals("a", null);
    }

    public void testAssertNotEquals_Fail() throws Exception {
        ExceptionAssert.assertThrows(AssertionFailedError.class,
                new ExceptionalClosure() {
                    public void execute() {
                        ExceptionAssert.assertNotEquals("a", "a");
                    }
                });
        ExceptionAssert.assertThrows(AssertionFailedError.class,
                new ExceptionalClosure() {
                    public void execute() {
                        ExceptionAssert.assertNotEquals(null, null);
                    }
                });
    }

}
