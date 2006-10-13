package org.seasar.teeda.extension.util;

import junit.framework.TestCase;

public class PagePersistenceUtilTest extends TestCase {

    public void testCalcActionMethodName() throws Exception {
        assertEquals("doExecute", PagePersistenceUtil.calcActionMethodName(
                "#{hogePage.doExecute}", "aaa"));
        assertEquals("goEmpEdit", PagePersistenceUtil.calcActionMethodName(
                null, "empEdit"));
    }
}