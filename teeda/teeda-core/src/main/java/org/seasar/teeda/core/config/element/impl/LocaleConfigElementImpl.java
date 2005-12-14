package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.seasar.framework.util.LocaleUtil;
import org.seasar.teeda.core.config.element.LocaleConfigElement;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class LocaleConfigElementImpl implements LocaleConfigElement {

    private Locale defaultLocale_;
    private List supportedLocales_;
    
    public LocaleConfigElementImpl(){
        supportedLocales_ = new ArrayList();
    }
    
    public void setDefaultLocale(String defaultLocaleName) {
        defaultLocale_ = LocaleUtil.getLocale(defaultLocaleName);
    }

    public Locale getDefaultLocale() {
        return defaultLocale_;
    }

    public void addSupportedLocale(String supportedLocaleName) {
        Locale locale = LocaleUtil.getLocale(supportedLocaleName);
        supportedLocales_.add(locale);
    }

    public List getSupportedLocales() {
        return supportedLocales_;
    }

}
