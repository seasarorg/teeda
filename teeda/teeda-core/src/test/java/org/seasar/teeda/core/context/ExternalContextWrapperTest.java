/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.context;

import java.io.IOException;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.NullExternalContext;

public class ExternalContextWrapperTest extends TestCase {

    public void test() throws Exception {
        final boolean[] calls = new boolean[] { false };
        ExternalContextWrapper wrapper = new ExternalContextWrapper();
        wrapper.setExternalContext(new NullExternalContext() {
            public void dispatch(String path) throws IOException {
                calls[0] = true;
            }
        });
        wrapper.dispatch("a");
        assertTrue(calls[0]);
    }

}
