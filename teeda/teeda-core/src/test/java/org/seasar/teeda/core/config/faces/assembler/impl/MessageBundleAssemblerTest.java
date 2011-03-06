/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.faces.assembler.impl;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class MessageBundleAssemblerTest extends TeedaTestCase {

    /**
     * Constructor for MessageBundleAssemblerTest.
     * @param name
     */
    public MessageBundleAssemblerTest(String name) {
        super(name);
    }

    public void testAssemble() throws Exception {
        MessageBundleAssembler assembler = new MessageBundleAssembler("hoge",
                getApplication());
        assembler.assemble();

        MockApplication app = getApplication();
        assertEquals("hoge", app.getMessageBundle());
    }
}
