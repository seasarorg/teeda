/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import javax.faces.convert.Converter;

import org.seasar.framework.util.ArrayUtil;

/**
 * @author shot
 */
public class ConverterResource {

    private static Map converterPairs = new HashMap();

    private static ConverterBuilder builder;

    protected ConverterResource() {
    }

    public static synchronized Converter getConverter(final String expression) {
        if (builder == null) {
            return null;
        }
        ConverterPair[] pairs = (ConverterPair[]) converterPairs
                .get(expression);
        return builder.build(expression, pairs);
    }

    public static synchronized void addConverter(final String expression,
            final String converterName) {
        addConverter(expression, converterName, new HashMap());
    }

    public static synchronized void addConverter(final String expression,
            final String converterName, final Map properties) {
        ConverterPair pair = new ConverterPair(converterName, properties);
        ConverterPair[] pairs = (ConverterPair[]) converterPairs
                .get(expression);
        if (pairs == null) {
            pairs = new ConverterPair[0];
        }
        converterPairs.put(expression, ArrayUtil.add(pairs, pair));
    }

    public static synchronized void removeConverter(String expression) {
        converterPairs.remove(expression);
        if (builder != null) {
            builder.clearConverter(expression);
        }
    }

    public static void removeAll() {
        converterPairs.clear();
        if (builder != null) {
            builder.clearAll();
        }
    }

    public static void setConverterBuilder(final ConverterBuilder cb) {
        builder = cb;
    }

    public static class ConverterPair {

        public String converterName;

        public Map properties;

        public ConverterPair(final String converterName, final Map properties) {
            this.converterName = converterName;
            this.properties = properties;
        }
    }

}
