package org.seasar.teeda.extension.util;

import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class GridUtilTest extends TeedaTestCase {

    public void test1() throws Exception {
        MockExternalContext extContext = getExternalContext();
        extContext
                .getRequestHeaderMap()
                .put(
                        "user-agent",
                        "User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.1; ja; rv:1.8.0.9) Gecko/20061206 Firefox/1.5.0.9");
        int scrollBarWidthByOS = GridUtil
                .getScrollBarWidthByOS(getFacesContext());
        assertTrue(scrollBarWidthByOS == 16);
    }
}
