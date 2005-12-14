package org.seasar.teeda.core.config.assembler;

import java.util.Iterator;
import java.util.Locale;

import org.seasar.teeda.core.config.element.impl.LocaleConfigElementImpl;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestLocaleConfigAssembler extends TeedaTestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestLocaleConfigAssembler.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for TestLocaleConfigAssembler.
     * @param arg0
     */
    public TestLocaleConfigAssembler(String arg0) {
        super(arg0);
    }

    public void testSimpleAssembleLocaleConfig(){
        MockApplication application = getApplication();
        LocaleConfigElementImpl element = new LocaleConfigElementImpl();
        element.setDefaultLocale("en");
        element.addSupportedLocale("ja");
        element.addSupportedLocale("fr");
        
        LocaleConfigAssembler assembler = new LocaleConfigAssembler(element, application);
        assembler.assemble();
        
        assertEquals(Locale.ENGLISH, application.getDefaultLocale());
        for(Iterator itr = application.getSupportedLocales();itr.hasNext();){
            Locale locale = (Locale)itr.next();
            System.out.println(locale);
        }
    }
    
}
