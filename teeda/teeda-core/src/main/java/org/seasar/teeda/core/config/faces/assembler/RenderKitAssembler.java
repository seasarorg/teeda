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
package org.seasar.teeda.core.config.faces.assembler;

import java.util.Collections;
import java.util.Map;

import org.seasar.teeda.core.config.faces.element.RenderKitElement;
import org.seasar.teeda.core.util.ClassUtil;

/**
 * @author shot
 */
public abstract class RenderKitAssembler extends AbstractJsfAssembler {

    private Map renderKits_ = Collections.EMPTY_MAP;

    public RenderKitAssembler(Map renderKits) {
        if (renderKits == null) {
            throw new IllegalArgumentException("renderKits");
        }
        isAllSuitableJsfElement(renderKits.values(), RenderKitElement.class);
        renderKits_ = renderKits;
        setupBeforeAssemble();
    }

    protected final Map getRenderKits() {
        return renderKits_;
    }

    protected Object newInstance(String className) {
        return ClassUtil.newInstance(className);
    }

}
