package org.seasar.teeda.core.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.seasar.teeda.core.JsfConstants;

public class MessageFormatterUtil {

    private MessageFormatterUtil() {
    }

    public static String getMessage(String messageCode, Object[] args) {
        if(messageCode == null){
            messageCode = "";
        }
        return getFormattedMessage(messageCode, args);
    }

    public static String getSimpleMessage(String messageCode, Object[] args) {
        try{
            String pattern = getPattern(messageCode);
            if(pattern != null){
                return MessageFormat.format(pattern, args);
            }
        }catch (Throwable ignore){
        }
        return getNoPatternMessage(args);
    }

    private static String getPattern(String messageCode) {
        ResourceBundle resourceBundle = getMessages(getSystemName(messageCode));
        if(resourceBundle != null){
            return resourceBundle.getString(messageCode);
        }
        return null;
    }

    private static String getSystemName(String messageCode) {
        return messageCode.substring(1, Math.min(4, messageCode.length()));
    }

    private static ResourceBundle getMessages(String systemName) {
        return ResourceBundle.getBundle(systemName + JsfConstants.MESSAGES);
    }

    private static String getNoPatternMessage(Object[] args) {
        final String delimeter = ", ";
        if(ArrayUtil.isEmpty(args)){
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < args.length; i++){
            buffer.append(args[i] + delimeter);
        }
        buffer.setLength(buffer.length() - delimeter.length());
        return buffer.toString();
    }
    
    public static String getFormattedMessage(String messageCode, Object[] args){
        return getFormattedMessage(messageCode, getSimpleMessage(messageCode, args));
    }
    
    public static String getFormattedMessage(String messageCode, String simpleMessage){
        return "[" + messageCode + "]" + simpleMessage;
    }
}