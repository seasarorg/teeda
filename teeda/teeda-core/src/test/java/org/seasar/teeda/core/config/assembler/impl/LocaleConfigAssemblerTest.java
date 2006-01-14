package org.seasar.teeda.core.config.assembler.impl;

import java.util.Iterator;
import java.util.Locale;

import org.seasar.teeda.core.config.assembler.impl.LocaleConfigAssembler;
import org.seasar.teeda.core.config.element.impl.LocaleConfigElementImpl;
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
