package org.seasar.teeda.core.el;


public interface ELParser {

    public Object parse(String expression);
    
    public void setExpressionProcessor(ExpressionProcessor processor);

    public ExpressionProcessor getExpressionProcessor();
}
