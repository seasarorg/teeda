package org.seasar.teeda.core.config.assembler;

import java.util.List;
import java.util.Locale;

import javax.faces.application.Application;

import org.seasar.teeda.core.config.element.LocaleConfigElement;


public class LocaleConfigAssembler implements JsfAssembler{

    private LocaleConfigElement localeConfig_;
    private Application application_;
    
    public LocaleConfigAssembler(LocaleConfigElement localeConfig, Application application) {
        localeConfig_ = localeConfig;
        application_ = application;
    }

    public void assemble() {
        Locale defaultLocale = localeConfig_.getDefaultLocale();
        application_.setDefaultLocale(defaultLocale);
        
        List supportedLocales = localeConfig_.getSupportedLocales();
        application_.setSupportedLocales(supportedLocales);
    }
    
}