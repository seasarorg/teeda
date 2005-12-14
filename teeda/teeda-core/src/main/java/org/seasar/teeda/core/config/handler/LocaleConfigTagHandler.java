package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.ApplicationElement;
import org.seasar.teeda.core.config.element.LocaleConfigElement;
import org.seasar.teeda.core.config.element.impl.LocaleConfigElementImpl;
import org.xml.sax.Attributes;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class LocaleConfigTagHandler extends JsfTagHandler {

    public LocaleConfigTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        LocaleConfigElement localeConfigTag = createLocaleConfigTag();
        context.push(localeConfigTag);
    }

    public void end(TagHandlerContext context, String body) {
        LocaleConfigElement localeConfigTag = (LocaleConfigElement)context.pop();
        ApplicationElement applicationTag = (ApplicationElement)context.peek();
        applicationTag.addLocaleConfig(localeConfigTag);
    }
    
    protected LocaleConfigElement createLocaleConfigTag(){
        return new LocaleConfigElementImpl();
    }
}
