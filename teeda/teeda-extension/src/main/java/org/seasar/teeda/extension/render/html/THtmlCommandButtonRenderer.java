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

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.LabelUtil;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.HtmlCommandButtonRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.util.TransactionTokenUtil;

/**
 * @author higa
 *
 */
public class THtmlCommandButtonRenderer extends HtmlCommandButtonRenderer {

    //TODO testing
    //TODO register by TeedaRendererComponentAutoRegister.
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

    protected void renderValueAttribute(FacesContext context,
            UICommand command, ResponseWriter writer) throws IOException {
        final String id = getIdForRender(context, command);
        final String labelValue = LabelUtil.getLabelValue(id);
        if(labelValue != null) {
            RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR, labelValue);
        } else {
            super.renderValueAttribute(context, command, writer);
        }
    }

}
