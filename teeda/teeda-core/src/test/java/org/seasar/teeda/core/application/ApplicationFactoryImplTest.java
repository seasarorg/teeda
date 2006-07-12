/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.application;

import javax.faces.application.Application;

import org.seasar.extension.unit.S2TestCase;

public class ApplicationFactoryImplTest extends S2TestCase {

    public void testApplicationFactoryImpl() {
        super.include("applicationTest.dicon");
        ApplicationFactoryImpl factory = new ApplicationFactoryImpl();
        Application app = factory.getApplication();
        assertNotNull(app);
    }
}
