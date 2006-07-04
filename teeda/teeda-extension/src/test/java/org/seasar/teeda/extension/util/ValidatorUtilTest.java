package org.seasar.teeda.extension.util;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class ValidatorUtilTest extends TeedaTestCase {

    public void testIsTargetCommand() throws Exception {
        assertTrue(ValidatorUtil.isTargetCommand(getFacesContext(), null));
        getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "aaa");
        assertTrue(ValidatorUtil.isTargetCommand(getFacesContext(), null));
        assertFalse(ValidatorUtil.isTargetCommand(getFacesContext(),
                new String[] { "bbb" }));
        assertTrue(ValidatorUtil.isTargetCommand(getFacesContext(),
                new String[] { "aaa", "bbb" }));
    }
}