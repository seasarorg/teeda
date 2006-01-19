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

import org.seasar.framework.log.Logger;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.config.assembler.RenderKitAssembler;
import org.seasar.teeda.core.config.element.RenderKitElement;
import org.seasar.teeda.core.config.element.RendererElement;
import org.seasar.teeda.core.util.FactoryFinderUtil;
import org.seasar.teeda.core.util.IteratorUtil;

/**
 * @author shot
 */
public class DefaultRenderKitAssembler extends RenderKitAssembler {

    private static Logger logger_ = Logger.getLogger(DefaultRenderKitAssembler.class);
    
    public DefaultRenderKitAssembler(Map renderKits) {
        super(renderKits);
    }

    protected void setupBeforeAssemble() {
        for (Iterator itr = IteratorUtil.getIterator(getRenderKits()); itr.hasNext();) {
            String renderKitId = (String) itr.next();
            RenderKitElement renderKitElement = (RenderKitElement)getRenderKits().get(renderKitId);
            isAllSuitableJsfElement(renderKitElement.getRendererElements(), RendererElement.class);
        }
    }

    public void assemble() {
        RenderKitFactory renderKitFactory = FactoryFinderUtil
                .getRenderKitFactory();
        for (Iterator itr = IteratorUtil.getIterator(getRenderKits()); itr.hasNext();) {
            String renderKitId = (String) itr.next();
            RenderKitElement renderKitElement = (RenderKitElement)getRenderKits().get(renderKitId);
            String className = getRenderKitClassName(renderKitElement);
            RenderKit renderKit = createRenderKit(className);

            for (Iterator i = renderKitElement.getRendererElements().iterator(); i
                    .hasNext();) {
                RendererElement rendererElement = (RendererElement) i.next();
                String rendererClass = rendererElement.getRendererClass();
                Renderer renderer = createRenderer(rendererClass);
                renderKit.addRenderer(rendererElement.getComponentFamily(), rendererElement.getRendererType(), renderer);
            }
            renderKitFactory.addRenderKit(renderKitElement.getRenderKitId(), renderKit);
        }
    }

    protected String getRenderKitClassName(RenderKitElement renderKitElement){
        String className = renderKitElement.getRenderKitClass();
        if(className == null) {
            className = JsfConstants.DEFAULT_RENDERKIT_CLASS;
        }
        return className;
    }
    
    protected RenderKit createRenderKit(String className){
        return (RenderKit) newInstance(className);
    }
    
    protected Renderer createRenderer(String className){
        Renderer renderer = null;
        try {
            renderer = (Renderer) newInstance(className);
        } catch (Exception e) {
            logger_.warn("Exception "+ e + "occured when trying to create Renderer.");
        }
        return renderer;
    }

}
