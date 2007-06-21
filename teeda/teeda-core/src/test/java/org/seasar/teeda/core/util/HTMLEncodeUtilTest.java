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
package org.seasar.teeda.core.util;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.core.util.HTMLEncodeUtil.HtmlEncodeStrategy;

/**
 * @author yone
 * @author shot
 */
public class HTMLEncodeUtilTest extends TeedaTestCase {

    public void testEncode_amp() throws Exception {
        assertEquals("&nbsp;", HTMLEncodeUtil.encode("&nbsp;", false, false));
        assertEquals("&amp;nbsp;", HTMLEncodeUtil.encode("&nbsp;", true, true));
    }

    public void testEncode_quote() throws Exception {
        assertEquals("'hoge'", HTMLEncodeUtil.encode("'hoge'", false, false));
        assertEquals("&#39;hoge&#39;", HTMLEncodeUtil.encode("'hoge'", true,
                true));
    }

    public void testEncode_lt() throws Exception {
        assertEquals("&lt;&lt;", HTMLEncodeUtil.encode("<<", false, false));
        assertEquals("&lt;&lt;", HTMLEncodeUtil.encode("<<", true, true));
    }

    public void testEncode_gt() throws Exception {
        assertEquals("&gt;&gt;", HTMLEncodeUtil.encode(">>", false, false));
        assertEquals("&gt;&gt;", HTMLEncodeUtil.encode(">>", true, true));
    }

    public void testEncode_00A5_withDefaultStrategy() throws Exception {
        char c = '\u00a5';
        assertEquals("&yen;", HTMLEncodeUtil.encode(Character.toString(c),
                false, false));
        assertEquals("&yen;", HTMLEncodeUtil.encode(Character.toString(c),
                true, true));
        c = '\u005c\';
        assertNotSame("&yen;", HTMLEncodeUtil.encode(Character.toString(c),
                false, false));
        assertNotSame("&yen;", HTMLEncodeUtil.encode(Character.toString(c),
                true, true));
        assertEquals("\\", HTMLEncodeUtil.encode(Character.toString(c), false,
                false));
        assertEquals("\\", HTMLEncodeUtil.encode(Character.toString(c), true,
                true));
    }

    public void testEncode_yen_for_SeasarUser5976() throws Exception {
        HtmlEncodeStrategy orgStrategy = HTMLEncodeUtil.getHtmlEncodeStrategy();
        HTMLEncodeUtil
                .setHtmlEncodeStrategy(new HTMLEncodeUtil.JapaneseHtmlEncodeStrategy());
        assertEquals("&yen;&yen;", HTMLEncodeUtil.encode("\\\\", false, false));
        assertEquals("&yen;&yen;", HTMLEncodeUtil.encode("\\\\", true, true));
        HTMLEncodeUtil.setHtmlEncodeStrategy(orgStrategy);
    }

}
