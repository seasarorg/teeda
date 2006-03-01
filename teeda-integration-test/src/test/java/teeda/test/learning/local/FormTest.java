package teeda.test.learning.local;

import java.net.URL;

import junit.framework.TestCase;

import org.seasar.framework.util.ResourceUtil;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class FormTest extends TestCase {

    public void testFormSubmit() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("formSubmit1.html");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page1 = (HtmlPage) webClient.getPage(url);
        HtmlForm form = page1.getFormByName("myFormName");
        HtmlSubmitInput button = (HtmlSubmitInput) form
            .getInputByName("doSubmitName");
        HtmlPage page2 = (HtmlPage) button.click();

        // ## Assert ##
        assertEquals("end", page2.getTitleText());
        assertEquals(getFileAsUrlByPackageName("end.html"), page2
            .getWebResponse().getUrl());
    }

    private URL getFileAsUrlByPackageName(String s) {
        return ResourceUtil.getResource(getClass().getPackage().getName()
            .replace('.', '/')
            + "/" + s);
    }

    public void testFormSubmit_UsingIdAttribute() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("formSubmit1.html");
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
        assertEquals(getFileAsUrlByPackageName("end.html"), page2
            .getWebResponse().getUrl());
    }

    private URL getFileAsUrl(String s) {
        return ResourceUtil.getResource(getClass().getName().replace('.', '/')
            + "_" + s);
    }

}
