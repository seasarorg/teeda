package org.seasar.teeda.core.util;

import javax.faces.application.Application;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class ApplicationUtilTest extends TeedaTestCase {

    public void testApplicationFromFactory() {
        Application application = ApplicationUtil.getApplicationFromFactory();
        assertNotNull(application);
        assertTrue(application instanceof MockApplication);
    }

    public void testApplicationFromContext() {
        Application application = ApplicationUtil.getApplicationFromContext();
        assertNotNull(application);
        assertTrue(application instanceof MockApplication);
    }

}
