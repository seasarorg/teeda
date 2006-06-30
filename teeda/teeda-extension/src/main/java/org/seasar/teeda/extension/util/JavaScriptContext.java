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
package org.seasar.teeda.extension.util;

import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.TextUtil;

/**
 * @author shot
 *
 */
public class JavaScriptContext {

    private StringBuffer scripts = new StringBuffer(100);

    public JavaScriptContext() {
    }

    //TODO need escape?
    public void loadScript(String scriptPath) {
        String text = TextUtil.readText(ResourceUtil.getResourcePath(
                scriptPath, "js"));
        scripts.append(text);
    }

    public void clear() {
        scripts = new StringBuffer(100);
    }

    public String getResult() {
        StringBuffer buf = new StringBuffer();
        startScript(buf);
        buf.append(scripts.toString());
        endScript(buf);
        return buf.toString();
    }

    public boolean hasContext() {
        return scripts.length() > 0;
    }

    public String toString() {
        return getResult();
    }

    protected StringBuffer startScript(StringBuffer buf) {
        return buf
                .append("\r\n<script language=\"JavaScript\" type=\"text/javascript\">\r\n<!--\r\n");
    }

    protected StringBuffer endScript(StringBuffer buf) {
        return buf.append("\r\n//-->\r\n</script>\r\n");
    }
}
