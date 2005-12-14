package org.seasar.teeda.core.el.impl.commons;

import javax.faces.el.EvaluationException;
import javax.servlet.jsp.el.ELException;

import org.apache.commons.el.Coercions;
import org.apache.commons.el.Logger;


public class CoercionsUtil {

    private static final org.seasar.framework.log.Logger logger_ = 
        org.seasar.framework.log.Logger.getLogger(CoercionsUtil.class);
    
    private CoercionsUtil(){
    }

    public static Integer coerceToInteger(Object index){
        return coerceToInteger(index, CommonsElLogger.getLogger());
    }
    
    public static Integer coerceToInteger(Object index, Logger logger) {
        try{
            return Coercions.coerceToInteger(index, logger);
        }catch (ELException e){
            logger_.error(e + " occured at " + CoercionsUtil.class);
            return null;
        }
    }
    
    public static boolean coerceToPrimitiveBoolean(Object value) throws EvaluationException{
        return coerceToPrimitiveBoolean(value, CommonsElLogger.getLogger());
    }
    
    public static boolean coerceToPrimitiveBoolean(Object value, Logger logger) throws EvaluationException{
        try{
            return Coercions.coerceToBoolean(value, logger).booleanValue();
        }catch(ELException e){
            throw new EvaluationException(e);
        }
    }

    public static Object coerce(Object newValue, Class type) throws EvaluationException{
        try{
            return Coercions.coerce(newValue, type, CommonsElLogger.getLogger());
        }catch (ELException e){
            throw new EvaluationException(e);
        }
    }
    
}
