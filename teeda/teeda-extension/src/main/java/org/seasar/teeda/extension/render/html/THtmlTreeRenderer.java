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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIParameter;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.Cookie;

import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.ScriptEnhanceUIViewRoot;
import org.seasar.teeda.extension.component.helper.TreeNode;
import org.seasar.teeda.extension.component.helper.TreeState;
import org.seasar.teeda.extension.component.helper.TreeWalker;
import org.seasar.teeda.extension.component.html.THtmlTree;
import org.seasar.teeda.extension.event.ToggleEvent;
import org.seasar.teeda.extension.util.JavaScriptContext;

public class THtmlTreeRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Tree";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.THtmlTree";

    protected static final String TOGGLE_SPAN = "teeda.extension.tree.TOGGLE_SPAN";

    protected static final String ROOT_NODE_ID = "0";

    private static final String NAV_COMMAND = "teeda.extension.tree.NAV_COMMAND";

    private static final String ENCODING = "UTF-8";

    private static final String ATTRIB_DELIM = ";";

    private static final String ATTRIB_KEYVAL = "=";

    private static final String NODE_STATE_EXPANDED = "x";

    private static final String NODE_STATE_CLOSED = "c";

    private static final String IMAGE_PREFIX = "t2";

    private static final String TOGGLE_ID = "t2g";

    private static final int NOTHING = 0;

    private static final int CHILDREN = 1;

    private static final int EXPANDED = 2;

    private static final int LINES = 4;

    private static final int LAST = 8;

    private static final String NAMESPACE = "Teeda.THtmlTree.";

    public boolean getRendersChildren() {
        return true;
    }

    private void restoreStateFromCookies(FacesContext context,
            UIComponent component) {
        THtmlTree tree = (THtmlTree) component;
        TreeState state = tree.getDataModel().getTreeState();
        Map cookieMap = context.getExternalContext().getRequestCookieMap();
        Cookie treeCookie = (Cookie) cookieMap.get(component.getId());
        if (treeCookie == null || treeCookie.getValue() == null) {
            return;
        }
        Map attrMap = getCookieAttr(treeCookie);
        for (Iterator i = attrMap.keySet().iterator(); i.hasNext();) {
            String nodeId = (String) i.next();
            String nodeState = (String) attrMap.get(nodeId);
            if (NODE_STATE_EXPANDED.equals(nodeState)) {
                if (!state.isNodeExpanded(nodeId)) {
                    state.toggleExpanded(nodeId);
                }
            } else if (NODE_STATE_CLOSED.equals(nodeState)) {
                if (state.isNodeExpanded(nodeId)) {
                    state.toggleExpanded(nodeId);
                }
            }
        }
    }

    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
        // see if one of the nav nodes was clicked, if so, then toggle appropriate node
        String nodeId = null;
        THtmlTree tree = (THtmlTree) component;
        if (tree.isClientSideToggle() && tree.isPreserveToggle()) {
            restoreStateFromCookies(context, component);
        } else {
            nodeId = (String) context.getExternalContext()
                    .getRequestParameterMap().get(
                            tree.getId() + ExtensionConstants.NAME_SEPARATOR
                                    + NAV_COMMAND);

            if (nodeId == null || nodeId.equals("")) {
                return;
            }
            component.queueEvent(new ToggleEvent(component, nodeId));
        }
    }

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        final ScriptEnhanceUIViewRoot sRoot = (ScriptEnhanceUIViewRoot) context
                .getViewRoot();
        final ResponseWriter writer = context.getResponseWriter();
        final String scriptKey = getScriptKey();
        if (!sRoot.containsScript(scriptKey)) {
            JavaScriptContext scriptContext = new JavaScriptContext();
            scriptContext.loadScript(scriptKey);
            sRoot.addScript(scriptKey, scriptContext);
            writer.write(sRoot.getAllScripts());
        }
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        THtmlTree tree = (THtmlTree) component;
        if (!component.isRendered()) {
            return;
        }
        if (tree.getValue() == null) {
            return;
        }
        final ResponseWriter writer = context.getResponseWriter();
        final String id = component.getId();
        boolean isOuterSpanUsed = false;
        if (id != null && !id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            final String clientId = component.getClientId(context);
            isOuterSpanUsed = true;
            writer.startElement(JsfConstants.SPAN_ELEM, component);
            writer.writeAttribute(JsfConstants.ID_ATTR, clientId, "id");
        }
        boolean clientSideToggle = tree.isClientSideToggle();
        boolean showRootNode = tree.isShowRootNode();
        TreeState state = tree.getDataModel().getTreeState();
        TreeWalker walker = tree.getDataModel().getTreeWalker();
        walker.reset();
        walker.setTree(tree);
        walker.setCheckState(!clientSideToggle); // walk all nodes in client mode
        if (showRootNode) {
            // encode the tree (starting with the root node)
            if (walker.next()) {
                encodeTree(context, writer, tree, walker);
            }
        } else {
            // skip the root node
            walker.next();
            TreeNode rootNode = tree.getNode();

            // now mark the root as expanded (so we don't stop there)
            String rootNodeId = tree.getNodeId();
            if (!state.isNodeExpanded(rootNodeId)) {
                state.toggleExpanded(rootNodeId);
            }

            // now encode each of the nodes in the level immediately below the root
            for (int i = 0; i < rootNode.getChildCount(); i++) {
                if (walker.next()) {
                    encodeTree(context, writer, tree, walker);
                }
            }
        }

        // reset the current node id once we're done encoding everything
        tree.setNodeId(null);

        if (isOuterSpanUsed) {
            writer.endElement("span");
        }
    }

    protected void encodeTree(FacesContext context, ResponseWriter out,
            THtmlTree tree, TreeWalker walker) throws IOException {
        boolean clientSideToggle = tree.isClientSideToggle();

        // encode the current node
        beforeNodeEncode(context, out, tree);
        encodeCurrentNode(context, out, tree);
        afterNodeEncode(context, out);

        // if client side toggling is on, add a span to be used for displaying/hiding children
        if (clientSideToggle) {
            String spanId = TOGGLE_SPAN + ":" + tree.getId() + ":"
                    + tree.getNodeId();
            out.startElement(JsfConstants.SPAN_ELEM, tree);
            out.writeAttribute(JsfConstants.ID_ATTR, spanId, null);

            if (tree.isNodeExpanded()) {
                RendererUtil.renderAttribute(out, JsfConstants.STYLE_ATTR,
                        "display:block");
            } else {
                RendererUtil.renderAttribute(out, JsfConstants.STYLE_ATTR,
                        "display:none");
            }
        }
        TreeNode node = tree.getNode();
        for (int i = 0; i < node.getChildCount(); i++) {
            if (walker.next()) {
                encodeTree(context, out, tree, walker);
            }
        }
        if (clientSideToggle) {
            out.endElement(JsfConstants.SPAN_ELEM);
        }
    }

    protected void encodeCurrentNode(FacesContext context, ResponseWriter out,
            THtmlTree tree) throws IOException {
        TreeNode node = tree.getNode();

        // set configurable values
        boolean showRootNode = tree.isShowRootNode();
        boolean showNav = tree.isShowNav();
        boolean showLines = tree.isShowLines();
        boolean clientSideToggle = tree.isClientSideToggle();

        if (clientSideToggle) {
            // we must show the nav icons if client side toggle is enabled (regardless of what user says)
            showNav = true;
        }

        UIComponent nodeTypeFacet = tree.getFacet(node.getType());
        UIComponent nodeImgFacet = null;

        if (nodeTypeFacet == null) {
            throw new IllegalArgumentException(
                    "Unable to locate facet with the name: " + node.getType());
        }

        // render node padding
        String[] pathInfo = tree.getPathInformation(tree.getNodeId());
        int paddingLevel = pathInfo.length - 1;

        for (int i = (showRootNode ? 0 : 1); i < paddingLevel; i++) {
            boolean lastChild = tree.isLastChild((String) pathInfo[i]);
            String lineSrc = (!lastChild && showLines) ? getGifImageSrc("line-trunk.gif")
                    : getGifImageSrc("spacer.gif");
            out.startElement(JsfConstants.TD_ELEM, tree);
            out.writeAttribute(JsfConstants.WIDTH_ATTR, "19", null);
            out.writeAttribute(JsfConstants.HEIGHT_ATTR, "100%", null);
            out.writeURIAttribute("background", lineSrc, null);
            out.startElement(JsfConstants.IMG_ELEM, tree);
            out.writeURIAttribute(JsfConstants.SRC_ATTR, lineSrc, null);
            out.writeAttribute(JsfConstants.WIDTH_ATTR, "19", null);
            out.writeAttribute(JsfConstants.HEIGHT_ATTR, "18", null);
            out.writeAttribute(JsfConstants.BORDER_ATTR, "0", null);
            out.endElement(JsfConstants.IMG_ELEM);
            out.endElement(JsfConstants.TD_ELEM);
        }

        if (showNav) {
            nodeImgFacet = encodeNavigation(context, out, tree);
        }

        // render node
        out.startElement(JsfConstants.TD_ELEM, tree);
        if (nodeImgFacet != null) {
            renderChild(context, nodeImgFacet);
        }
        renderChild(context, nodeTypeFacet);
        out.endElement(JsfConstants.TD_ELEM);
    }

    protected void beforeNodeEncode(FacesContext context, ResponseWriter out,
            THtmlTree tree) throws IOException {
        out.startElement(JsfConstants.TABLE_ELEM, tree);
        out.writeAttribute(JsfConstants.CELLPADDING_ATTR, "0", null);
        out.writeAttribute(JsfConstants.CELLSPACING_ATTR, "0", null);
        out.writeAttribute(JsfConstants.BORDER_ATTR, "0", null);
        out.startElement(JsfConstants.TR_ELEM, tree);
    }

    protected void afterNodeEncode(FacesContext context, ResponseWriter out)
            throws IOException {
        out.endElement(JsfConstants.TR_ELEM);
        out.endElement(JsfConstants.TABLE_ELEM);
    }

    private UIComponent encodeNavigation(FacesContext context,
            ResponseWriter out, THtmlTree tree) throws IOException {
        TreeNode node = tree.getNode();
        String nodeId = tree.getNodeId();
        String spanId = TOGGLE_SPAN + ":" + tree.getId() + ":" + nodeId;
        boolean showLines = tree.isShowLines();
        boolean clientSideToggle = tree.isClientSideToggle();
        UIComponent nodeTypeFacet = tree.getFacet(node.getType());
        String navSrc = null;
        String altSrc = null;
        UIComponent nodeImgFacet = null;

        int bitMask = NOTHING;
        bitMask += (node.isLeaf()) ? NOTHING : CHILDREN;
        if (bitMask == CHILDREN) // if there are no children, ignore expand state -> more flexible with dynamic tree-structures
            bitMask += (tree.isNodeExpanded()) ? EXPANDED : NOTHING;
        bitMask += (tree.isLastChild(tree.getNodeId())) ? LAST : NOTHING;
        bitMask += (showLines) ? LINES : NOTHING;

        switch (bitMask) {
        case (NOTHING):
        case (LAST):
            navSrc = "spacer.gif";
            break;
        case (LINES):
            navSrc = "line-middle.gif";
            break;
        case (LINES + LAST):
            navSrc = "line-last.gif";
            break;
        case (CHILDREN):
        case (CHILDREN + LAST):
            navSrc = "nav-plus.gif";
            altSrc = "nav-minus.gif";
            break;
        case (CHILDREN + LINES):
            navSrc = "nav-plus-line-middle.gif";
            altSrc = "nav-minus-line-middle.gif";
            break;
        case (CHILDREN + LINES + LAST):
            navSrc = "nav-plus-line-last.gif";
            altSrc = "nav-minus-line-last.gif";
            break;
        case (CHILDREN + EXPANDED):
        case (CHILDREN + EXPANDED + LAST):
            navSrc = "nav-minus.gif";
            altSrc = "nav-plus.gif";
            break;
        case (CHILDREN + EXPANDED + LINES):
            navSrc = "nav-minus-line-middle.gif";
            altSrc = "nav-plus-line-middle.gif";
            break;
        case (CHILDREN + EXPANDED + LINES + LAST):
            navSrc = "nav-minus-line-last.gif";
            altSrc = "nav-plus-line-last.gif";
            break;
        // unacceptable bitmask combinations
        case (EXPANDED + LINES):
        case (EXPANDED + LINES + LAST):
        case (EXPANDED):
        case (EXPANDED + LAST):
            throw new IllegalStateException(
                    "Encountered a node ["
                            + nodeId
                            + "] + with an illogical state.  "
                            + "Node is expanded but it is also considered a leaf (a leaf cannot be considered expanded.");
        default:
            // catch all for any other combinations
            throw new IllegalArgumentException("Invalid bit mask of " + bitMask);
        }

        // adjust navSrc and altSrc so that the images can be retrieved using the extensions filter
        String navSrcUrl = getGifImageSrc(navSrc);
        navSrc = getGifImageSrc(navSrc);
        altSrc = getGifImageSrc(altSrc);

        // render nav cell
        out.startElement(JsfConstants.TD_ELEM, tree);
        out.writeAttribute(JsfConstants.WIDTH_ATTR, "19", null);
        out.writeAttribute(JsfConstants.HEIGHT_ATTR, "100%", null);
        out.writeAttribute("valign", "top", null);

        if ((bitMask & LINES) != 0 && (bitMask & LAST) == 0) {
            out.writeURIAttribute("background",
                    getGifImageSrc("line-trunk.gif"), null);
        }

        //      add the appropriate image for the nav control
        UIGraphic image = new HtmlGraphicImage();
        image.setId(IMAGE_PREFIX);
        image.setUrl(navSrcUrl);
        Map imageAttrs = image.getAttributes();
        imageAttrs.put(JsfConstants.WIDTH_ATTR, "19");
        imageAttrs.put(JsfConstants.HEIGHT_ATTR, "18");
        imageAttrs.put(JsfConstants.BORDER_ATTR, "0");

        if (clientSideToggle) {
            /**
             * With client side toggle, user has the option to specify open/closed images for the node (in addition to
             * the navigtion ones provided by the component.)
             */
            String expandImgSrc = "";
            String collapseImgSrc = "";
            String nodeImageId = "";

            UIComponent expandFacet = nodeTypeFacet.getFacet("expand");
            if (expandFacet != null) {
                UIGraphic expandImg = (UIGraphic) expandFacet;
                expandImgSrc = context.getApplication().getViewHandler()
                        .getResourceURL(context, expandImg.getUrl());
                if (expandImg.isRendered()) {
                    expandImg.setId(IMAGE_PREFIX + NODE_STATE_EXPANDED);
                    expandImg.setParent(tree);
                    nodeImageId = expandImg.getClientId(context);
                    nodeImgFacet = expandFacet;
                }
            }

            UIComponent collapseFacet = nodeTypeFacet.getFacet("collapse");
            if (collapseFacet != null) {
                UIGraphic collapseImg = (UIGraphic) collapseFacet;
                collapseImgSrc = context.getApplication().getViewHandler()
                        .getResourceURL(context, collapseImg.getUrl());
                if (collapseImg.isRendered()) {
                    collapseImg.setId(IMAGE_PREFIX + NODE_STATE_CLOSED);
                    collapseImg.setParent(tree);
                    nodeImageId = collapseImg.getClientId(context);
                    nodeImgFacet = collapseFacet;
                }
            }

            image.setParent(tree);
            if (node.getChildCount() > 0) {
                String onClick = new StringBuffer().append(NAMESPACE).append(
                        "treeNavClick('").append(spanId).append("', '").append(
                        image.getClientId(context)).append("', '").append(
                        navSrc).append("', '").append(altSrc).append("', '")
                        .append(nodeImageId).append("', '")
                        .append(expandImgSrc).append("', '").append(
                                collapseImgSrc).append("', '").append(
                                tree.getId()).append("', '").append(nodeId)
                        .append("');").toString();

                imageAttrs.put(JsfConstants.ONCLICK_ATTR, onClick);
                imageAttrs.put(JsfConstants.STYLE_ATTR,
                        "cursor:hand;cursor:pointer");
            }
            renderChild(context, image);

        } else {
            if (node.getChildCount() > 0) {
                // set up the expand control and remove whatever children (if any) this control had previously
                UICommand expandControl = tree.getExpandControl();
                expandControl.getChildren().clear();
                expandControl.setId(TOGGLE_ID);

                UIParameter param = new UIParameter();
                param.setName(tree.getId() + NamingContainer.SEPARATOR_CHAR
                        + NAV_COMMAND);
                param.setValue(tree.getNodeId());
                expandControl.getChildren().add(param);
                expandControl.getChildren().add(image);

                renderChild(context, expandControl);
            } else {
                renderChild(context, image);
            }
        }
        out.endElement(JsfConstants.TD_ELEM);

        return nodeImgFacet;
    }

    protected String getScriptKey() {
        return THtmlTree.class.getName();
    }

    private String getGifImageSrc(String imageName) {
        return getImageSrc(imageName, "gif");
    }

    private String getImageSrc(String imageName, String extension) {
        final String path = "/" + StringUtil.replace(getScriptKey(), ".", "/");
        String resourcePath = ResourceUtil.getResourcePath(path + "/"
                + imageName, extension);
        return resourcePath;
    }

    protected boolean getBoolean(UIComponent component, String attributeName,
            boolean defaultValue) {
        Boolean booleanAttr = (Boolean) component.getAttributes().get(
                attributeName);

        if (booleanAttr == null) {
            return defaultValue;
        } else {
            return booleanAttr.booleanValue();
        }
    }

    private Map getCookieAttr(Cookie cookie) {
        Map attribMap = new HashMap();
        try {
            String cookieValue = URLDecoder.decode(cookie.getValue(), ENCODING);
            String[] attribArray = cookieValue.split(ATTRIB_DELIM);
            for (int j = 0; j < attribArray.length; j++) {
                int index = attribArray[j].indexOf(ATTRIB_KEYVAL);
                String name = attribArray[j].substring(0, index);
                String value = attribArray[j].substring(index + 1);
                attribMap.put(name, value);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error parsing tree cookies", e);
        }
        return attribMap;
    }

    private static void renderChild(FacesContext facesContext, UIComponent child)
            throws IOException {
        if (!child.isRendered()) {
            return;
        }
        child.encodeBegin(facesContext);
        if (child.getRendersChildren()) {
            child.encodeChildren(facesContext);
        } else {
            renderChildren(facesContext, child);
        }
        child.encodeEnd(facesContext);
    }

    private static void renderChildren(FacesContext facesContext,
            UIComponent component) throws IOException {
        if (component.getChildCount() > 0) {
            for (Iterator it = component.getChildren().iterator(); it.hasNext();) {
                UIComponent child = (UIComponent) it.next();
                renderChild(facesContext, child);
            }
        }
    }

}
