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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.PropertyNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.MetaDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.servlet.S2ContainerServlet;
import org.seasar.teeda.ajax.translator.AjaxTranslator;

/**
 * @author yone
 * @author shot
 */
public class AjaxServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private S2Container container;

    /**
     * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {
        container = S2ContainerServlet.getContainer();
        System.setErr(System.out);
    }

    /**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doAjax(request, response);
    }

    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doAjax(request, response);
    }

    protected void doAjax(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String componentName = request
                .getParameter(AjaxConstants.REQ_PARAM_COMPONENT);
        String method = request.getParameter(AjaxConstants.REQ_PARAM_ACTION);

        ComponentDef def = getComponentDefNoException(componentName);
        if (def == null) {
            throw new ServletException("Ajax Component Name[" + componentName
                    + "] is not found.");
        }

        MetaDef meta = def.getMetaDef(AjaxConstants.TEEDA_AJAX_META);
        if (meta == null) {
            throw new ServletException("Ajax Component Name[" + componentName
                    + "] is not public.");
        }
        Object obj = def.getComponent();
        if (obj == null) {
            throw new ServletException("Ajax Component Name[" + componentName
                    + "] not found.");
        }

        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(def.getConcreteClass());
        if (method == null) {
            method = AjaxConstants.DEFAULT_AJAX_METHOD;
        }

        if (!beanDesc.hasMethod(method)) {
            throw new ServletException("Ajax Component Name[" + componentName
                    + "] does not has method[" + method + "]");
        }

        this.setRequestParameter(request, obj);

        Object target = null;
        try {
            target = beanDesc.invoke(obj, method, null);
        } catch (Exception e) {
            throw new ServletException(
                    "The error occurred while create Ajax response.");
        }
        String result = AjaxTranslator.translate(target);
        AjaxTranslator.setContentType(response, result);
        
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter pw = response.getWriter();
        pw.write(result);
        pw.close();
    }

    protected void setRequestParameter(HttpServletRequest request, Object obj) {
        Enumeration enume = request.getParameterNames();
        while (enume.hasMoreElements()) {
            String key = (String) enume.nextElement();
            String value = request.getParameter(key);
            this.setPropertyNoException(obj, key, value);
        }
    }

    protected void setPropertyNoException(Object target, String propertyName,
            String value) {
        try {
            BeanDesc beanDesc = BeanDescFactory.getBeanDesc(target.getClass());
            PropertyDesc propertyDesc = beanDesc.getPropertyDesc(propertyName);
            propertyDesc.setValue(target, value);
        } catch (PropertyNotFoundRuntimeException e) {
        }
    }

    protected ComponentDef getComponentDefNoException(String componentName) {
        ComponentDef def = null;
        try {
            def = container.getComponentDef(componentName);
        } catch (ComponentNotFoundRuntimeException e) {
        }
        return def;
    }

}