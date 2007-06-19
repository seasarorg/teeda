package examples.teeda.it;

import junit.framework.TestCase;

import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * Selenium用TestCase
 * 
 * @author yone
 */
abstract public class SeleniumTestCase extends TestCase {

    // ブラウザIE
    public static final String BROWSER_IEXPLORE = "*iexplore";

    // ブラウザFF
    public static final String BROWSER_FIREFOX = "*firefox";

    protected SeleniumServer seleniumServer;

    protected Selenium selenium;

    // デフォルトで使用するブラウザ(IE)
    private String browser = BROWSER_IEXPLORE;
    //private String browser = BROWSER_FIREFOX;

    //@Override
    protected void setUp() throws Exception {
        super.setUp();

        startSeleniumServer();
        startSelenium();
    }

    //@Override
    protected void tearDown() throws Exception {
        super.tearDown();
        stopSelenium();
        stopSeleniumServer();
    }

    private void startSeleniumServer() throws Exception {
        seleniumServer = new SeleniumServer();
        seleniumServer.start();
    }

    private void stopSeleniumServer() {
        seleniumServer.stop();
    }

    private void startSelenium() {
        String url = "http://localhost:8080/";

        selenium = new DefaultSelenium("localhost",
                SeleniumServer.DEFAULT_PORT, browser, url);

        selenium.start();
    }

    private void stopSelenium() {
        selenium.stop();
    }

    /**
     * テストで使用するブラウザを指定します
     * 
     * @param browser
     */
    public void setBrowser(final String browser) {
        this.browser = browser;
    }
}
