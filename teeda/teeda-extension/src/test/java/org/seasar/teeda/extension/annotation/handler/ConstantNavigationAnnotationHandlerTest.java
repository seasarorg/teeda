package org.seasar.teeda.extension.annotation.handler;

import java.util.List;
import java.util.Map;

import org.seasar.teeda.core.application.navigation.NavigationCaseContext;
import org.seasar.teeda.core.application.navigation.NavigationContext;
import org.seasar.teeda.core.application.navigation.NavigationResource;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class ConstantNavigationAnnotationHandlerTest extends TeedaTestCase {

    public void testCreateNavigationCaseContext() throws Exception {
        register(HogePage.class, "hogePage");
        register(Hoge2Page.class, "hoge2Page");
        register(Hoge3Page.class, "hoge3Page");
        String viewId = "/view/hoge.html";
        ConstantNavigationAnnotationHandler handler = new ConstantNavigationAnnotationHandler();
        handler.registerNavigationsByPage("hogePage", HogePage.class);
        Map m = NavigationResource.getNavigationContexts();
        assertNotNull(m);
        List l = (List) m.get(viewId);
        assertNotNull(l);
        assertEquals(1, l.size());
        NavigationContext nc = (NavigationContext) l.get(0);
        assertEquals(viewId, nc.getFromViewId());
        List nccs = nc.getNavigationCases();
        assertEquals(2, nccs.size());
        NavigationCaseContext ncc = (NavigationCaseContext) nccs.get(0);
        assertEquals("hoge2Page", ncc.getFromOutcome());
        assertEquals("/view/hoge2.html", ncc.getToViewId());
        assertTrue(ncc.isRedirect());

        ncc = (NavigationCaseContext) nccs.get(1);
        assertEquals("hoge3Page", ncc.getFromOutcome());
        assertEquals("/view/hoge3.html", ncc.getToViewId());
        assertFalse(ncc.isRedirect());
    }

    public static class HogePage {
        public static final String HOGE2PAGE = "hoge2Page";

        public static final String HOGE3PAGE_NAVIGATION = "#{'redirect':false}";

        public static final String HOGE3PAGE = "hoge3Page";

        public static final String HOGE4PAGE = "hoge4Page";
    }

    public static class Hoge2Page {
    }

    public static class Hoge3Page {
    }
}