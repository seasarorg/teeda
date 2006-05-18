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
package org.seasar.teeda.core.application;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.StateManager.SerializedView;

/**
 * @author higa
 * 
 */
public class SerializedViewCache {

    private static Map cache = new HashMap();
    
    protected SerializedViewCache() {
    }
    
    public static SerializedView getSerializedView(String viewId) {
        return (SerializedView) cache.get(viewId);
    }
    
    public static void saveSerializedView(String viewId, SerializedView view) {
        cache.put(viewId, view);
    }
    
    public static boolean hasSerializedView(String viewId) {
        return cache.containsKey(viewId);
    }
    
    public static void removeSerializedView(String viewId) {
        cache.remove(viewId);
    }
    
    public static void removeAll() {
        cache.clear();
    }
}
