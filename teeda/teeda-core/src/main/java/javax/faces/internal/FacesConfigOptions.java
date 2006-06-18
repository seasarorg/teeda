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
package javax.faces.internal;


/**
 * @author higa
 * 
 */
public class FacesConfigOptions {

    private static String configFiles;
    
    private static boolean savingStateInClient = false;
    
    private static String defaultSuffix;
    
    private static String lifecycleId;
    
    private static boolean javascriptAllowed = true;
    
    private static boolean compressState = false;
    
    protected FacesConfigOptions() {
    }
    
    public static boolean isSavingStateInClient() {
        return savingStateInClient;
    }
    
    public static void setSavingStateInClient(boolean inClient) {
        savingStateInClient = inClient;
    }

    public static String getConfigFiles() {
        return configFiles;
    }

    public static void setConfigFiles(String files) {
        configFiles = files;
    }

    public static String getDefaultSuffix() {
        return defaultSuffix;
    }

    public static void setDefaultSuffix(String suffix) {
        defaultSuffix = suffix;
    }
    
    public static String getLifecycleId() {
        return lifecycleId;
    }

    public static void setLifecycleId(String id) {
        lifecycleId = id;
    }

    public static boolean isJavascriptAllowed() {
        return javascriptAllowed;
    }

    public static void setJavascriptAllowed(boolean isJavaScriptAllowed) {
        javascriptAllowed = isJavaScriptAllowed;
    }

    public static boolean getCompressState() {
        return compressState;
    }

    public static void setCompressState(boolean isCompress) {
        compressState = isCompress;
    }
    
    public static void clear() {
        configFiles = null;
        savingStateInClient = false;
        defaultSuffix = null;
        lifecycleId = null;
        javascriptAllowed = true;
        compressState = false;
    }

}