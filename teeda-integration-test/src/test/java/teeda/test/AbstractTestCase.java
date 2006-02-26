package teeda.test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.seasar.jsf.selenium.MavenUtil;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public abstract class AbstractTestCase extends TestCase {

    protected static WebApplicationTestSetup setUpTestSuite(
        final Class testClass) {
        if (testClass == null) {
            throw new NullPointerException("testClass");
        }
        JettyServerSetup jettySetup = new JettyServerSetup(new TestSuite(
            testClass));
        File project = MavenUtil.getProjectPomFile(testClass).getParentFile();
        File webapp = new File(project, "target/teeda-integration-test");
        jettySetup.setWebapp(webapp);

        WebApplicationTestSetup webApplicationTestSetup = new WebApplicationTestSetup(
            jettySetup);
        webApplicationTestSetup.setTestClass(testClass);
        return webApplicationTestSetup;
    }

    private String baseUrl_ = "http://localhost:8080/";

    protected URL getUrl(String path) throws MalformedURLException {
        return new URL(baseUrl_ + path);
    }

    protected String getBody(HtmlPage page) throws UnsupportedEncodingException {
        WebResponse webResponse = page.getWebResponse();
        return new String(webResponse.getResponseBody(), page.getPageEncoding());
    }

}
