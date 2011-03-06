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
package org.seasar.teeda.core.config.faces.element.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.render.RenderKitFactory;

import org.seasar.teeda.core.config.faces.element.RenderKitElement;
import org.seasar.teeda.core.config.faces.element.RendererElement;

/**
 * @author shot
 */
public class RenderKitElementImpl implements RenderKitElement {

    private String renderKitId_ = RenderKitFactory.HTML_BASIC_RENDER_KIT;

    private String renderKitClass_;

    private List renderers_ = new ArrayList();

    public RenderKitElementImpl() {
    }

    public void setRenderKitId(String renderKitId) {
        renderKitId_ = renderKitId;
    }

    public void setRenderKitClass(String renderKitClass) {
        renderKitClass_ = renderKitClass;
    }

    public String getRenderKitId() {
        return renderKitId_;
    }

    public String getRenderKitClass() {
        return renderKitClass_;
    }

    public void addRendererElement(RendererElement renderer) {
        renderers_.add(renderer);
    }

    public List getRendererElements() {
        return renderers_;
    }

}
