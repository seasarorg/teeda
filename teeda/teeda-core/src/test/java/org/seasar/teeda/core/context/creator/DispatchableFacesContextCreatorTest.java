package org.seasar.teeda.core.context.creator;

import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;

import org.seasar.teeda.core.context.creator.portlet.PortletFacesContextCreator;
import org.seasar.teeda.core.context.creator.servlet.ServletFacesContextCreator;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockPortletContextImpl;
import org.seasar.teeda.core.mock.MockPortletRequestImpl;
import org.seasar.teeda.core.mock.MockPortletResponseImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class DispatchableFacesContextCreatorTest extends TeedaTestCase {

    public void testCreate_inCaseOfServletEnvironment() throws Exception {
        DispatchableFacesContextCreator creator = new DispatchableFacesContextCreator();
        creator.setServletFacesContextCreator(new ServletFacesContextCreator() {
            public FacesContext create(Object context, Object request,
                    Object response, Lifecycle lifecycle) {
                return getFacesContext();
            }

        });
        FacesContext context = creator.create(getServletContext(),
                getRequest(), getResponse(), getLifecycle());
        assertNotNull(context);
        assertTrue(context instanceof MockFacesContext);
    }

    public void testCreate_inCaseOfPortletEnvironment() throws Exception {
        DispatchableFacesContextCreator creator = new DispatchableFacesContextCreator();
        creator.setPortletFacesContextCreator(new PortletFacesContextCreator() {
            public FacesContext create(Object context, Object request,
                    Object response, Lifecycle lifecycle) {
                return getFacesContext();
            }

        });
        FacesContext context = creator.create(new MockPortletContextImpl(),
                new MockPortletRequestImpl(), new MockPortletResponseImpl(),
                getLifecycle());
        assertNotNull(context);
        assertTrue(context instanceof MockFacesContext);
    }

    public void testCreate_inCaseOfNoSuchEnvironment() throws Exception {
        
        assertTrue(true);
        
    }

}
