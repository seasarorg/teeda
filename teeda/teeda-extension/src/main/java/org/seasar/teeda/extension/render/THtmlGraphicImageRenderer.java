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
package org.seasar.teeda.extension.render;

import javax.faces.component.UIGraphic;
import javax.faces.context.FacesContext;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.HtmlGraphicImageRenderer;
import org.seasar.teeda.extension.component.html.THtmlGraphicImage;

/**
 * @author koichik
 * @since 1.0.12
 */
public class THtmlGraphicImageRenderer extends HtmlGraphicImageRenderer {

    public static final String COMPONENT_FAMILY = THtmlGraphicImage.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlGraphicImage.DEFAULT_RENDERER_TYPE;

    private final IgnoreAttribute ignoreComponent = new IgnoreAttribute();
    {
        ignoreComponent.addAttributeName(JsfConstants.ID_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.VALUE_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.URL_ATTR);
    }

    protected String getUrl(final FacesContext context,
            final UIGraphic htmlGraphicImage) {
        return htmlGraphicImage.getUrl();
    }

}
