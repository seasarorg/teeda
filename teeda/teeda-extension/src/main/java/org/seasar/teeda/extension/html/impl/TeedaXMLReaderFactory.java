/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.html.impl;

import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.parsers.XML11Configuration;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.JsfConstants;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

/**
 * @author koichik
 */
public interface TeedaXMLReaderFactory {

    public static final String PROPERTY_LEXICAL_HANDLER = "http://xml.org/sax/properties/lexical-handler";

    public static final String FEATURE_NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";

    public static final String FEATURE_NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";

    public static final String FEATURE_NOTIFY_BUILTIN_REFS = "http://apache.org/xml/features/scanner/notify-builtin-refs";

    XMLReader createXMLReader() throws SAXException;

    public class DEFAULT implements TeedaXMLReaderFactory {
        public XMLReader createXMLReader() throws SAXException {
            final XMLParserConfiguration configuration = new TeedaXMLConfiguration();
            final SAXParser parser = new TeedaSAXParser(configuration);
            final XMLFilter filter = new TeedaXMLFilter(parser);
            filter.setProperty(PROPERTY_LEXICAL_HANDLER, filter);
            filter.setFeature(FEATURE_NAMESPACE_PREFIXES, true);
            filter.setFeature(FEATURE_NOTIFY_CHAR_REFS, true);
            filter.setFeature(FEATURE_NOTIFY_BUILTIN_REFS, true);
            return filter;
        }
    }

    public class CLASSIC implements TeedaXMLReaderFactory {
        public XMLReader createXMLReader() throws SAXException {
            final XMLParserConfiguration configuration = new TeedaXMLConfiguration();
            final SAXParser parser = new TeedaSAXParser(configuration);
            final XMLFilter filter = new TeedaXMLFilter(parser);
            filter.setProperty(PROPERTY_LEXICAL_HANDLER, filter);
            filter.setFeature(FEATURE_NAMESPACE_PREFIXES, true);
            return filter;
        }
    }

    public class STRICT implements TeedaXMLReaderFactory {
        public XMLReader createXMLReader() throws SAXException {
            final XMLParserConfiguration configuration = new XML11Configuration();
            final SAXParser parser = new TeedaSAXParser(configuration);
            final XMLFilter filter = new TeedaXMLFilter(parser);
            filter.setProperty(PROPERTY_LEXICAL_HANDLER, filter);
            filter.setFeature(FEATURE_NAMESPACE_PREFIXES, true);
            filter.setFeature(FEATURE_NOTIFY_CHAR_REFS, true);
            filter.setFeature(FEATURE_NOTIFY_BUILTIN_REFS, true);
            return filter;
        }
    }

    public class HTML implements TeedaXMLReaderFactory {
        protected static final String CONFIGURATION_CLASS_NAME = "org.cyberneko.html.HTMLConfiguration";

        protected static final String PROPERTY_DEFAULT_ENCODING = "http://cyberneko.org/html/properties/default-encoding";

        protected static final String PROPERTY_ELEMS = "http://cyberneko.org/html/properties/names/elems";

        protected static final String PROPERTY_ATTRS = "http://cyberneko.org/html/properties/names/attrs";

        protected static final String FEATURE_NOTIFY_HTML_BUILTIN_REFS = "http://cyberneko.org/html/features/scanner/notify-builtin-refs";

        public static final String encoding_BINDING = "bindingType=may";

        protected String encoding = JsfConstants.DEFAULT_ENCODING;

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public XMLReader createXMLReader() throws SAXException {
            final XMLParserConfiguration configuration = (XMLParserConfiguration) ClassUtil
                    .newInstance(CONFIGURATION_CLASS_NAME);
            final SAXParser parser = new TeedaSAXParser(configuration);
            final XMLFilter filter = new TeedaXMLFilter(parser);
            filter.setProperty(PROPERTY_LEXICAL_HANDLER, filter);
            filter.setProperty(PROPERTY_DEFAULT_ENCODING, encoding);
            filter.setProperty(PROPERTY_ELEMS, "match");
            filter.setProperty(PROPERTY_ATTRS, "no-change");
            filter.setFeature(FEATURE_NAMESPACE_PREFIXES, true);
            filter.setFeature(FEATURE_NOTIFY_HTML_BUILTIN_REFS, true);
            return filter;
        }
    }

}
