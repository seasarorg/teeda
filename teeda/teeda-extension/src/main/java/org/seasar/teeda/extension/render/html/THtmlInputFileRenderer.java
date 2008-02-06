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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;

import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.html.THtmlInputFile;

/**
 * @author koichik
 */
public class THtmlInputFileRenderer extends THtmlInputTextRenderer {

    public static final String COMPONENT_FAMILY = THtmlInputFile.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlInputFile.DEFAULT_RENDERER_TYPE;

    public THtmlInputFileRenderer() {
        setDecoder(new UploadedFileDecoder());
    }

    protected void encodeHtmlInputTextEnd(FacesContext context,
            HtmlInputText htmlInputFile) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.INPUT_ELEM, htmlInputFile);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.FILE_VALUE);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlInputFile,
                getIdForRender(context, htmlInputFile));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                htmlInputFile.getClientId(context));
        RendererUtil.renderAttribute(writer, JsfConstants.AUTOCOMPLETE_ATTR,
                htmlInputFile.getAutocomplete());
        renderStyleClass(context, htmlInputFile, writer);
        renderRemainAttributes(htmlInputFile, writer, getIgnoreAttributes());
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

}
