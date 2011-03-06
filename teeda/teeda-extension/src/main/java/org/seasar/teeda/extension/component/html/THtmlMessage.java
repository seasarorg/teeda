/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlMessage;

import org.seasar.teeda.extension.util.AdjustValueHolderUtil;

/**
 * @author shot
 */
public class THtmlMessage extends HtmlMessage {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlMessage";

    public static final String DEFAULT_RENDERER_TYPE = "javax.faces.Message";

    public THtmlMessage() {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    protected boolean containsSameId(UIComponent base, String id) {
        String baseId = base.getId();
        if (baseId != null) {
            baseId = AdjustValueHolderUtil.getAdjustedValue(baseId);
        }
        return id.equals(baseId);
    }

}
