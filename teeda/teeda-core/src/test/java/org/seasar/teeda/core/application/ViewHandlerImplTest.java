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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ViewHandlerImplTest extends TeedaTestCase {

    private Locale orgDefaultLocale_;

    private Iterator orgSupportedLocales_;

    protected void setUp() throws Exception {
        orgDefaultLocale_ = getApplication().getDefaultLocale();
        orgSupportedLocales_ = getApplication().getSupportedLocales();
    }

    protected void tearDown() throws Exception {
        getApplication().setDefaultLocale(orgDefaultLocale_);
        List list = new ArrayList();
        for (; orgSupportedLocales_.hasNext();) {
            list.add(orgSupportedLocales_.next());
        }
        getApplication().setSupportedLocales(list);
    }

    public void testCalculateLocale_facesContextIsNull() throws Exception {
        ViewHandlerImpl handler = new ViewHandlerImpl();
        try {
            handler.calculateLocale(null);
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

    public void fixme_testGetLocaleFromSupportedLocales() throws Exception {
        ViewHandlerImpl handler = new ViewHandlerImpl();
        // handler.getLocaleFromSupportedLocales(null);
    }
}
