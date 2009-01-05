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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.LabelUtil;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.RenderPreparableRenderer;
import org.seasar.teeda.core.render.html.HtmlCommandButtonRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.TForEach;
import org.seasar.teeda.extension.component.html.THtmlCommandButton;
import org.seasar.teeda.extension.event.TActionEvent;
import org.seasar.teeda.extension.util.DoubleSubmitProtectionLoader;
import org.seasar.teeda.extension.util.KumuDisabledScriptLoader;
import org.seasar.teeda.extension.util.PathUtil;
import org.seasar.teeda.extension.util.TransactionTokenUtil;

/**
 * @author higa
 * @author shot
 */
public class THtmlCommandButtonRenderer extends HtmlCommandButtonRenderer
        implements RenderPreparableRenderer {

    public static final String COMPONENT_FAMILY = THtmlCommandButton.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlCommandButton.DEFAULT_RENDERER_TYPE;

    public static final String loader_BINDING = "bindingType=may";

    private DoubleSubmitProtectionLoader loader = new KumuDisabledScriptLoader();

    public THtmlCommandButtonRenderer() {
        addIgnoreAttributeName(ExtensionConstants.RENDERJS_ATTR);
        addIgnoreAttributeName(ExtensionConstants.TIME_ATTR);
        addIgnoreAttributeName("baseViewId");
    }

    protected void enqueueEvent(final HtmlCommandButton htmlCommandButton) {
        TActionEvent event = new TActionEvent(htmlCommandButton);
        UIComponent component = htmlCommandButton.getParent();
        while (component != null) {
            if (component instanceof TForEach) {
                TForEach forEach = (TForEach) component;
                event.addIndex(forEach, forEach.getRowIndex());
            }
            component = component.getParent();
        }
        htmlCommandButton.queueEvent(event);
    }

    public void encodeBefore(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        final String id = component.getId();
        if (TransactionTokenUtil.isDoOnce(id)) {
            encodeTHtmlCommandButtonPrepare(context,
                    (THtmlCommandButton) component);
        }
    }

    protected void encodeTHtmlCommandButtonPrepare(final FacesContext context,
            final THtmlCommandButton button) throws IOException {
        if (!button.isRenderJs()) {
            return;
        }
        loader.loadScript(context, button);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        if (!component.isRendered()) {
            return;
        }
        if (!TransactionTokenUtil.isDoOnce(component.getId())) {
            return;
        }
        TransactionTokenUtil.renderTokenIfNeed(context, component);
    }

    protected String getImage(FacesContext context,
            HtmlCommandButton htmlCommandButton) {
        final String image = super.getImage(context, htmlCommandButton);
        if (StringUtil.isEmpty(image)) {
            return image;
        }
        final String baseViewId = ((THtmlCommandButton) htmlCommandButton)
                .getBaseViewId();
        return PathUtil.toAbsolutePath(context, image, baseViewId);
    }

    protected void renderValueAttribute(FacesContext context,
            UICommand command, ResponseWriter writer) throws IOException {
        final String id = getIdForRender(context, command);
        final String labelValue = LabelUtil.getLabelValue(id);
        if (labelValue != null) {
            RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR,
                    labelValue);
        } else {
            super.renderValueAttribute(context, command, writer);
        }
    }

    public DoubleSubmitProtectionLoader getLoader() {
        return loader;
    }

    public void setLoader(DoubleSubmitProtectionLoader loader) {
        this.loader = loader;
    }

}
