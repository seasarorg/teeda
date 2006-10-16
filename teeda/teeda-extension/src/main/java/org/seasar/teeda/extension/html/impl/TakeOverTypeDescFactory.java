/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.html.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.extension.html.TakeOverTypeDesc;

/**
 * テイクオーバタイプ記述のファクトリです。
 * 
 * @author higa
 *
 */
public class TakeOverTypeDescFactory {

    public static final TakeOverTypeDesc NEVER = new TakeOverTypeNeverDesc();

    public static final TakeOverTypeDesc INCLUDE = new TakeOverTypeIncludeDesc();

    public static final TakeOverTypeDesc EXCLUDE = new TakeOverTypeExcludeDesc();

    private static Map takeOverTypeDescs = new HashMap();

    static {
        addTakeOverTypeDesc(NEVER);
        addTakeOverTypeDesc(INCLUDE);
        addTakeOverTypeDesc(EXCLUDE);
    }

    protected TakeOverTypeDescFactory() {
    }

    public static void addTakeOverTypeDesc(TakeOverTypeDesc takeOverTypeDesc) {
        takeOverTypeDescs.put(takeOverTypeDesc.getName(), takeOverTypeDesc);
    }

    public static boolean existTakeOverTypeDesc(String name) {
        return takeOverTypeDescs.containsKey(name);
    }

    public static TakeOverTypeDesc getTakeOverTypeDesc(String name) {
        if (name == null) {
            return INCLUDE;
        }
        if (!existTakeOverTypeDesc(name)) {
            throw new IllegalArgumentException(name);
        }
        return (TakeOverTypeDesc) takeOverTypeDescs.get(name);
    }
}