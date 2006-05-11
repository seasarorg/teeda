package org.seasar.teeda.extension.html;

import java.io.InputStream;

public interface HtmlParser {

    HtmlNode parse(InputStream is);
}
