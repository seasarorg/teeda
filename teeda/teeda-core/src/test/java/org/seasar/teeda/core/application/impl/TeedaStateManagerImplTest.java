package org.seasar.teeda.core.application.impl;

import org.seasar.teeda.core.unit.TeedaTestCase;

public class TeedaStateManagerImplTest extends TeedaTestCase {

    //TODO test save and restore view
    public void testRestoreView() throws Exception {
    }
    
    public void testRestoreTreeStructureFromServer() throws Exception {
        TeedaStateManagerImpl manager = new TeedaStateManagerImpl();
        assertNull(manager.restoreTreeStructureFromServer(getFacesContext(), "id"));
    }
}
