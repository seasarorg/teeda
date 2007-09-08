package examples.teeda.it;

import org.seasar.teeda.core.unit.seleniumunit.SeleniumTestCase;

/**
 * jettyでseleniumのテスト(add.html)
 * 
 * @author yone
 */
public class AddTest extends SeleniumTestCase {

    /**
     * add.htmlテスト
     * 
     * @throws Exception
     */
    public void testAdd2Html() throws Exception {
        selenium.open("/teeda-selenium-project/view/add/add2.html");
        // for DEBUG
        System.out.println(selenium.getHtmlSource());

        assertEquals("Add2", selenium.getTitle());

        selenium.type("arg1", "111");
        selenium.type("arg2", "222");
        selenium.click("doCalculate");
        selenium.waitForPageToLoad("30000");
        assertEquals("333", selenium.getText("result"));
        selenium.type("arg1", "aaa");
        selenium.type("arg2", "bbb");
        selenium.click("doCalculate");
        // for DEBUG
        System.out.println(selenium.getHtmlSource());
    }
}
