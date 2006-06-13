package org.seasar.teeda.spike.rhino;

import org.seasar.teeda.spike.rhino.RhinoTestCase;


public class Target2Test extends RhinoTestCase {

    public void test() throws Exception {
        try {
            createJsTestCase();
        } catch (Throwable t) {
            fail();
        }
    }
}
