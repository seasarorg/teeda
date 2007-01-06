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
package org.seasar.teeda.core.render;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

import org.seasar.framework.log.Logger;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.util.DIContainerUtil;

/**
 * @author shot
 */
public class RenderKitFactoryImpl extends RenderKitFactory {

    private Map renderKits_ = Collections.synchronizedMap(new HashMap());

    private static Logger logger_ = Logger
            .getLogger(RenderKitFactoryImpl.class);

    public RenderKitFactoryImpl() {
        initialize();
    }

    public void addRenderKit(String renderKitId, RenderKit renderKit) {
        AssertionUtil.assertNotNull("renderKitId", renderKitId);
        AssertionUtil.assertNotNull("renderKit", renderKit);
        renderKits_.put(renderKitId, renderKit);
    }

    public RenderKit getRenderKit(FacesContext context, String renderKitId) {
        AssertionUtil.assertNotNull("renderKitId", renderKitId);
        RenderKit renderKit = (RenderKit) renderKits_.get(renderKitId);
        if (renderKit == null) {
            logger_.warn("RenderKit [" + renderKitId + "] is null.");
        }
        return renderKit;
    }

    public Iterator getRenderKitIds() {
        return renderKits_.keySet().iterator();
    }

    protected void initialize() {
        RenderKit renderKit = (RenderKit) DIContainerUtil
                .getComponentNoException(RenderKit.class);
        if (renderKit != null) {
            addRenderKit(HTML_BASIC_RENDER_KIT, renderKit);
        }
    }

}
