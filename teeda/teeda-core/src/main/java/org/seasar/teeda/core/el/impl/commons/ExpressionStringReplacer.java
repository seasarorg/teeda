package org.seasar.teeda.core.el.impl.commons;

import org.apache.commons.el.ExpressionString;
import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.Replacer;


public class ExpressionStringReplacer implements Replacer {

    private ExpressionProcessor processor_;
    public ExpressionStringReplacer(ExpressionProcessor processor){
        processor_ = processor;
    }

    public void replace(Object expression) {
        ExpressionString expressionString = (ExpressionString)expression;
        Object[] expressions = (Object[])expressionString.getElements();
        for (int i = 0; i < expressions.length;i++){
            Object e = expressions[i];
            processor_.processExpression(e, e.getClass());
        }
    }

}
