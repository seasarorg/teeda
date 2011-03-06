/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.util;

/**
 * @author manhole
 */
public class ForEachContext {

    private static ThreadLocal threadLocal = new ThreadLocal();

    private int depth;

    public static ForEachContext getContext() {
        ForEachContext context = (ForEachContext) threadLocal.get();
        if (context == null) {
            context = new ForEachContext();
            threadLocal.set(context);
        }
        return context;
    }

    public void begin() {
        depth++;
    }

    public void end() {
        depth--;
        if (depth <= 0) {
            clear();
        }
    }

    public void clear() {
        depth = 0;
        threadLocal.set(null);
    }

    public boolean isInForEach() {
        if (0 < depth) {
            return true;
        }
        return false;
    }

}
