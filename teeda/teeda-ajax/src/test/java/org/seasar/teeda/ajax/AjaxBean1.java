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
package org.seasar.teeda.ajax;

import javax.servlet.http.HttpServletResponse;

/**
 * @author yone
 */
public class AjaxBean1 {
    private String arg1;

    private int arg2;

    private HttpServletResponse response;

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public int getArg2() {
        return arg2;
    }

    public void setArg2(int arg2) {
        this.arg2 = arg2;
    }

    public String ajaxText() {
        this.response.setContentType(AjaxConstants.CONTENT_TYPE_TEXT);
        return "ajaxOrg";
    }

    public String ajaxJson() {
        return null;
    }

    public String ajaxXml() {
        this.response.setContentType(AjaxConstants.CONTENT_TYPE_XML);
        return null;
    }

    public String ajaxHoge() {
        return "call default ajaxAction";
    }

}