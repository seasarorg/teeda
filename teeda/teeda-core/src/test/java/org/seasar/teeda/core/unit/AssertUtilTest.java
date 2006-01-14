package org.seasar.teeda.core.unit;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class AssertUtilTest extends TestCase {

    public void testAssertExceptionMessageExist_Success() throws Exception {
        AssertUtil.assertExceptionMessageExist(new NullPointerException("c"));
    }

    public void testAssertExceptionMessageExist_Fail() throws Exception {
        new AssertionFailedErrorExpectation(new Closure() {
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
        new AssertionFailedErrorExpectation(new Closure() {
            public void execute() {
                AssertUtil.assertNotEquals("a", "a");
            }
        });
        new AssertionFailedErrorExpectation(new Closure() {
            public void execute() {
                AssertUtil.assertNotEquals(null, null);
            }
        });
    }

    private static interface Closure {
        public void execute();
    }

    private static class AssertionFailedErrorExpectation {
        AssertionFailedErrorExpectation(Closure closure) {
            AssertionFailedError afe = null;
            try {
                closure.execute();
            } catch (AssertionFailedError expected) {
                afe = expected;
            }
            if (afe == null) {
                Assert.fail("AssertionFailedError is not thrown");
            }
        }
    }

}
