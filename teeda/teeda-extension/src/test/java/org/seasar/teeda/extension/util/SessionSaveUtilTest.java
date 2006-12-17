package org.seasar.teeda.extension.util;

import junit.framework.TestCase;

public class SessionSaveUtilTest extends TestCase {

    public void testConvertTargetId() throws Exception {
        assertEquals("aaaItems", SessionSaveUtil
                .convertTargetId("aaaItemsSessionSave"));
    }
}