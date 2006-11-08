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
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.internal.UIComponentUtil;

import org.seasar.teeda.core.render.html.HtmlCommandButtonRenderer;
import org.seasar.teeda.core.render.html.HtmlFormRenderer;
import org.seasar.teeda.extension.util.TransactionTokenUtil;

/**
 * @author higa
 * 
 */
public class THtmlCommandButtonRenderer extends HtmlCommandButtonRenderer {

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        if (!component.isRendered()) {
            return;
        }
        if (!TransactionTokenUtil.isDoOnce(component.getId())) {
            return;
        }
        UIForm form = UIComponentUtil.findParentForm(component);
        Map hiddenParams = HtmlFormRenderer.getHiddenParameters(form);
        if (!hiddenParams.containsKey(TransactionTokenUtil.TOKEN)) {
            hiddenParams.put(TransactionTokenUtil.TOKEN, TransactionTokenUtil
                    .generate(context));
        }

    }

}
