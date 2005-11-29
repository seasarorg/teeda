package org.seasar.teeda.core.el.impl;

import javax.faces.el.ReferenceSyntaxException;

import org.seasar.framework.exception.EmptyRuntimeException;


public class JspELParserUtil {

    private static final String JSP_OPEN_BRACE = "${";
    
    private static final String JSP_CLOSE_BRACE = "}";
    
    private JspELParserUtil(){
    }
    
    public static String convertToJspExpression(final String expression){
        return convertToJspExpression(expression, "#{", "}");
    }
    
    public static String convertToJspExpression(final String expression, final String openBrace, final String closeBrace){
        if(expression == null){
            throw new EmptyRuntimeException("expression");
        }
        if(openBrace == null || closeBrace == null){
            throw new ReferenceSyntaxException();
        }
        String str = expression;
        if(str.startsWith(openBrace) && str.indexOf(closeBrace) > 0){
            int pos = str.indexOf(closeBrace);
            String s = str.substring(JSP_OPEN_BRACE.length(), pos);
            String postfix = str.substring(pos + 1); 
            str = JSP_OPEN_BRACE + s + JSP_CLOSE_BRACE + convertToJspExpression(postfix);
        }else{
            int pos1 = str.indexOf(openBrace);
            int pos2 = str.indexOf(closeBrace);
            if(pos1 > 0){
                if(pos2 > pos1){
                    String prefix = str.substring(0, pos1);
                    String s = str.substring(pos1 + openBrace.length(), pos2);
                    String postfix = str.substring(pos2 + 1);
                    if(str.indexOf("\\") != pos1 - 1 && str.indexOf("'") != pos1 - 1 && str.indexOf("'") != pos2 - 1){
                        str = prefix + JSP_OPEN_BRACE + s + JSP_CLOSE_BRACE + convertToJspExpression(postfix);
                    }
                }else if(pos2 >= 0 && pos2 <= pos1){
                    String prefix = str.substring(0, pos2 + 1);
                    String s = str.substring(pos2 + 1);
                    str = prefix + convertToJspExpression(s);
                }
            }
        }
        return str;
    }

}
