package org.seasar.teeda.it.learning.server;

import org.seasar.teeda.it.AbstractTestCase;

import junit.framework.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class LinkTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(LinkTest.class);
    }

    public void testLink() throws Exception {
        // ## Arrange ##
        WebClient webClient = new WebClient();
        HtmlPage page1 = (HtmlPage) webClient
            .getPage(getUrl("learning/LinkTest_1.html"));
        HtmlElement link = page1.getHtmlElementById("a");
        HtmlAnchor htmlAnchor = (HtmlAnchor) link;

        // ## Act ##
        HtmlPage page2 = (HtmlPage) htmlAnchor.click();

        // ## Assert ##
        assertEquals("link 2", page2.getTitleText());
    }

}
