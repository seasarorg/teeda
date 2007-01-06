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
package org.seasar.teeda.core.config.faces.assembler.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.config.faces.assembler.RenderKitAssembler;
import org.seasar.teeda.core.config.faces.element.RenderKitElement;
import org.seasar.teeda.core.config.faces.element.RendererElement;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.FactoryFinderUtil;
import org.seasar.teeda.core.util.IteratorUtil;

/**
 * @author shot
 */
public class DefaultRenderKitAssembler extends RenderKitAssembler {

    private static Logger logger_ = Logger
            .getLogger(DefaultRenderKitAssembler.class);

    private List renderLists_;

    public DefaultRenderKitAssembler(Map renderKits) {
        super(renderKits);
    }

    protected void setupBeforeAssemble() {
        renderLists_ = new ArrayList();
        for (Iterator itr = IteratorUtil.getEntryIterator(getRenderKits()); itr
                .hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            RenderKitElement renderKitElement = (RenderKitElement) entry
                    .getValue();
            String renderKitId = getRenderKitId(renderKitElement);
            String className = getRenderKitClassName(renderKitElement);
            List renderers = renderKitElement.getRendererElements();
            isAllSuitableJsfElement(renderers, RendererElement.class);
            RenderKitBean bean = new RenderKitBean(renderKitId, className,
                    renderers);
            renderLists_.add(bean);
        }
    }

    private String getRenderKitId(RenderKitElement renderKitElement) {
        String renderKitId = renderKitElement.getRenderKitId();
        if (renderKitId != null) {
            return renderKitId;
        }
        return RenderKitFactory.HTML_BASIC_RENDER_KIT;
    }

    public void assemble() {
        RenderKitFactory renderKitFactory = FactoryFinderUtil
                .getRenderKitFactory();
        for (Iterator itr = IteratorUtil.getIterator(renderLists_); itr
                .hasNext();) {
            RenderKitBean bean = (RenderKitBean) itr.next();
            String renderKitId = bean.getRenderKitId();
            RenderKit renderKit = createRenderKit(bean.getClassName());

            RendererElement rendererElement = null;
            while ((rendererElement = bean.getRendererElement()) != null) {
                String rendererClass = rendererElement.getRendererClass();
                Renderer renderer = createRenderer(rendererClass);
                renderKit.addRenderer(rendererElement.getComponentFamily(),
                        rendererElement.getRendererType(), renderer);
            }
            renderKitFactory.addRenderKit(renderKitId, renderKit);
        }
    }

    protected String getRenderKitClassName(RenderKitElement renderKitElement) {
        String className = renderKitElement.getRenderKitClass();
        if (className == null) {
            className = JsfConstants.DEFAULT_RENDERKIT_CLASS;
        }
        return className;
    }

    protected RenderKit createRenderKit(String className) {
        Class clazz = ClassUtil.forName(className);
        RenderKit renderKit = (RenderKit) DIContainerUtil
                .getComponentNoException(clazz);
        if (renderKit == null) {
            renderKit = (RenderKit) newInstance(className);
            S2Container container = SingletonS2ContainerFactory.getContainer();
            BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
            if (beanDesc.hasPropertyDesc("container")) {
                beanDesc.getPropertyDesc("container").setValue(renderKit,
                        container);
            }
        }
        return renderKit;
    }

    protected Renderer createRenderer(String className) {
        Renderer renderer = null;
        try {
            renderer = (Renderer) newInstance(className);
        } catch (Exception e) {
            logger_.warn("Exception " + e
                    + "occured when trying to create Renderer.");
        }
        return renderer;
    }

    List getRenderList() {
        return renderLists_;
    }

    static class RenderKitBean {

        private String renderKitId_;

        private String className_;

        private Iterator renderers_;

        public RenderKitBean(String renderKitId, String className,
                List rendererElements) {
            renderKitId_ = renderKitId;
            className_ = className;
            renderers_ = rendererElements.iterator();
        }

        public String getClassName() {
            return className_;
        }

        public String getRenderKitId() {
            return renderKitId_;
        }

        public boolean hasNext() {
            return renderers_.hasNext();
        }

        public RendererElement getRendererElement() {
            RendererElement element = null;
            if (hasNext()) {
                element = (RendererElement) renderers_.next();
            }
            return element;
        }
    }

}
