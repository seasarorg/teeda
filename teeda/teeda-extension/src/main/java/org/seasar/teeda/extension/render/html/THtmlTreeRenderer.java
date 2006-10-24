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

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.ScriptEnhanceUIViewRoot;
import org.seasar.teeda.extension.component.TreeNode;
import org.seasar.teeda.extension.component.TreeState;
import org.seasar.teeda.extension.component.TreeWalker;
import org.seasar.teeda.extension.component.html.THtmlTree;
import org.seasar.teeda.extension.util.JavaScriptContext;

public class THtmlTreeRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Tree";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.THtmlTree";

    protected static final String TOGGLE_SPAN = "teeda.extension.tree.TOGGLE_SPAN";

    protected static final String ROOT_NODE_ID = "0";

    private static final String NODE_STATE_EXPANDED = "x";

    private static final String NODE_STATE_CLOSED = "c";

    private static final String IMAGE_PREFIX = "t2";

    private static final String NAMESPACE = "Teeda.THtmlTree.";

    public boolean getRendersChildren() {
        return true;
    }

    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
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
        walker.walkBegin(tree);
        walker.setCheckState(!clientSideToggle); // walk all nodes in client mode
        if (showRootNode) {
            if (walker.next()) {
                encodeTree(context, writer, tree, walker);
            }
        } else {
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
        resetCurrentNode(tree);
        if (isOuterSpanUsed) {
            writer.endElement(JsfConstants.SPAN_ELEM);
        }
    }

    protected void resetCurrentNode(THtmlTree tree) {
        tree.setNodeId(null);
    }

    protected void encodeTree(FacesContext context, ResponseWriter out,
            THtmlTree tree, TreeWalker walker) throws IOException {
        final boolean clientSideToggle = tree.isClientSideToggle();

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
        boolean showNav = tree.isShowNav();
        boolean clientSideToggle = tree.isClientSideToggle();
        TreeNode node = tree.getNode();
        if (clientSideToggle) {
            showNav = true;
        }
        UIComponent nodeTypeFacet = tree.getFacet(node.getType());
        UIComponent nodeImgFacet = null;
        if (nodeTypeFacet == null) {
            throw new IllegalArgumentException(
                    "Unable to locate facet with the name: " + node.getType());
        }
        renderPadding(context, out, tree);
        if (showNav) {
            nodeImgFacet = encodeNavigation(context, out, tree);
        }
        renderEachNode(context, out, tree, nodeImgFacet, nodeTypeFacet);
    }

    protected void renderPadding(FacesContext context, ResponseWriter out,
            THtmlTree tree) throws IOException {
        final String nodeId = tree.getNodeId();
        final String[] pathInfo = tree.getPathInformation(nodeId);
        final boolean showRootNode = tree.isShowRootNode();
        final boolean showLines = tree.isShowLines();
        for (int i = (showRootNode ? 0 : 1); i < (pathInfo.length - 1); i++) {
            final boolean lastChild = tree.isLastChild((String) pathInfo[i]);
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
    }

    protected void renderEachNode(FacesContext context, ResponseWriter out,
            THtmlTree tree, UIComponent nodeImgFacet, UIComponent nodeTypeFacet)
            throws IOException {
        out.startElement(JsfConstants.TD_ELEM, tree);
        if (nodeImgFacet != null) {
            RendererUtil.renderChild(context, nodeImgFacet);
        }
        RendererUtil.renderChild(context, nodeTypeFacet);
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
        boolean clientSideToggle = tree.isClientSideToggle();
        UIComponent nodeTypeFacet = tree.getFacet(node.getType());
        UIComponent nodeImgFacet = null;

        THtmlTreeNavigationImageLocator imageLocator = new THtmlTreeNavigationImageLocator();
        imageLocator.setUpImageSources(tree);
        String navSrc = imageLocator.getNavSrc();
        String altSrc = imageLocator.getAltSrc();

        // adjust navSrc and altSrc so that the images can be retrieved using the extensions filter
        String navSrcUrl = getGifImageSrc(navSrc);
        navSrc = getGifImageSrc(navSrc);
        altSrc = getGifImageSrc(altSrc);

        // render nav cell
        out.startElement(JsfConstants.TD_ELEM, tree);
        out.writeAttribute(JsfConstants.WIDTH_ATTR, "19", null);
        out.writeAttribute(JsfConstants.HEIGHT_ATTR, "100%", null);
        out.writeAttribute("valign", "top", null);

        if (imageLocator.shouldRenderLineBackground()) {
            out.writeURIAttribute("background",
                    getGifImageSrc("line-trunk.gif"), null);
        }
        HtmlGraphicImage image = new HtmlGraphicImage();
        image.setId(IMAGE_PREFIX);
        image.setRendererType(THtmlTreeGraphicImageRenderer.RENDERER_TYPE);
        image.setUrl(navSrcUrl);
        image.setWidth("19");
        image.setHeight("18");
        image.setBorder(0);
        if (clientSideToggle) {
            String expandImgSrc = "";
            String collapseImgSrc = "";
            String nodeImageId = "";

            UIComponent expandFacet = nodeTypeFacet.getFacet("expand");
            if (expandFacet != null) {
                UIGraphic expandImg = (UIGraphic) expandFacet;
                final ViewHandler vh = context.getApplication()
                        .getViewHandler();
                final String uri = expandImg.getUrl();
                expandImgSrc = vh.getResourceURL(context, uri);
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
                StringBuffer buf = new StringBuffer();
                buf.append(NAMESPACE);
                buf.append("treeNavClick('");
                buf.append(spanId);
                buf.append("', '");
                buf.append(image.getClientId(context));
                buf.append("', '");
                buf.append(navSrc);
                buf.append("', '");
                buf.append(altSrc);
                buf.append("', '");
                buf.append(nodeImageId);
                buf.append("', '");
                buf.append(expandImgSrc);
                buf.append("', '");
                buf.append(collapseImgSrc);
                buf.append("', '");
                buf.append(tree.getId());
                buf.append("', '");
                buf.append(nodeId).append("');");
                image.setOnclick(buf.toString());
                image.setStyle("cursor:hand;cursor:pointer");
            }
            RendererUtil.renderChild(context, image);
        }
        out.endElement(JsfConstants.TD_ELEM);
        return nodeImgFacet;
    }

    protected String getScriptKey() {
        return THtmlTree.class.getName();
    }

    //TODO fix me
    private String getGifImageSrc(String imageName) {
        return imageName;
    }

}
