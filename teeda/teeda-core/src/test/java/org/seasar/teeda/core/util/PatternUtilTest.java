package org.seasar.teeda.core.util;

import java.util.regex.Pattern;

import junit.framework.TestCase;

public class PatternUtilTest extends TestCase {

    public void testMatches_normal() throws Exception {
        assertTrue(PatternUtil.matches("^[1-9][a-z]", "1a"));
    }

    public void testMatches_regexMustNotNull() throws Exception {
        try {
            PatternUtil.matches(null, "1a");
        } catch (IllegalArgumentException expected) {
            assertTrue(true);
        }
    }

    public void testGetPattern_ensureCacheWork() throws Exception {
        Pattern p = Pattern.compile("^[1-9][a-z]");
        Pattern p1 = PatternUtil.getPattern("^[1-9][a-z]");
        Pattern p2 = PatternUtil.getPattern("^[1-9][a-z]");
        assertNotSame(p, p1);
        assertEquals(p1, p2);
    }
    
    public void testClearPatternCache() throws Exception {
        Pattern p1 = PatternUtil.getPattern("^[1-9][a-z]");
        Pattern p2 = PatternUtil.getPattern("^[1-9][a-z]");
        assertEquals(p1, p2);
        PatternUtil.clearPatternCache();
        Pattern p3 = PatternUtil.getPattern("^[1-9][a-z]");
        assertNotSame(p1, p3);
        assertNotSame(p2, p3);
    }
}
