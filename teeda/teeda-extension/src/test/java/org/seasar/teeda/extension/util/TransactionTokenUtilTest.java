/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import java.util.Map;

import javax.faces.component.UICommand;

import org.seasar.teeda.core.unit.TeedaTestCase;

public class TransactionTokenUtilTest extends TeedaTestCase {

    public void testGenerateAndVerify() throws Exception {
        String token = TransactionTokenUtil.generate(getFacesContext());
        System.out.println(token);
        String token2 = TransactionTokenUtil.generate(getFacesContext());
        System.out.println(token2);
        assertNotNull(token);
        assertNotNull(token2);
        assertFalse(token.equals(token2));
        Map sessionMap = getExternalContext().getSessionMap();
        assertEquals(2, TransactionTokenUtil.getTokens(sessionMap).size());
        assertTrue(TransactionTokenUtil.verify(getFacesContext(), token));
        assertFalse(TransactionTokenUtil.verify(getFacesContext(), token));
        assertEquals(1, TransactionTokenUtil.getTokens(sessionMap).size());
        assertTrue(TransactionTokenUtil.verify(getFacesContext(), token2));
        assertNull(TransactionTokenUtil.getTokens(sessionMap));
    }

    public void testRenderTokenIfNeed() throws Exception {
        TransactionTokenUtil.renderTokenIfNeed(getFacesContext(),
                new UICommand());
    }
}