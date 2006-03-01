package teeda.test.learning.server;

import java.net.URL;

import teeda.test.AbstractTestCase;

import junit.framework.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class FormTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(FormTest.class);
    }

    public void testFormSubmit() throws Exception {
        URL url = getUrl("learning/FormTest_formSubmit1.html");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page1 = (HtmlPage) webClient.getPage(url);
        HtmlForm form = (HtmlForm) page1.getHtmlElementById("myFormId");
        HtmlSubmitInput button = (HtmlSubmitInput) form
            .getHtmlElementById("doSubmitId");
        HtmlPage page2 = (HtmlPage) button.click();

        // ## Assert ##
        assertEquals("end", page2.getTitleText());
        assertEquals(getUrl("learning/end.html"), page2
            .getWebResponse().getUrl());
    }

    public void testFormSubmitParam() throws Exception {
        // ## Arrange ##
        URL url = getUrl("learning/FormTest_formSubmitParam.html");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page1 = (HtmlPage) webClient.getPage(url);
        HtmlForm form = (HtmlForm) page1.getHtmlElementById("myFormId");
        HtmlInput a = (HtmlInput) form.getHtmlElementById("a");
        a.setNodeValue("a_value");
        HtmlSubmitInput button = (HtmlSubmitInput) form
            .getHtmlElementById("doSubmitId");
        HtmlPage page2 = (HtmlPage) button.click();

        // ## Assert ##
        assertEquals(url, page2.getWebResponse().getUrl());
    }

}
