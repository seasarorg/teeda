package teeda.test.learning.local;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.seasar.framework.util.ResourceUtil;

import com.gargoylesoftware.htmlunit.CollectingAlertHandler;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.KeyValuePair;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequestSettings;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class JavascriptTest extends TestCase {

    public void testOnloadAlert() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("onload.html");
        System.out.println(url);

        // ## Act ##
        WebClient webClient = new WebClient();
        List collectedAlerts = new ArrayList();
        webClient.setAlertHandler(new CollectingAlertHandler(collectedAlerts));

        HtmlPage page = (HtmlPage) webClient.getPage(url);

        // ## Assert ##
        assertEquals("alert sample", page.getTitleText());
        assertEquals(1, collectedAlerts.size());
        assertEquals("foo", collectedAlerts.get(0));
    }

    public void testFormObj() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("formObj.html");
        System.out.println(url);

        WebClient webClient = new WebClient();
        List collectedAlerts = new ArrayList();
        webClient.setAlertHandler(new CollectingAlertHandler(collectedAlerts));

        // ## Act ##

        HtmlPage page = (HtmlPage) webClient.getPage(url);
        HtmlInput input = (HtmlInput) page.getHtmlElementById("button1");
        input.click();

        // ## Assert ##
        assertEquals(1, collectedAlerts.size());
        System.out.println(collectedAlerts);
        assertEquals("fooForm", collectedAlerts.get(0));
    }

    public void testSubmitByButton() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("submitByButton.html");
        System.out.println(url);

        WebClient webClient = new WebClient();
        List collectedAlerts = new ArrayList();
        webClient.setAlertHandler(new CollectingAlertHandler(collectedAlerts));

        // ## Act ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);
        HtmlInput input = (HtmlInput) page.getHtmlElementById("button1");

        HtmlPage page2 = (HtmlPage) input.click();

        // ## Assert ##
        assertEquals("end", page2.getTitleText());
        assertEquals(getFileAsUrlByPackageName("end.html"), page2
            .getWebResponse().getUrl());
    }

    private static class MyWebClient extends WebClient {
        private WebRequestSettings webRequestSettings_;

        public Page getPage(WebWindow webWindow, WebRequestSettings parameters)
            throws IOException, FailingHttpStatusCodeException {
            webRequestSettings_ = parameters;
            return super.getPage(webWindow, parameters);
        }

        public WebRequestSettings getWebRequestSettings() {
            return webRequestSettings_;
        }

        public KeyValuePair getRequestParameter(String key) {
            for (Iterator it = webRequestSettings_.getRequestParameters()
                .iterator(); it.hasNext();) {
                KeyValuePair pair = (KeyValuePair) it.next();
                if (key.equals(pair.getKey())) {
                    return pair;
                }
            }
            return null;
        }
    }

    public void testSubmitByLink() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("submitByLink.html");
        System.out.println(url);

        MyWebClient webClient = new MyWebClient();
        List collectedAlerts = new ArrayList();
        webClient.setAlertHandler(new CollectingAlertHandler(collectedAlerts));

        // ## Act ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);
        HtmlAnchor link = (HtmlAnchor) page.getHtmlElementById("link1");

        assertEquals(0, collectedAlerts.size());
        HtmlPage page2 = (HtmlPage) link.click();

        // ## Assert ##
        assertEquals(1, collectedAlerts.size());
        assertEquals("end", page2.getTitleText());
        assertEquals(getFileAsUrlByPackageName("end.html"), page2
            .getWebResponse().getUrl());

        KeyValuePair linkMarker = webClient
            .getRequestParameter("fooForm:__link_clicked__");
        assertNotNull(linkMarker);
        assertEquals("fooForm:link1", linkMarker.getValue());
    }

    private URL getFileAsUrl(String s) {
        return ResourceUtil.getResource(getClass().getName().replace('.', '/')
            + "_" + s);
    }

    private URL getFileAsUrlByPackageName(String s) {
        return ResourceUtil.getResource(getClass().getPackage().getName()
            .replace('.', '/')
            + "/" + s);
    }

}
