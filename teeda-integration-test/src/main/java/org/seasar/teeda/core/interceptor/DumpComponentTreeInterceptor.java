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

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;

/**
 * @author manhole
 */
public class DumpComponentTreeInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 1L;

    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("DumpComponentTreeInterceptor.invoke() before");
        dumpTree();
        try {
            Object result = invocation.proceed();
            return result;
        } finally {
            System.out.println("DumpComponentTreeInterceptor.invoke() after");
            dumpTree();
        }
    }

    private void dumpTree() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = context.getViewRoot();
        System.out.println("viewRoot=" + viewRoot + " ,viewId="
            + viewRoot.getViewId());
        System.out.println("[tree] " + viewRoot);
        dumpTree(viewRoot, 1);
    }

    private void dumpTree(UIComponent component, int level) {
        for (Iterator it = component.getFacetsAndChildren(); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();
            System.out.print("[tree] ");
            for (int i = 0; i < level; i++) {
                System.out.print("  ");
            }
            System.out.print(child);
            System.out.println("");
            dumpTree(child, level + 1);
        }
    }

}
