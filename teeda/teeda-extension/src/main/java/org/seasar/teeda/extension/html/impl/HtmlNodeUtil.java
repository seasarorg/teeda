/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.seasar.teeda.core.util.EmptyElementUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.xml.sax.Attributes;

/**
 * @author higa
 * @author shot
 */
public class HtmlNodeUtil {

    protected HtmlNodeUtil() {
    }

    public static Map convertMap(Attributes attributes) {
        Map map = new HashMap();
        for (int i = 0; i < attributes.getLength(); ++i) {
            String qname = attributes.getQName(i);
            String localName = attributes.getLocalName(i);
            String uri = attributes.getURI(i);
            String value = attributes.getValue(i);
            if (ExtensionConstants.TEEDA_EXTENSION_URI.equals(uri)) {
                map.put(localName, value);
            } else if (!map.containsKey(qname)) {
                map.put(qname, value);
            }
        }
        return map;
    }

    public static String getCompleteTagString(String tagName, Map properties) {
        if (EmptyElementUtil.isEmptyElement(tagName)) {
            return getStartTagString0(tagName, properties) + " />";
        } else {
            return getStartTagString0(tagName, properties) + ">"
                    + getEndTagString0(tagName);
        }
    }

    public static String getStartTagString(String tagName, Map properties) {
        if (EmptyElementUtil.isEmptyElement(tagName)) {
            return getStartTagString0(tagName, properties) + " />";
        } else {
            return getStartTagString0(tagName, properties) + ">";
        }
    }

    public static String getEndTagString(String tagName) {
        if (EmptyElementUtil.isEmptyElement(tagName)) {
            return "";
        } else {
            return getEndTagString0(tagName);
        }
    }

    protected static String getEndTagString0(String tagName) {
        return "</" + tagName + ">";
    }

    protected static String getStartTagString0(String tagName, Map properties) {
        StringBuffer buf = new StringBuffer(100);
        buf.append("<");
        buf.append(tagName);
        for (Iterator i = properties.keySet().iterator(); i.hasNext();) {
            String name = (String) i.next();
            String value = "\"" + properties.get(name) + "\"";
            buf.append(" ");
            buf.append(name);
            buf.append("=");
            buf.append(value);
        }
        return buf.toString();
    }
}