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
package org.seasar.teeda.core.unit;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * @author manhole
 */
public class AssertUtilTest extends TestCase {

    public void testAssertThrows_FailNoThrown() throws Exception {
        try {
            AssertUtil.assertThrows(NullPointerException.class,
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
            AssertUtil.assertThrows(NullPointerException.class,
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
        AssertUtil.assertExceptionMessageExist(new NullPointerException("c"));
    }

    public void testAssertExceptionMessageExist_Fail() throws Exception {
        AssertUtil.assertThrows(AssertionFailedError.class,
                new ExceptionalClosure() {
                    public void execute() {
                        AssertUtil
                                .assertExceptionMessageExist(new NullPointerException());
                    }
                });
    }

    public void testAssertNotEquals_Success() throws Exception {
        AssertUtil.assertNotEquals("a", "b");
        AssertUtil.assertNotEquals(null, "b");
        AssertUtil.assertNotEquals("a", null);
    }

    public void testAssertNotEquals_Fail() throws Exception {
        AssertUtil.assertThrows(AssertionFailedError.class,
                new ExceptionalClosure() {
                    public void execute() {
                        AssertUtil.assertNotEquals("a", "a");
                    }
                });
        AssertUtil.assertThrows(AssertionFailedError.class,
                new ExceptionalClosure() {
                    public void execute() {
                        AssertUtil.assertNotEquals(null, null);
                    }
                });
    }

}
