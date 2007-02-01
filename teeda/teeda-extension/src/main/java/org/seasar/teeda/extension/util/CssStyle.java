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
package org.seasar.teeda.extension.util;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author higa
 * 
 */
public class CssStyle {

    private StringBuffer buf = new StringBuffer(1024);

    public void startStyle() {
        buf.append("<style>").append(JsfConstants.LINE_SP);
    }

    public void endStyle() {
        buf.append("</style>").append(JsfConstants.LINE_SP);
    }

    public void startSelector(String pattern) {
        buf.append(pattern).append(" {");
        buf.append(JsfConstants.LINE_SP);
    }

    public void endSelector() {
        buf.append("}").append(JsfConstants.LINE_SP);
    }

    public void addProperty(String name, String value) {
        buf.append("  ").append(name).append(" : ");
        buf.append(value).append(";").append(JsfConstants.LINE_SP);
    }

    public String getString() {
        return buf.toString();
    }
}