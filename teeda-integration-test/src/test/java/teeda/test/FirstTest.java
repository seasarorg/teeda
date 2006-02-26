package teeda.test;

import java.net.URL;

import junit.framework.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class FirstTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(FirstTest.class);
    }

    public void testA() throws Exception {
        URL url = getUrl("a.html");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page1 = (HtmlPage) webClient.getPage(url);

        // ## Assert ##
        assertEquals("this is a.html", page1.getTitleText());
    }

}
