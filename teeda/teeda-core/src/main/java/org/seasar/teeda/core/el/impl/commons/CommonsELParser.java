package org.seasar.teeda.core.el.impl.commons;

import java.io.StringReader;

import javax.faces.el.ReferenceSyntaxException;

import org.apache.commons.el.parser.ELParser;
import org.apache.commons.el.parser.ParseException;
import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.impl.JspELParserUtil;


public class CommonsELParser implements org.seasar.teeda.core.el.ELParser{

    private ExpressionProcessor processor_;
    
    public CommonsELParser(){
        processor_ = new CommonsExpressionProcessorImpl();
    }
    
    public Object parse(final String expression){
        String jspExpression = JspELParserUtil.convertToJspExpression(expression);
        ELParser parser = new ELParser(new StringReader(jspExpression));
        try{
            Object obj = parser.ExpressionString();
            processor_.processExpression(obj, Object.class);
            return obj;
        }catch (ParseException e){
            throw new ReferenceSyntaxException();
        }
    }
    
    public ExpressionProcessor getExpressionProcessor(){
        return processor_;
    }
    
    public void setExpressionProcessor(ExpressionProcessor processor){
        processor_ = processor;
    }
}
