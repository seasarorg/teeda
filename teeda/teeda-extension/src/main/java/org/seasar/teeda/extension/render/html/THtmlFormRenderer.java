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

import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.render.html.HtmlFormRenderer;
import org.seasar.teeda.extension.component.html.THtmlForm;
import org.seasar.teeda.extension.util.TransactionTokenUtil;

/**
 * @author koichik
 */
public class THtmlFormRenderer extends HtmlFormRenderer {

    public static final String COMPONENT_FAMILY = THtmlForm.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlForm.DEFAULT_RENDERER_TYPE;

    protected void encodeHtmlFormEnd(final FacesContext context,
            final HtmlForm htmlForm) throws IOException {
        super.encodeHtmlFormEnd(context, htmlForm);
        TransactionTokenUtil.resetToken(context);
    }

}
