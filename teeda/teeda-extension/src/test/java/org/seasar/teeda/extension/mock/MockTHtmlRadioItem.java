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
package org.seasar.teeda.extension.mock;

import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.seasar.teeda.extension.component.html.THtmlRadioItem;

/**
 * @author manhole
 */
public class MockTHtmlRadioItem extends THtmlRadioItem {

    private Renderer renderer;

    private String clientId;

    public void setRenderer(final Renderer renderer) {
        this.renderer = renderer;
    }

    protected Renderer getRenderer(final FacesContext context) {
        if (renderer != null) {
            return renderer;
        }
        return super.getRenderer(context);
    }

    public String getClientId(final FacesContext context) {
        if (clientId != null) {
            return clientId;
        }
        return super.getClientId(context);
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

}