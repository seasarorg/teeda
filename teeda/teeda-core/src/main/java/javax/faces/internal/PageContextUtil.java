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

import java.util.List;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.PageContext;

/**
 * @author shot
 *
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class PageContextUtil {

    private PageContextUtil() {
    }

    public static List getComponentTagStackAttribute(PageContext pageContext) {
        return (List) pageContext.getAttribute(
                InternalConstants.COMPONENT_TAG_STACK_ATTR, getScope());
    }

    public static void setComponentStackAttribute(PageContext pageContext,
            List list) {
        pageContext.setAttribute(InternalConstants.COMPONENT_TAG_STACK_ATTR,
                list, getScope());
    }

    public static void removeComponentStackAttribute(PageContext pageContext) {
        pageContext.removeAttribute(InternalConstants.COMPONENT_TAG_STACK_ATTR,
                getScope());
    }

    private static int getScope() {
        String defaultSuffix = FacesConfigOptions.getDefaultSuffix();
        return (defaultSuffix.indexOf(".htm") >= 0) ? PageContext.PAGE_SCOPE
                : PageContext.REQUEST_SCOPE;
    }

    public static FacesContext getCurrentFacesContextAttribute(
            PageContext pageContext) {
        return (FacesContext) pageContext
                .getAttribute(InternalConstants.CURRENT_FACES_CONTEXT);
    }

    public static void setCurrentFacesContextAttribute(PageContext pageContext,
            FacesContext context) {
        pageContext.setAttribute(InternalConstants.CURRENT_FACES_CONTEXT,
                context);
    }

    public static UIViewRoot getCurrentViewRootAttribute(PageContext pageContext) {
        return (UIViewRoot) pageContext
                .getAttribute(InternalConstants.CURRENT_VIEW_ROOT);
    }

    public static void setCurrentViewRootAttribute(PageContext pageContext,
            UIViewRoot component) {
        pageContext
                .setAttribute(InternalConstants.CURRENT_VIEW_ROOT, component);
    }

    public static String getCharacterEncoding(PageContext pageContext) {
        return pageContext.getRequest().getCharacterEncoding();
    }
}
