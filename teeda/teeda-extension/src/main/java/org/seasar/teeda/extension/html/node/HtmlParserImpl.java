package org.seasar.teeda.extension.html.node;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;

import org.seasar.framework.util.SAXParserFactoryUtil;
import org.seasar.framework.util.SAXParserUtil;
import org.seasar.teeda.extension.html.HtmlNode;
import org.seasar.teeda.extension.html.HtmlParser;
import org.xml.sax.InputSource;

public class HtmlParserImpl implements HtmlParser {

    public HtmlNode parse(InputStream is) {
        SAXParser parser = SAXParserFactoryUtil.newSAXParser();
        HtmlNodeHandler handler = new HtmlNodeHandler();
        SAXParserUtil.parse(parser, new InputSource(is), handler);
        return handler.getRoot();
    }
}