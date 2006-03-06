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
package org.seasar.teeda.core.interceptor;

import java.io.PrintWriter;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.SPrintWriter;

/**
 * @author manhole
 */
public class DumpComponentTreeInterceptor extends AbstractInterceptor {

    private static final Logger logger_ = Logger
            .getLogger(DumpComponentTreeInterceptor.class);

    private static final long serialVersionUID = 1L;

    public Object invoke(MethodInvocation invocation) throws Throwable {
        FacesContext context = FacesContext.getCurrentInstance();
        printBefore(context);
        try {
            return invocation.proceed();
        } finally {
            printAfter(context);
        }
    }

    private void printAfter(FacesContext context) {
        SPrintWriter writer = new SPrintWriter();
        writer.println(getClass().getName() + " after");
        dumpTree(writer, context);
        logger_.debug(writer.toString());
    }

    private void printBefore(FacesContext context) {
        SPrintWriter writer = new SPrintWriter();
        writer.println(getClass().getName() + " before");
        dumpTree(writer, context);
        logger_.debug(writer.toString());
    }

    void dumpTree(PrintWriter writer, FacesContext context) {
        UIViewRoot viewRoot = context.getViewRoot();
        writer.println("viewId=" + viewRoot.getViewId());
        writer.println("[tree] " + viewRoot);
        dumpTree(writer, viewRoot, 1);
    }

    void dumpTree(PrintWriter writer, UIComponent component, int depth) {
        for (Iterator it = component.getFacetsAndChildren(); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();
            writer.print("[tree] ");
            for (int i = 0; i < depth; i++) {
                writer.print("  ");
            }
            writer.print(child);
            writer.println("");
            dumpTree(writer, child, depth + 1);
        }
    }

}
