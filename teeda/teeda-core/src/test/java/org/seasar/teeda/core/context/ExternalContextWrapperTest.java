package org.seasar.teeda.core.context;

import java.io.IOException;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.NullExternalContext;

public class ExternalContextWrapperTest extends TestCase {

    public void test() throws Exception {
        final boolean[] calls = new boolean[] {false};
        ExternalContextWrapper wrapper = new ExternalContextWrapper();
        wrapper.setExternalContext(new NullExternalContext(){
            public void dispatch(String path) throws IOException {
                calls[0] = true;
            }
        });
        wrapper.dispatch("a");
        assertTrue(calls[0]);
    }
    
}
