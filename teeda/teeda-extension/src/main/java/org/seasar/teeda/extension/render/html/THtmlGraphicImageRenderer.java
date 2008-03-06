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

import javax.faces.component.UIGraphic;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.render.html.HtmlGraphicImageRenderer;
import org.seasar.teeda.extension.component.html.THtmlGraphicImage;
import org.seasar.teeda.extension.util.PathUtil;

/**
 * @author koichik
 */
public class THtmlGraphicImageRenderer extends HtmlGraphicImageRenderer {

    public static final String COMPONENT_FAMILY = THtmlGraphicImage.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = THtmlGraphicImage.DEFAULT_RENDERER_TYPE;

    public THtmlGraphicImageRenderer() {
        addIgnoreAttributeName("baseViewId");
    }

    protected String getUrl(FacesContext context, UIGraphic htmlGraphicImage) {
        String url = htmlGraphicImage.getUrl();
        if (StringUtil.isEmpty(url)) {
            return url;
        }
        final String baseViewId = ((THtmlGraphicImage) htmlGraphicImage)
                .getBaseViewId();
        url = PathUtil.toAbsolutePath(context, url, baseViewId);
        url = context.getExternalContext().encodeResourceURL(url);
        return url;
    }

}
