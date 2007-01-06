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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.ScriptEnhanceUIViewRoot;
import org.seasar.teeda.extension.component.TreeModel;
import org.seasar.teeda.extension.component.TreeNode;
import org.seasar.teeda.extension.component.TreeWalker;
import org.seasar.teeda.extension.component.UITreeData;
import org.seasar.teeda.extension.component.html.THtmlTree;
import org.seasar.teeda.extension.util.JavaScriptContext;
import org.seasar.teeda.extension.util.TreeNavigationImageLocator;

public class THtmlTreeRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Tree";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.THtmlTree";

    //TODO change with javascript
    protected static final String TOGGLE_DIV = "teeda.extension.tree.TOGGLE_SPAN";

    protected static final String ROOT_NODE_ID = "0";

    private static final String NODE_STATE_EXPANDED = "x";

    private static final String NODE_STATE_CLOSED = "c";

    private static final String IMAGE_PREFIX = "t2";

    private static final String NAMESPACE = "Teeda.THtmlTree.";

    private static final String TOGGLE_VALUE_SUFFIX = ExtensionConstants.NAME_SEPARATOR
            + "treeExpanded";

    private TreeNavigationImageLocator imageLocator;

    public static final String imageLocator_BINDING = "bindingType=may";

    public boolean getRendersChildren() {
        return true;
    }

    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
        decodeTHtmlTree(context, (THtmlTree) component);
    }

    protected void decodeTHtmlTree(FacesContext context, THtmlTree tree) {
        if (tree.getDataModel() == null) {
            return;
        }
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        decodeTreeNodeExpansion(tree, paramMap);
        decodeTreeValue(context, tree, paramMap);
    }

    protected void decodeTreeNodeExpansion(THtmlTree tree, Map paramMap) {
        final TreeModel model = tree.getDataModel();
        final String prefix = TOGGLE_DIV + ExtensionConstants.NAME_SEPARATOR
                + tree.getId() + ExtensionConstants.NAME_SEPARATOR;
        for (Iterator itr = paramMap.keySet().iterator(); itr.hasNext();) {
            String key = (String) itr.next();
            if (key.startsWith(prefix) && key.endsWith(TOGGLE_VALUE_SUFFIX)) {
                String nodeId = StringUtil.replace(key, prefix, "");
                nodeId = StringUtil.replace(nodeId, TOGGLE_VALUE_SUFFIX, "");
                String value = (String) paramMap.get(key);
                if ("true".equalsIgnoreCase(value)) {
                    model.toggleExpanded(nodeId);
                } else {
                    model.collapseExpanded(nodeId);
                }
            }
        }
    }

    protected void decodeTreeValue(FacesContext context, THtmlTree tree,
            Map paramMap) {
        final TreeModel model = tree.getDataModel();
        final String clientId = tree.getClientId(context);
        final String targetPrefix = clientId
                + ExtensionConstants.NAME_SEPARATOR;
        final String[] candidates = getTreeValueDecodeCandidate(targetPrefix,
                paramMap);
        for (int i = 0; i < candidates.length; i++) {
            final String key = candidates[i];
            final Object value = paramMap.get(key);
            String s = key.substring(targetPrefix.length(), key
                    .lastIndexOf(ExtensionConstants.NAME_SEPARATOR));
            TreeNode node = model.getNodeById(s);
            node.setValue(value);
        }
    }

    protected String[] getTreeValueDecodeCandidate(final String targetPrefix,
            final Map paramMap) {
        final List list = new ArrayList();
        for (Iterator itr = paramMap.keySet().iterator(); itr.hasNext();) {
            String key = (String) itr.next();
            if (key.startsWith(targetPrefix)) {
                list.add(key);
            }
        }
        return (String[]) list.toArray(new String[list.size()]);
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
            writer.startElement(JsfConstants.DIV_ELEM, component);
            writer.writeAttribute(JsfConstants.ID_ATTR, clientId,
                    JsfConstants.ID_ATTR);
        }
        final boolean showRootNode = tree.isShowRootNode();
        TreeModel model = tree.getDataModel();
        TreeWalker walker = tree.getDataModel().getTreeWalker();
        walker.walkBegin(tree);
        if (showRootNode) {
            if (walker.next()) {
                encodeTree(context, writer, tree, walker);
            }
        } else {
            skipRootTreeNode(walker);
            setRootNodeExpanded(tree, model);
            encodeEachTreeNode(context, walker, tree);
        }
        resetCurrentNode(tree);
        if (isOuterSpanUsed) {
            writer.endElement(JsfConstants.DIV_ELEM);
        }
    }

    protected void skipRootTreeNode(TreeWalker walker) {
        walker.next();
    }

    protected void setRootNodeExpanded(final THtmlTree tree,
            final TreeModel model) {
        String rootNodeId = tree.getNodeId();
        if (!model.isNodeExpanded(rootNodeId)) {
            model.toggleExpanded(rootNodeId);
        }
    }

    protected void encodeEachTreeNode(FacesContext context, TreeWalker walker,
            THtmlTree tree) throws IOException {
        TreeNode rootNode = tree.getNode();
        ResponseWriter writer = context.getResponseWriter();
        for (int i = 0; i < rootNode.getChildCount(); i++) {
            if (walker.next()) {
                encodeTree(context, writer, tree, walker);
            }
        }
    }

    protected void resetCurrentNode(THtmlTree tree) {
        tree.setNodeId(null);
    }

    protected void encodeTree(FacesContext context, ResponseWriter out,
            THtmlTree tree, TreeWalker walker) throws IOException {
        beforeNodeEncode(context, out, tree);
        encodeCurrentNode(context, out, tree);
        afterNodeEncode(context, out);

        final String divId = getMarkerId(tree);
        out.startElement(JsfConstants.DIV_ELEM, tree);
        out.writeAttribute(JsfConstants.ID_ATTR, divId, null);
        if (tree.isNodeExpanded()) {
            RendererUtil.renderAttribute(out, JsfConstants.STYLE_ATTR,
                    "display:block");
        } else {
            RendererUtil.renderAttribute(out, JsfConstants.STYLE_ATTR,
                    "display:none");
        }
        final String id = divId + ExtensionConstants.NAME_SEPARATOR
                + "treeExpanded";
        HtmlInputHidden hidden = new HtmlInputHidden() {

            public String getId() {
                return id;
            }

            public String getClientId(FacesContext context) {
                return id;
            }
        };
        hidden.setValue(new Boolean(tree.isNodeExpanded()));
        hidden.setParent(tree);
        RendererUtil.renderChild(context, hidden);

        TreeNode node = tree.getNode();
        for (int i = 0; i < node.getChildCount(); i++) {
            if (walker.next()) {
                encodeTree(context, out, tree, walker);
            }
        }
        out.endElement(JsfConstants.DIV_ELEM);
    }

    protected void encodeCurrentNode(FacesContext context, ResponseWriter out,
            THtmlTree tree) throws IOException {
        boolean showNav = tree.isShowNav();
        TreeNode node = tree.getNode();
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
            final boolean shouldShowLineBackground = (!lastChild && showLines);
            final String contextPath = context.getExternalContext()
                    .getRequestContextPath();
            final String lineSrc = contextPath
                    + imageLocator
                            .getLineBackgroundSrc(shouldShowLineBackground);
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

    protected HtmlGraphicImage createHtmlGraphicImage(final String navSrc) {
        final HtmlGraphicImage image = new HtmlGraphicImage();
        image.setId(IMAGE_PREFIX);
        image.setRendererType(THtmlTreeGraphicImageRenderer.RENDERER_TYPE);
        image.setUrl(navSrc);
        image.setWidth("19");
        image.setHeight("18");
        image.setBorder(0);
        return image;
    }
    
    private UIComponent encodeNavigation(FacesContext context,
            ResponseWriter out, THtmlTree tree) throws IOException {
        final String ownClientId = tree.getOwnClientId(context);
        final String contextPath = context.getExternalContext()
                .getRequestContextPath();
        TreeNode node = tree.getNode();
        String nodeId = tree.getNodeId();
        String markerId = getMarkerId(tree);
        UIComponent nodeTypeFacet = tree.getFacet(node.getType());
        UIComponent nodeImgFacet = null;

        imageLocator.setUpImageLocation(tree);
        final String navSrc = imageLocator.getNavSrc();
        final String altSrc = imageLocator.getAltSrc();
        final boolean isClickable = imageLocator.isClickable();
        // render nav cell
        out.startElement(JsfConstants.TD_ELEM, tree);
        out.writeAttribute(JsfConstants.WIDTH_ATTR, "19", null);
        out.writeAttribute(JsfConstants.HEIGHT_ATTR, "100%", null);
        out.writeAttribute("valign", "top", null);
        if (imageLocator.shouldRenderLineBackground()) {
            final String img = contextPath
                    + imageLocator.getLineBackgroundSrc(true);
            out.writeURIAttribute("background", img, null);
        }
        HtmlGraphicImage image = createHtmlGraphicImage(navSrc);
        String expandImgSrc = "";
        String collapseImgSrc = "";
        String nodeImageId = "";
        String expandImgUrl = null;
        String collapseImgUrl = null;
        UIComponent expandFacet = nodeTypeFacet.getFacet("expand");
        if (expandFacet != null) {
            UIGraphic expandImg = (UIGraphic) expandFacet;
            final ViewHandler vh = context.getApplication().getViewHandler();
            expandImgUrl = expandImg.getUrl();
            expandImgSrc = vh.getResourceURL(context, expandImgUrl);
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
            collapseImgUrl = collapseImg.getUrl();
            collapseImgSrc = context.getApplication().getViewHandler()
                    .getResourceURL(context, collapseImgUrl);
            if (collapseImg.isRendered()) {
                collapseImg.setId(IMAGE_PREFIX + NODE_STATE_CLOSED);
                collapseImg.setParent(tree);
                nodeImageId = collapseImg.getClientId(context);
                nodeImgFacet = collapseFacet;
            }
        }
        image.setParent(tree);
        final String imageClientId = image.getClientId(context);

        if (isClickable) {
            out.startElement(JsfConstants.ANCHOR_ELEM, tree);
            out.writeAttribute(JsfConstants.HREF_ATTR, "#", null);
            final StringBuffer buf = new StringBuffer();
            buf.append(NAMESPACE);
            buf.append("walkTreeNode(event, '");
            buf.append(imageClientId);
            buf.append("', this, '");
            buf.append(ownClientId);
            buf.append("', '");
            buf.append(markerId);
            buf.append("', '");
            buf.append(imageClientId);
            buf.append("', '");
            buf.append(navSrc);
            buf.append("', '");
            buf.append(nodeImageId);
            buf.append("', '");
            buf.append(expandImgUrl);
            buf.append("');");
            out.writeAttribute(JsfConstants.ONKEYDOWN_ATTR, buf.toString(), "");
        }

        if (node.getChildCount() > 0) {
            final StringBuffer buf = new StringBuffer();
            buf.append(NAMESPACE);
            buf.append("treeNavClick('");
            buf.append(markerId);
            buf.append("', '");
            buf.append(imageClientId);
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
            buf.append(nodeId);
            buf.append("', '");
            buf.append(contextPath);
            buf.append("');");
            image.setOnclick(buf.toString());
            image.setStyle("cursor:hand;cursor:pointer");
        }
        RendererUtil.renderChild(context, image);
        if (isClickable) {
            out.endElement(JsfConstants.ANCHOR_ELEM);
        }
        out.endElement(JsfConstants.TD_ELEM);
        return nodeImgFacet;
    }
    
    protected String getScriptKey() {
        return THtmlTree.class.getName();
    }

    public TreeNavigationImageLocator getImageLocator() {
        return imageLocator;
    }

    public void setImageLocator(TreeNavigationImageLocator imageLocator) {
        this.imageLocator = imageLocator;
    }

    protected String getMarkerId(UITreeData tree) {
        return TOGGLE_DIV + ExtensionConstants.NAME_SEPARATOR + tree.getId()
                + ExtensionConstants.NAME_SEPARATOR + tree.getNodeId();
    }
}
