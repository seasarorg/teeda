package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.SaxHandler;
import org.seasar.framework.xml.SaxHandlerParser;
import org.seasar.framework.xml.TagHandlerRule;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.rule.FacesConfigTagHandlerRule;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.xml.sax.Attributes;

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

    protected static class NullAttributes implements Attributes {

        public int getLength() {
            return 0;
        }

        public String getURI(int arg0) {
            return null;
        }

        public String getLocalName(int arg0) {
            return null;
        }

        public String getQName(int arg0) {
            return null;
        }

        public String getType(int arg0) {
            return null;
        }

        public String getValue(int arg0) {
            return null;
        }

        public int getIndex(String arg0, String arg1) {
            return 0;
        }

        public int getIndex(String arg0) {
            return 0;
        }

        public String getType(String arg0, String arg1) {
            return null;
        }

        public String getType(String arg0) {
            return null;
        }

        public String getValue(String arg0, String arg1) {
            return null;
        }

        public String getValue(String arg0) {
            return null;
        }
        
    }
}
