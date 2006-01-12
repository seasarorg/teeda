package org.seasar.teeda.core.unit;

import junit.framework.Assert;

public class AssertUtil {

    public static void assertExceptionMessageExist(Throwable th) {
        String message = th.getMessage();
        Assert.assertNotNull(message);
        Assert.assertTrue(message.trim().length() > 0);
    }

}
