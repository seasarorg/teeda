package examples.teeda.it;

import org.seasar.teeda.core.unit.seleniumunit.SeleniumTestCase;

/**
 * tomcat5xでseleniumのテスト
 * 
 * @author yone
 */
public class IndexTest extends SeleniumTestCase {

    //@Override
    protected void setUp() throws Exception {
        //super.setBrowser(SeleniumTestCase.BROWSER_FIREFOX);
        super.setUp();
    }

    /**
     * index.htmlを表示するテスト
     * 
     * @throws Exception
     */
    public void testIndexHtml() throws Exception {
        selenium.open("/teeda-selenium-project/");

        assertEquals("Teeda Extension samples", selenium.getTitle());
    }
}
