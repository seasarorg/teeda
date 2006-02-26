package teeda.test;

import java.net.URL;

import junit.framework.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public class HelloWorldTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(HelloWorldTest.class);
    }

    public void testHelloWorld() throws Exception {
        URL url = getUrl("faces/helloWorld.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);
        assertEquals(true, -1 < body.indexOf("Hello World!"));

        HtmlSpan span = (HtmlSpan) page.getHtmlElementById("hello");
        System.out.println(span);
        assertEquals("Hello World!", span.asText());

        // TODO
        //assertEquals("this is helloWorld.jsp", page.getTitleText());
    }

}
