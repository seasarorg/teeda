package org.seasar.teeda.extension.html.node;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.Attributes;

public class HtmlNodeUtil {

    protected HtmlNodeUtil() {
    }
    
    public static Map convertMap(Attributes attributes) {
        Map map = new HashMap();
        for (int i = 0; i < attributes.getLength(); ++i) {
            String qname = attributes.getQName(i);
            String value = attributes.getValue(i);
            map.put(qname, value);
        }
        return map;
    }
    
    public static String getEmptyTagString(String tagName, Map properties) {
        return getStartTagString0(tagName, properties) + " />";
    }
    
    public static String getStartTagString(String tagName, Map properties) {
        return getStartTagString0(tagName, properties) + ">";
    }
    
    public static String getEndTagString(String tagName) {
        return "</" + tagName + ">";
    }
    
    protected static String getStartTagString0(String tagName, Map properties) {
        StringBuffer buf = new StringBuffer(100);
        buf.append("<");
        buf.append(tagName);
        for (Iterator i = properties.keySet().iterator(); i.hasNext(); ) {
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