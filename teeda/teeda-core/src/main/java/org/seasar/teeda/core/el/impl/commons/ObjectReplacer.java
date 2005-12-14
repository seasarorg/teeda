package org.seasar.teeda.core.el.impl.commons;

import org.apache.commons.el.Expression;
import org.apache.commons.el.ExpressionString;
import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.Replacer;


public class ObjectReplacer implements Replacer{

    private ExpressionProcessor processor_;
    public ObjectReplacer(ExpressionProcessor processor){
        processor_ = processor;
    }
    
    public void replace(Object expression) {
        if(!(expression instanceof Expression || expression instanceof ExpressionString)){
            throw new IllegalStateException();
        }
        processor_.processExpression(expression, expression.getClass());
    }
    
}