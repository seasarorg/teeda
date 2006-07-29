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
package org.seasar.teeda.core.render;

import javax.faces.render.ResponseStateManager;

/**
 * @author shot
 */
public abstract class AbstractResponseStateManager extends ResponseStateManager {

    public static final String RENDER_KIT_ID_PARAM = "javax.faces.RenderKitId";

    public static final String VIEW_STATE_PARAM = "javax.faces.ViewState";

    public static final String FACES_VIEW_STATE = AbstractResponseStateManager.class
            .getPackage()
            + ".FACES_VIEW_STATE";

    private EncodeConverter encodeConverter;

    public void setEncodeConverter(EncodeConverter encodeConverter) {
        this.encodeConverter = encodeConverter;
    }

    public EncodeConverter getEncodeConverter() {
        return encodeConverter;
    }

}
