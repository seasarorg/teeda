package org.seasar.teeda.ajax;

import junit.framework.TestCase;

public class QuoteUtilTest extends TestCase {

    public void testQuote1() throws Exception {
        assertEquals("\"\"", QuoteUtil.quote(null));
        assertEquals("\"\"", QuoteUtil.quote(""));
    }
    
    public void testQuote2() throws Exception {
        assertEquals("\"\\\\\"", QuoteUtil.quote("\\"));
    }
    
    public void testQuote3() throws Exception {
        assertEquals("\"\\\"\"", QuoteUtil.quote("\""));
    }
    
    public void testQuote4() throws Exception {
        assertEquals("\"<\\/aaa>\"", QuoteUtil.quote("</aaa>"));
    }
    
    public void testQuote5() throws Exception {
        assertEquals("\"\\b\"", QuoteUtil.quote("\b"));
    }
    
    public void testQuote6() throws Exception {
        assertEquals("\"\\t\"", QuoteUtil.quote("\t"));
    }

    public void testQuote7() throws Exception {
        assertEquals("\"\\n\"", QuoteUtil.quote("\n"));
    }

    public void testQuote8() throws Exception {
        assertEquals("\"\\r\"", QuoteUtil.quote("\r"));
    }

    public void testQuote9() throws Exception {
        assertEquals("\" \"", QuoteUtil.quote(" "));
    }

}
