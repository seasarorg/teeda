/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.util;

import javax.faces.component.UICommand;

import org.seasar.teeda.core.unit.TeedaTestCase;

public class TransactionTokenUtilTest extends TeedaTestCase {

    public void testGenerateAndVerify() throws Exception {
        String token = TransactionTokenUtil.generate(getFacesContext());
        System.out.println(token);
        assertNotNull(token);
        assertEquals(token, TransactionTokenUtil
                .getCurrentToken(getFacesContext()));
        assertNull(TransactionTokenUtil.getPreviousToken(getFacesContext()));

        assertTrue(TransactionTokenUtil.verify(getFacesContext(), token));
        assertNull(TransactionTokenUtil.getCurrentToken(getFacesContext()));
        assertEquals(token, TransactionTokenUtil
                .getPreviousToken(getFacesContext()));
        assertFalse(TransactionTokenUtil.verify(getFacesContext(), token));
    }

    public void testRenderTokenIfNeed() throws Exception {
        TransactionTokenUtil.renderTokenIfNeed(getFacesContext(),
                new UICommand());
    }
}