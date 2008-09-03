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
package javax.faces.internal;

/**
 * @author higa
 * @author shot
 */
public abstract class FacesConfigOptions {

    private static String configFiles;

    private static boolean savingStateInClient = false;

    private static final String DEFAULT_DEFAULT_SUFFIX = ".jsp";

    private static String defaultSuffix = DEFAULT_DEFAULT_SUFFIX;

    private static String lifecycleId;

    private static String[] javascriptNotPermittedPath;

    private static boolean compressState = false;

    private static boolean defaultGridAsync = true;

    private static int gridFirstRenderRowCount = 50;

    private static final String DEFAULT_LAYOUT_PATH = "/layout/layout.html";

    private static String defaultLayoutPath = DEFAULT_LAYOUT_PATH;

    private static String redirectUrl = null;

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

    public static String[] getJavascriptNotPermittedPath() {
        return javascriptNotPermittedPath;
    }

    public static void setJavascriptNotPermittedPath(String[] notAllowedPath) {
        javascriptNotPermittedPath = notAllowedPath;
    }

    public static boolean getCompressState() {
        return compressState;
    }

    public static void setCompressState(boolean isCompress) {
        compressState = isCompress;
    }

    public static boolean isDefaultGridAsync() {
        return defaultGridAsync;
    }

    public static void setDefaultGridAsync(boolean defaultGridAsync) {
        FacesConfigOptions.defaultGridAsync = defaultGridAsync;
    }

    public static int getGridFirstRenderRowCount() {
        return gridFirstRenderRowCount;
    }

    public static void setGridFirstRenderRowCount(int gridFirstRenderRowCount) {
        FacesConfigOptions.gridFirstRenderRowCount = gridFirstRenderRowCount;
    }

    public static String getDefaultLayoutPath() {
        return defaultLayoutPath;
    }

    public static void setDefaultLayoutPath(String defaultLayoutPath) {
        FacesConfigOptions.defaultLayoutPath = defaultLayoutPath;
    }

    public static String getRedirectUrl() {
        return redirectUrl;
    }

    public static void setRedirectUrl(String redirectUrl) {
        FacesConfigOptions.redirectUrl = redirectUrl;
    }

    public static void clear() {
        configFiles = null;
        savingStateInClient = false;
        defaultSuffix = DEFAULT_DEFAULT_SUFFIX;
        lifecycleId = null;
        javascriptNotPermittedPath = null;
        compressState = false;
        defaultGridAsync = true;
        gridFirstRenderRowCount = 50;
        defaultLayoutPath = DEFAULT_LAYOUT_PATH;
        redirectUrl = null;
    }

}