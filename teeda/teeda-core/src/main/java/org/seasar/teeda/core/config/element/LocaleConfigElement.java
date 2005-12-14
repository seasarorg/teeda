package org.seasar.teeda.core.config.element;

import java.util.List;
import java.util.Locale;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public interface LocaleConfigElement extends JsfConfig {

    public void setDefaultLocale(String defaultLocaleName);

    public Locale getDefaultLocale();
    
    public void addSupportedLocale(String supportedLocaleName);
    
    public List getSupportedLocales();
    
}
