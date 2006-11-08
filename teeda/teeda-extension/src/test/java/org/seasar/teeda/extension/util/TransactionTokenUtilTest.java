package org.seasar.teeda.extension.util;

import java.util.Map;

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
}