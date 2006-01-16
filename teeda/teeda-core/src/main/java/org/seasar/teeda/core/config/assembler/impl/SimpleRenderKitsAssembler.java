/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.assembler.impl;

import java.util.Iterator;
import java.util.Map;

import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

import org.seasar.teeda.core.config.assembler.RenderKitsAssembler;
import org.seasar.teeda.core.config.element.RenderKitElement;
import org.seasar.teeda.core.config.element.RendererElement;
import org.seasar.teeda.core.render.html.HtmlRenderKitImpl;
import org.seasar.teeda.core.util.ClassUtil;
import org.seasar.teeda.core.util.FactoryFinderUtil;
import org.seasar.teeda.core.util.IteratorUtil;

//TODO make it done.
public class SimpleRenderKitsAssembler extends RenderKitsAssembler {

    public SimpleRenderKitsAssembler(Map renderKits) {
        super(renderKits);
    }

    protected void setupBeforeAssemble() {
    }

    public void assemble() {
        RenderKitFactory renderKitFactory = FactoryFinderUtil
                .getRenderKitFactory();
        for (Iterator itr = IteratorUtil.getIterator(getRenderKits()); itr.hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            String renderKitId = (String) entry.getKey();
            RenderKitElement renderKitElement = (RenderKitElement) entry
                    .getValue();
            String className = renderKitElement.getRenderKitClass();
            if(className == null) {
                className = HtmlRenderKitImpl.class.getName();
            }
            RenderKit renderKit = (RenderKit) ClassUtil.newInstance(className);

            for (Iterator i = renderKitElement.getRendererElements().iterator(); i
                    .hasNext();) {
                RendererElement rendererElement = (RendererElement) i.next();
                String rendererClass = rendererElement.getRendererClass();
                Renderer renderer = null;
                try {
                    renderer = (Renderer) ClassUtil.newInstance(rendererClass);
                } catch (Exception e) {
                    // ignore
                    continue;
                }
                renderKit.addRenderer(rendererElement.getComponentFamily(), rendererElement.getRendererType(), renderer);
            }
            renderKitFactory.addRenderKit(renderKitElement.getRenderKitId(), renderKit);
        }
    }

}
