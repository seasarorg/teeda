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

import javax.faces.convert.Converter;

/**
 * @author shot
 */
public class ConverterResource {

    private static Map converters = new HashMap();

    protected ConverterResource() {
    }

    public static synchronized Converter getConverter(String expression) {
        return (Converter) converters.get(expression);
    }

    public static synchronized void addConverter(String expression,
            Converter converter) {
        converters.put(expression, converter);
    }

    public static synchronized void removeConverter(String expression) {
        converters.remove(expression);
    }

    public static void removeAll() {
        converters.clear();
    }
}
