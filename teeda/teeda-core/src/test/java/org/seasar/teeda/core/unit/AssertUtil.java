package org.seasar.teeda.core.unit;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

public class AssertUtil {

    public static void assertExceptionMessageExist(Throwable th) {
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

    public static void assertContains(String expected, String value) {
        Assert.assertEquals(true, value.indexOf(expected) > -1);
    }

}
