package org.seasar.teeda.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class TagConvertUtil {

    private static final String[] EMPTY_STRING = new String[0];
    private TagConvertUtil(){
    }
    
    public static String[] convertToSetter(String tagName){
        return convertToSetter(tagName, "-");
    }
    
    public static String[] convertToSetter(String tagName, String delim){
        if(tagName == null){
            return EMPTY_STRING;
        }
        
        if(tagName.indexOf(delim) < 0){
            tagName = capitalizePropertyName(tagName);
            return createSetters(tagName);
        }
        
        StringBuffer buf = new StringBuffer();
        StringTokenizer st = new StringTokenizer(tagName, delim);
        String s = null;
        while (st.hasMoreElements()) {
            s = (String)st.nextElement();
            if(s.length() >= 1){
                buf.append(capitalizePropertyName(s));
            }
        }
        return createSetters(buf.toString());
    }
    
    private static String capitalizePropertyName(String propertyName){
        if(propertyName.length() >= 1){
            char[] chars = propertyName.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        }
        return propertyName;
    }
    
    private static String[] createSetters(String property){
        return new String[]{"set" + property, "add" + property};
    }
}
