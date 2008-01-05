/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import java.util.Iterator;
import java.util.Locale;

import org.seasar.teeda.core.config.faces.element.impl.LocaleConfigElementImpl;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class LocaleConfigAssemblerTest extends TeedaTestCase {

    public void testSimpleAssembleLocaleConfig() {
        MockApplication application = getApplication();
        LocaleConfigElementImpl element = new LocaleConfigElementImpl();
        element.setDefaultLocale("en");
        element.addSupportedLocale("ja");
        element.addSupportedLocale("fr");

        LocaleConfigAssembler assembler = new LocaleConfigAssembler(element,
                application);
        assembler.assemble();

        assertEquals(Locale.ENGLISH, application.getDefaultLocale());
        for (Iterator itr = application.getSupportedLocales(); itr.hasNext();) {
            Locale locale = (Locale) itr.next();
            System.out.println(locale);
        }
    }

}
