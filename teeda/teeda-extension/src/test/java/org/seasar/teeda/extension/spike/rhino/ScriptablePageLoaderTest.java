package org.seasar.teeda.extension.spike.rhino;

import java.net.URL;

import junit.framework.TestCase;

public class ScriptablePageLoaderTest extends TestCase {

    public void testLoad() {
        ScriptablePageLoader loader = new ScriptablePageLoader();
        String htmlPath = "org/seasar/teeda/extension/spike/rhino/ScriptPageTest.html";
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        URL url = classLoader.getResource(htmlPath);
        htmlPath = url.getFile();
        try {
            loader.load(htmlPath);
        } catch (Throwable t) {
            fail();
        }
    }

}
