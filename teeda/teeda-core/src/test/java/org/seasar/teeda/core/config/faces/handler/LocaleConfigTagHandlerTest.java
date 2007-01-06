/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.faces.handler;

import java.util.List;
import java.util.Locale;

import org.seasar.teeda.core.config.faces.element.ApplicationElement;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.faces.element.LocaleConfigElement;
import org.seasar.teeda.core.config.faces.element.impl.ApplicationElementImpl;

public class LocaleConfigTagHandlerTest extends TagHandlerTestCase {

    public void testLocaleConfigHandler() {
        ApplicationElement app = new ApplicationElementImpl();
        getContext().push(app);
        LocaleConfigTagHandler handler = new LocaleConfigTagHandler();

        handler.start(getContext(), new NullAttributes());
        assertTrue(getContext().peek() instanceof LocaleConfigElement);

        handler.end(getContext(), "a");

        ApplicationElement app2 = (ApplicationElement) getContext().pop();
        assertEquals(app, app2);
        List locales = app2.getLocaleConfigs();
        assertEquals(1, locales.size());
        assertTrue(locales.get(0) instanceof LocaleConfigElement);
    }

    public void testLocaleConfigHandlerByXMLParse() {
        FacesConfig config = parse("testLocaleConfigTagHandler.xml");
        List apps = config.getApplicationElements();
        assertNotNull(apps.get(0));

        ApplicationElement appElement = (ApplicationElement) apps.get(0);
        List localeConfigs = appElement.getLocaleConfigs();
        assertNotNull(localeConfigs.get(0));

        LocaleConfigElement localeConfig = (LocaleConfigElement) localeConfigs
                .get(0);
        assertEquals(Locale.JAPANESE, localeConfig.getDefaultLocale());
        List supportedLocale = localeConfig.getSupportedLocales();
        assertEquals(Locale.ENGLISH, supportedLocale.get(0));
        assertEquals(Locale.FRENCH, supportedLocale.get(1));
    }

}
