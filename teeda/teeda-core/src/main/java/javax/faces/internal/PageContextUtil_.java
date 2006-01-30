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

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.PageContext;

public class PageContextUtil_ {

    private static final int PAGE_CONTEXT_SCOPE = PageContext.REQUEST_SCOPE;

    private PageContextUtil_() {
    }

    public static List getComponentTagStackAttribute(PageContext pageContext) {
        return (List) pageContext.getAttribute(
                WebAppConstants_.COMPONENT_TAG_STACK_ATTR, PAGE_CONTEXT_SCOPE);
    }

    public static void setComponentStackAttribute(PageContext pageContext,
            List list) {
        pageContext.setAttribute(WebAppConstants_.COMPONENT_TAG_STACK_ATTR,
                list, PAGE_CONTEXT_SCOPE);
    }

    public static void removeComponentStackAttribute(PageContext pageContext) {
        pageContext.removeAttribute(WebAppConstants_.COMPONENT_TAG_STACK_ATTR,
                PAGE_CONTEXT_SCOPE);
    }

    public static FacesContext getCurrentFacesContextAttribute(
            PageContext pageContext) {
        return (FacesContext) pageContext
                .getAttribute(WebAppConstants_.CURRENT_FACES_CONTEXT);
    }

    public static void setCurrentFacesContextAttribute(PageContext pageContext,
            FacesContext context) {
        pageContext.setAttribute(WebAppConstants_.CURRENT_FACES_CONTEXT,
                context);
    }

    public static UIComponent getCurrentViewRootAttribute(
            PageContext pageContext) {
        return (UIComponent) pageContext.getAttribute(
                WebAppConstants_.CURRENT_VIEW_ROOT, PAGE_CONTEXT_SCOPE);
    }

    public static void setCurrentViewRootAttribute(PageContext pageContext,
            UIComponent component) {
        pageContext.setAttribute(WebAppConstants_.CURRENT_VIEW_ROOT, component,
                PAGE_CONTEXT_SCOPE);
    }
}
