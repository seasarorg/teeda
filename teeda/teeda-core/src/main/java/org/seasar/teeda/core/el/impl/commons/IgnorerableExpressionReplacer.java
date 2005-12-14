package org.seasar.teeda.core.el.impl.commons;

import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.Replacer;


public class IgnorerableExpressionReplacer implements Replacer {

    private ExpressionProcessor processor_;
    public IgnorerableExpressionReplacer(ExpressionProcessor processor){
        processor_ = processor;
    }

    public void replace(Object expression) {
        processor_.processExpression(expression, expression.getClass());
    }

}
