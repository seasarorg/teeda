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
package org.seasar.teeda.core.el.impl.commons;

import javax.faces.el.EvaluationException;
import javax.servlet.jsp.el.ELException;

import org.apache.commons.el.Coercions;
import org.apache.commons.el.Logger;

/**
 * @author shot
 */
public class CoercionsUtil {

    private static final org.seasar.framework.log.Logger logger_ = org.seasar.framework.log.Logger
            .getLogger(CoercionsUtil.class);

    private CoercionsUtil() {
    }

    public static Integer coerceToInteger(Object index) {
        return coerceToInteger(index, CommonsElLogger.getLogger());
    }

    public static Integer coerceToInteger(Object index, Logger logger) {
        try {
            return Coercions.coerceToInteger(index, logger);
        } catch (ELException e) {
            logger_.error(e + " occured at " + CoercionsUtil.class);
            return null;
        }
    }

    public static boolean coerceToPrimitiveBoolean(Object value)
            throws EvaluationException {
        return coerceToPrimitiveBoolean(value, CommonsElLogger.getLogger());
    }

    public static boolean coerceToPrimitiveBoolean(Object value, Logger logger)
            throws EvaluationException {
        try {
            return Coercions.coerceToBoolean(value, logger).booleanValue();
        } catch (ELException e) {
            throw new EvaluationException(e);
        }
    }

    public static Object coerce(Object newValue, Class type)
            throws EvaluationException {
        try {
            return Coercions
                    .coerce(newValue, type, CommonsElLogger.getLogger());
        } catch (ELException e) {
            throw new EvaluationException(e);
        }
    }

}
