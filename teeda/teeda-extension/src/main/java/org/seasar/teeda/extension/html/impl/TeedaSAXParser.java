package org.seasar.teeda.extension.html.impl;

import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class TeedaSAXParser extends SAXParser {

    public TeedaSAXParser() throws SAXNotRecognizedException,
            SAXNotSupportedException {
        super(new TeedaXMLConfiguration());
        setFeature("http://apache.org/xml/features/scanner/notify-char-refs",
                true);
        setFeature(
                "http://apache.org/xml/features/scanner/notify-builtin-refs",
                true);
        setFeature("http://xml.org/sax/features/namespace-prefixes", true);
    }

}