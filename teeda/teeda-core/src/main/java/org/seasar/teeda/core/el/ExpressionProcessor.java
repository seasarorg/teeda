/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.el;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ReferenceSyntaxException;

/**
 * @author shot
 */
public interface ExpressionProcessor extends Serializable {

    public void processExpression(Object o, Class type);

    public Object evaluate(FacesContext context, Object expression)
            throws EvaluationException;

    public Integer toIndex(Object base, Object index)
            throws ReferenceSyntaxException;

    public Object resolveBase(FacesContext context, Object expression)
            throws PropertyNotFoundException;

    public Object getCoercedObject(Object newValue, Class type)
            throws EvaluationException;
}