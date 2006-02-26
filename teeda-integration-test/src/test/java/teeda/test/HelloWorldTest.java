package teeda.test;

import java.net.URL;

import junit.framework.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HelloWorldTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(HelloWorldTest.class);
    }

    public void testHelloWorld() throws Exception {
        URL url = getUrl("helloWorld.jsf");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        // ## Assert ##
        HtmlElement elem = page.getHtmlElementById("hello");
        System.out.println(elem);
        assertEquals("this is a.html", page.getTitleText());
    }

}
