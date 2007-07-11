/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package javax.faces.internal;

import java.util.HashMap;
import java.util.Map;

import javax.faces.validator.Validator;

import org.seasar.framework.util.ArrayUtil;

/**
 * @author shot
 * @author higa
 */
public class ValidatorResource {

    private static Map validators_ = new HashMap();

    private static ValidatorBuilder builder = new NormalValidatorBuilderImpl();

    protected ValidatorResource() {
    }

    public static synchronized Validator getValidator(final String expression) {
        ValidatorPair[] pairs = (ValidatorPair[]) validators_.get(expression);
        return builder.build(expression, pairs);
    }

    public static synchronized void addValidator(final String expression,
            final String validatorName) {
        addValidator(expression, validatorName, new HashMap());
    }

    public static synchronized void addValidator(final String expression,
            final String validatorName, final Map properties) {
        ValidatorPair pair = new ValidatorPair(validatorName, properties);
        ValidatorPair[] pairs = (ValidatorPair[]) validators_.get(expression);
        if (pairs == null) {
            pairs = new ValidatorPair[0];
        }
        validators_.put(expression, ArrayUtil.add(pairs, pair));
    }

    public static synchronized void removeValidator(String expression) {
        validators_.remove(expression);
        if (builder != null) {
            builder.clearValidator(expression);
        }
    }

    public static void removeAll() {
        validators_.clear();
        if (builder != null) {
            builder.clearAll();
        }
    }

    public static void setValidatorBuilder(ValidatorBuilder vb) {
        builder = vb;
    }

    public static class ValidatorPair {

        public String validatorName;

        public Map properties;

        public ValidatorPair(final String validatorName, final Map properties) {
            this.validatorName = validatorName;
            this.properties = properties;
        }
    }

}
