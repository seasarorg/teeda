package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.SaxHandler;
import org.seasar.framework.xml.SaxHandlerParser;
import org.seasar.framework.xml.TagHandlerRule;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.rule.FacesConfigTagHandlerRule;
import org.seasar.teeda.core.unit.TeedaTestCase;

public abstract class TagHandlerTestCase extends TeedaTestCase {

    public TagHandlerTestCase() {
        super();
    }

    public TagHandlerTestCase(String name) {
        super(name);
    }

    public FacesConfig parse(String path) {
        TagHandlerRule rule = new FacesConfigTagHandlerRule();
        SaxHandler handler = new SaxHandler(rule);
        SaxHandlerParser parser = new SaxHandlerParser(handler);
        Object o = parser.parse(convertPath(path));
        return (FacesConfig) o;
    }

}
