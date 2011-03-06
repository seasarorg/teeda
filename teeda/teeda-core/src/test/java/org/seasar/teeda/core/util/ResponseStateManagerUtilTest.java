/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.util;

import javax.faces.render.RenderKitFactory;
import javax.faces.render.ResponseStateManager;

import org.seasar.teeda.core.mock.NullResponseStateManager;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ResponseStateManagerUtilTest extends TeedaTestCase {

    public void testGetResponseStateManager() throws Exception {
        getRenderKit().setResponseStateManager(new NullResponseStateManager());
        ResponseStateManager responseStateManager = ResponseStateManagerUtil
                .getResponseStateManager(getFacesContext(),
                        RenderKitFactory.HTML_BASIC_RENDER_KIT);
        assertNotNull(responseStateManager);
        assertTrue(responseStateManager instanceof NullResponseStateManager);
    }

    public void testGetResponseStateManager2() throws Exception {
        getRenderKit().setResponseStateManager(new NullResponseStateManager());
        assertNotNull(getFacesContext().getViewRoot());
        assertEquals(RenderKitFactory.HTML_BASIC_RENDER_KIT, getFacesContext()
                .getViewRoot().getRenderKitId());
        ResponseStateManager responseStateManager = ResponseStateManagerUtil
                .getResponseStateManager(getFacesContext());
        assertNotNull(responseStateManager);
        assertTrue(responseStateManager instanceof NullResponseStateManager);
    }

}
