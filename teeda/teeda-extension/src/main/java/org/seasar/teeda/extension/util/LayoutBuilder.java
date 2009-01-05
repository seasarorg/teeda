/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesConfigOptions;
import javax.faces.internal.UIComponentUtil;
import javax.servlet.ServletContext;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.ServletContextUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.TInclude;
import org.seasar.teeda.extension.component.TIncludeChildBody;
import org.seasar.teeda.extension.component.TViewRoot;
import org.seasar.teeda.extension.component.UIBody;
import org.seasar.teeda.extension.component.UITitle;
import org.seasar.teeda.extension.component.html.THtmlHead;
import org.seasar.teeda.extension.component.html.THtmlLink;
import org.seasar.teeda.extension.component.html.THtmlScript;
import org.seasar.teeda.extension.component.html.THtmlStyle;
import org.seasar.teeda.extension.helper.PathHelper;
import org.seasar.teeda.extension.html.HtmlDescCache;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.PageDescCache;
import org.seasar.teeda.extension.render.IncludedBody;

/**
 * @author koichik
 * 
 */
public class LayoutBuilder {

    public static final String LIST_KEY = LayoutBuilder.class.getName();

    public static final String POP_INDEX_KEY = LIST_KEY + ".INDEX";

    private static final Logger logger = Logger.getLogger(LayoutBuilder.class);

    private ServletContext servletContext;

    private ViewHandler viewHandler;

    private PathHelper pathHelper;

    private HtmlDescCache htmlDescCache;

    private PageDescCache pageDescCache;

    public static List getIncludedBodies(FacesContext context) {
        Map requestMap = context.getExternalContext().getRequestMap();
        return (List) requestMap.get(LIST_KEY);
    }

    public void layout(final FacesContext context, final TViewRoot component) {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        TViewRoot child = component;
        TViewRoot parent = getParentViewRoot(context, child);
        UIComponent title = null;
        List styleList = new ArrayList();
        List scriptList = new ArrayList();
        List linkList = new ArrayList();
        while (parent != null) {
            final UIComponent body = UIComponentUtil.findDescendant(child,
                    UIBody.class);
            if (body == null) {
                logger.log("WTDA0202", new Object[] { child.getViewId() });
            }
            final UIComponent head = UIComponentUtil.findDescendant(component,
                    THtmlHead.class);
            if (head != null) {
                title = UIComponentUtil.findDescendant(head, UITitle.class);
                styleList = UIComponentUtil.collectDescendants(head,
                        THtmlStyle.class);
                scriptList = UIComponentUtil.collectDescendants(head,
                        THtmlScript.class);
                linkList = UIComponentUtil.collectDescendants(head,
                        THtmlLink.class);
            }
            if (body != null) {
                final List children = body.getChildren();
                final IncludedBody includedBody = new IncludedBody(child
                        .getViewId(), children);
                pushIncludedBody(context, includedBody);
            }
            child = parent;
            parent = getParentViewRoot(context, child);
        }
        if (child != component) {
            component.setRootViewId(child.getViewId());
            component.getChildren().clear();
            component.getChildren().addAll(child.getChildren());
            final UIComponent head = UIComponentUtil.findDescendant(component,
                    THtmlHead.class);
            if (styleList.size() > 0) {
                head.getChildren().addAll(styleList);
            }
            if (scriptList.size() > 0) {
                head.getChildren().addAll(scriptList);
            }
            if (linkList.size() > 0) {
                head.getChildren().addAll(linkList);
            }
            if (title != null) {
                replaceComponent(head, title);
            }
        } else {
            component.setRootViewId(component.getViewId());
        }
    }

    public void processInclude(final FacesContext context,
            final UIComponent component) {
        if (component instanceof TInclude) {
            include(context, ((TInclude) component));
        } else if (component instanceof TIncludeChildBody) {
            include(context, ((TIncludeChildBody) component));
        }
        for (final Iterator it = component.getChildren().iterator(); it
                .hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            processInclude(context, child);
        }
    }

    protected TViewRoot getParentViewRoot(final FacesContext context,
            final TViewRoot component) {
        final String parentViewId = getParentViewId(context, component);
        if (parentViewId == null) {
            return null;
        }
        if (HotdeployUtil.isHotdeploy() ||
                htmlDescCache.getHtmlDesc(parentViewId) == null) {
            final InputStream is = ServletContextUtil.getResourceAsStream(
                    servletContext, parentViewId);
            if (is != null) {
                InputStreamUtil.close(is);
            } else {
                return null;
            }
        }
        UIViewRoot viewRoot = viewHandler.restoreView(context, parentViewId);
        if (viewRoot == null) {
            viewRoot = viewHandler.createView(context, parentViewId);
        }
        return (TViewRoot) viewRoot;
    }

    protected String getParentViewId(final FacesContext context,
            final TViewRoot component) {
        final String defaultSuffix = FacesConfigOptions.getDefaultSuffix();
        if (defaultSuffix.indexOf(ViewHandler.DEFAULT_SUFFIX) >= 0) {
            return null;
        }
        String parentPath = FacesConfigOptions.getDefaultLayoutPath();
        final int i = parentPath.lastIndexOf("/");
        final String dirPath = (parentPath.length() > (i + 1)) ? parentPath
                .substring(0, i + 1) : parentPath;
        final UIViewRoot viewRoot = context.getViewRoot();
        if (component != viewRoot || viewRoot.getViewId().indexOf(dirPath) >= 0) {
            parentPath = null;
        }
        final PageDesc pageDesc = pageDescCache.getPageDesc(component
                .getViewId());
        if (pageDesc != null &&
                pageDesc.hasProperty(ExtensionConstants.LAYOUT_ATTR)) {
            final Object page = DIContainerUtil.getComponent(pageDesc
                    .getPageName());
            final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(page
                    .getClass());
            final PropertyDesc propDesc = beanDesc
                    .getPropertyDesc(ExtensionConstants.LAYOUT_ATTR);
            if (propDesc.isReadable()) {
                parentPath = (String) propDesc.getValue(page);
            }
        }
        if (parentPath == null) {
            return null;
        }
        return pathHelper.fromViewRootRelativePathToViewId(parentPath);
    }

    protected static void pushIncludedBody(final FacesContext context,
            final IncludedBody includedBody) {
        final Map requestMap = context.getExternalContext().getRequestMap();
        List list = (List) requestMap.get(LIST_KEY);
        if (list == null) {
            list = new ArrayList();
            requestMap.put(LIST_KEY, list);
        }
        requestMap.put(POP_INDEX_KEY, new Integer(list.size()));
        list.add(includedBody);
    }

    public static IncludedBody popIncludedBody(final FacesContext context) {
        final Map requestMap = context.getExternalContext().getRequestMap();
        final List list = (List) requestMap.get(LIST_KEY);
        if (list == null) {
            return null;
        }
        final Integer popIndex = (Integer) requestMap.get(POP_INDEX_KEY);
        if (popIndex == null) {
            return null;
        }
        final int index = (popIndex).intValue();
        final IncludedBody body = (IncludedBody) list.get(index);
        requestMap.put(POP_INDEX_KEY, new Integer(index - 1));
        return body;
    }

    protected boolean replaceComponent(final UIComponent root,
            final UIComponent titleCandidate) {
        for (int i = 0; i < root.getChildCount(); i++) {
            final UIComponent child = (UIComponent) root.getChildren().get(i);
            final boolean b = replaceComponent(child, titleCandidate);
            if (b) {
                return true;
            }
            if (child instanceof UITitle) {
                root.getChildren().remove(i);
                root.getChildren().add(i, titleCandidate);
            }
        }
        return false;
    }

    protected void include(FacesContext context, TIncludeChildBody component) {
        IncludedBody includedBody = popIncludedBody(context);
        if (includedBody == null) {
            return;
        }
        component.getChildren().addAll(includedBody.getComponentList());
        component.setIncludedViewId(includedBody.getViewId());
    }

    protected void include(FacesContext context, TInclude component) {
        String src = (component).getSrc();
        if (StringUtil.isEmpty(src)) {
            return;
        }
        String srcViewId = pathHelper.fromViewRootRelativePathToViewId(src);
        UIViewRoot viewRoot = viewHandler.restoreView(context, srcViewId);
        if (viewRoot == null) {
            viewRoot = viewHandler.createView(context, srcViewId);
        }
        if (viewRoot == null) {
            return;
        }
        UIComponent body = UIComponentUtil.findDescendant(viewRoot,
                UIBody.class);
        if (body == null) {
            logger.log("WTDA0202", new Object[] { viewRoot.getViewId() });
            return;
        }
        IncludedBody includedBody = new IncludedBody(viewRoot.getViewId(), body
                .getChildren());
        component.getChildren().addAll(includedBody.getComponentList());
        component.setIncludedViewId(includedBody.getViewId());
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void setViewHandler(ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
    }

    public void setPathHelper(PathHelper pathHelper) {
        this.pathHelper = pathHelper;
    }

    public void setHtmlDescCache(HtmlDescCache htmlDescCache) {
        this.htmlDescCache = htmlDescCache;
    }

    public void setPageDescCache(PageDescCache pageDescCache) {
        this.pageDescCache = pageDescCache;
    }

}
