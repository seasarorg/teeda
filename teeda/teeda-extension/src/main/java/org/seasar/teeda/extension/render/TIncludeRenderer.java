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

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.internal.UIComponentUtil;

import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.component.AbstractInclude;
import org.seasar.teeda.extension.component.TInclude;
import org.seasar.teeda.extension.component.UIBody;

/**
 * @author higa
 */
public class TIncludeRenderer extends AbstractIncludeRenderer {

    public static final String COMPONENT_FAMILY = TInclude.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = TInclude.DEFAULT_RENDERER_TYPE;

    private static final Logger logger = Logger
            .getLogger(TIncludeRenderer.class);

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeBegin(context, component);
        AbstractInclude inc = (AbstractInclude) component;
        if (!inc.isIncluded()) {
            include(context, inc);
        }
        if (!inc.isIncluded()) {
            return;
        }
        invoke(context, inc.getIncludedViewId());
    }

    protected IncludedBody getIncludedBody(FacesContext context,
            AbstractInclude component) {

        String src = ((TInclude) component).getSrc();
        if (StringUtil.isEmpty(src)) {
            return null;
        }
        String srcViewId = getPathHelper()
                .fromViewRootRelativePathToViewId(src);
        UIViewRoot viewRoot = getViewHandler().restoreView(context, srcViewId);
        if (viewRoot == null) {
            viewRoot = getViewHandler().createView(context, srcViewId);
        }
        if (viewRoot == null) {
            return null;
        }
        UIComponent body = UIComponentUtil.findDescendant(viewRoot,
                UIBody.class);
        if (body == null) {
            logger.log("WTDA0202", new Object[] { viewRoot.getViewId() });
        }
        return new IncludedBody(viewRoot.getViewId(), body.getChildren());
    }
}