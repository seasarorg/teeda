package org.seasar.teeda.extension.spike.rhino;


public class Target2Test extends RhinoTestCase {

    public void test() throws Exception {
        try {
            createJsTestCase();
        } catch (Throwable t) {
            fail();
        }
    }
}
