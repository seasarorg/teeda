/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.context.html;

import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;

import javax.faces.context.ResponseWriter;

import junit.framework.TestCase;

import org.seasar.framework.util.SPrintWriter;
import org.seasar.teeda.core.mock.NullWriter;
import org.seasar.teeda.core.unit.AssertUtil;

/**
 * @author manhole
 */
public class HtmlResponseWriterTest extends TestCase {

    public void testStartDocument() throws Exception {
        // nothing to do
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        responseWriter.startDocument();
    }

    public void testEndDocument() throws Exception {
        // nothing to do
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        responseWriter.endDocument();
    }

    public void testCloneWithWriter() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        responseWriter.setContentType("foo content type");
        responseWriter.setCharacterEncoding("bar encoding");

        Writer writer = new NullWriter();
        ResponseWriter newWriter = responseWriter.cloneWithWriter(writer);

        assertEquals(true, newWriter instanceof HtmlResponseWriter);
        HtmlResponseWriter newResponseWriter = (HtmlResponseWriter) newWriter;
        assertSame(writer, newResponseWriter.getWriter());
        assertEquals("foo content type", newResponseWriter.getContentType());
        assertEquals("bar encoding", newResponseWriter.getCharacterEncoding());
    }

    public void testClose() throws Exception {
        // ## Arrange ##
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        final boolean[] calls = { false };
        Writer writer = new NullWriter() {
            public void close() throws IOException {
                calls[0] = true;
            }
        };
        responseWriter.setWriter(writer);

        // ## Act ##
        responseWriter.close();

        // ## Assert ##
        assertTrue(calls[0]);
    }

    public void testFlush() throws Exception {
        // ## Arrange ##
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        final boolean[] calls = { false };
        Writer writer = new NullWriter() {
            public void flush() throws IOException {
                calls[0] = true;
            }
        };
        responseWriter.setWriter(writer);

        // ## Act ##
        responseWriter.flush();

        // ## Assert ##
        assertTrue(calls[0]);
    }

    public void testCloneWithWriterWithNull() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        try {
            responseWriter.cloneWithWriter(null);
            fail();
        } catch (NullPointerException npe) {
            String message = npe.getMessage();
            assertTrue((message != null && 0 < message.length()));
        }
    }

    public void testStartElement() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.startElement("a", null);

        String value = writer.toString();
        assertEquals("<a", value);
    }

    public final void testStartElement_NameIsNull() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        try {
            responseWriter.startElement(null, null);
            fail();
        } catch (NullPointerException npe) {
            String message = npe.getMessage();
            assertNotNull(message);
            assertTrue(message.trim().length() > 0);
        }
    }

    public void testEndElement() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.startElement("span", null);
        responseWriter.endElement("span");

        String value = writer.toString();
        assertEquals("<span/>", value);
    }

    public final void testEndElement_NameIsNull() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        try {
            responseWriter.endElement(null);
            fail();
        } catch (NullPointerException npe) {
            String message = npe.getMessage();
            assertNotNull(message);
            assertTrue(message.trim().length() > 0);
        }
    }

    public void testWrite() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.write("abcdefg".toCharArray(), 3, 2);
        String value = writer.toString();
        assertEquals("de", value);
    }

    public void testWrite_NoEncoding() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.write("1234<a>".toCharArray(), 3, 4);
        String value = writer.toString();
        assertEquals("4<a>", value);
    }

    public void testWriteComment() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.writeComment("abc");
        String value = writer.toString();
        assertEquals("<!--abc-->", value);
    }

    public void testWriteTextA() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.writeText("abc", null);
        String value = writer.toString();
        assertEquals("abc", value);
    }

    public void testWriteTextA_WithNull() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        try {
            responseWriter.writeText(null, null);
            fail();
        } catch (NullPointerException npe) {
            String message = npe.getMessage();
            assertTrue((message != null && 0 < message.length()));
        }
    }

    public void testWriteTextA_Encoding() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.writeText("<a>", null);
        String value = writer.toString();
        assertEquals("&lt;" + "a" + "&gt;", value);
    }

    public void testWriteTextB() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.writeText("abcdefg".toCharArray(), 3, 2);
        String value = writer.toString();
        assertEquals("de", value);
    }

    public void testWriteTextB_WithNull() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        try {
            responseWriter.writeText((char[]) null, 0, 0);
            fail();
        } catch (NullPointerException npe) {
            String message = npe.getMessage();
            assertTrue((message != null && 0 < message.length()));
        }
    }

    public void testWriteTextB_Encoding() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.writeText("ab\"efgh".toCharArray(), 2, 4);
        String value = writer.toString();
        assertEquals("&quot;" + "efg", value);
    }

    public void testWriteCommentWithNull() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);
        try {
            responseWriter.writeComment(null);
            fail();
        } catch (NullPointerException npe) {
            String message = npe.getMessage();
            assertTrue((message != null && 0 < message.length()));
        }
    }

    public void testStartElementAndEndDocument() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.startElement("span", null);
        responseWriter.endDocument();

        String value = writer.toString();
        assertEquals("<span>", value);
    }

    public void testStartElementAndStartElement() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.startElement("span", null);
        responseWriter.startElement("a", null);

        String value = writer.toString();
        assertEquals("<span><a", value);
    }

    public void testStartElementAndWriteComment() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.startElement("span", null);
        responseWriter.writeComment("foo");

        String value = writer.toString();
        assertEquals("<span><!--foo-->", value);
    }

    public void testStartElementAndWriteTextA() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.startElement("span", null);
        responseWriter.writeText("foo", null);

        String value = writer.toString();
        assertEquals("<span>foo", value);
    }

    public void testStartElementAndWriteTextB() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.startElement("span", null);
        responseWriter.writeText("zxcvbnm".toCharArray(), 1, 3);

        String value = writer.toString();
        assertEquals("<span>xcv", value);
    }

    public void testStartElementAndWriteTextAndEndElement() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.startElement("span", null);
        responseWriter.writeText("aa", null);
        responseWriter.endElement("span");

        String value = writer.toString();
        assertEquals("<span>aa</span>", value);
    }

    public void testSetGetContentType() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        assertNull(responseWriter.getContentType());
        responseWriter.setContentType("some content type");
        assertEquals("some content type", responseWriter.getContentType());
    }

    public void testSetGetCharacterEncoding() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        // assertNull(responseWriter.getCharacterEncoding());
        responseWriter.setCharacterEncoding("some character encoding");
        assertEquals("some character encoding", responseWriter
                .getCharacterEncoding());
    }

    public void testWriteAttribute() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);

        responseWriter.startElement("span", null);
        responseWriter.writeAttribute("a", "<b>", null);

        String value = writer.toString();
        assertEquals("<span a=\"&lt;b&gt;\"", value);
    }

    public void testWriteAttribute_NameIsNull() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        try {
            responseWriter.writeAttribute(null, "attrValue", null);
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    public void testWriteAttribute_NoOpenElement() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        try {
            responseWriter.writeAttribute("attrName", "attrValue", null);
            fail();
        } catch (IllegalStateException ise) {
            AssertUtil.assertExceptionMessageExist(ise);
        }
    }

    public void testWriteURIAttribute_NameIsNull() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        try {
            responseWriter.writeURIAttribute(null, "attrValue", null);
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    public void testWriteURIAttribute_NoOpenElement() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        try {
            responseWriter.writeURIAttribute("attrName", "attrValue", null);
            fail();
        } catch (IllegalStateException ise) {
            AssertUtil.assertExceptionMessageExist(ise);
        }
    }

    public void testWriteURIAttribute() throws Exception {
        assertEquals("url", writeURIAttributeTest("url"));
        assertEquals("/a/b.html", writeURIAttributeTest("/a/b.html"));
        assertEquals("url?a=b", writeURIAttributeTest("url?a=b"));
        assertEquals("url?a=b%3Fc=d", writeURIAttributeTest("url?a=b?c=d"));
        final Character japaneseA = new Character((char) 12354);
        assertEquals("url?a=%E3%81%82", writeURIAttributeTest("url?a="
                + japaneseA));
        assertEquals("%E3%81%82?a=1", writeURIAttributeTest(japaneseA + "?a=1"));
    }

    private String writeURIAttributeTest(String input) throws IOException {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        SPrintWriter writer = new SPrintWriter();
        responseWriter.setWriter(writer);
        responseWriter.setCharacterEncoding("UTF-8");

        responseWriter.startElement("span", null);
        responseWriter.writeURIAttribute("a", input, null);

        String value = writer.toString();
        final String prefix = "<span a=\"";
        final String suffix = "\"";
        assertEquals(true, value.startsWith(prefix));
        assertEquals(true, value.endsWith(suffix));
        value = value.substring(prefix.length());
        value = value.substring(0, value.length() - suffix.length());
        return value;
    }

    public void testEncodeURIAttribute() throws Exception {
        HtmlResponseWriter responseWriter = new HtmlResponseWriter();
        assertEquals("a", responseWriter.encodeURIAttribute("a"));
        assertEquals("url", responseWriter.encodeURIAttribute("url"));
        assertEquals("/a/b.html", responseWriter
                .encodeURIAttribute("/a/b.html"));
        assertEquals("a?1=2", responseWriter.encodeURIAttribute("a?1=2"));
        assertEquals("a?1=2&3=4", responseWriter
                .encodeURIAttribute("a?1=2&3=4"));
        assertEquals("a?1=2%3F3=4", responseWriter
                .encodeURIAttribute("a?1=2?3=4"));
    }

    public void testLeaningUrlEncoder() throws Exception {
        assertEquals("%2F", URLEncoder.encode("/", "UTF-8"));
        assertEquals("+", URLEncoder.encode(" ", "UTF-8"));
        assertEquals("%40", URLEncoder.encode("@", "UTF-8"));
        assertEquals("%3F", URLEncoder.encode("?", "UTF-8"));

        Character japaneseA = new Character((char) 12354);
        assertEquals(12354, japaneseA.charValue());
        assertEquals("3042", Integer.toHexString((int) japaneseA.charValue()));
        assertEquals("%E3%81%82", URLEncoder.encode(japaneseA.toString(),
                "UTF-8"));
        assertEquals("%82%A0", URLEncoder.encode(japaneseA.toString(),
                "Windows-31J"));
    }

    public void testLearningCharacter() throws Exception {
        assertEquals(true, Character.isLetter('A'));
        assertEquals(true, Character.isLetter((char) 12354)); // japanese "a"

        assertEquals(false, Character.isLetter('0'));
        assertEquals(false, Character.isLetter((char) 55893));
    }

}
