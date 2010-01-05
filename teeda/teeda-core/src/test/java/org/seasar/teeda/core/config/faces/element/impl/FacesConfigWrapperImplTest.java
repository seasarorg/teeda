/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.teeda.core.config.faces.element.RenderKitElement;
import org.seasar.teeda.core.config.faces.element.RendererElement;

public class FacesConfigWrapperImplTest extends TestCase {

    public void testRenderKitElements1() throws Exception {
        // ## Arrange ##
        RenderKitElementImpl renderKitElement = new RenderKitElementImpl();
        renderKitElement.setRenderKitId("renderKitId1");
        renderKitElement.setRenderKitClass("renderKitClass1");

        RendererElementImpl rendererElement = new RendererElementImpl();
        rendererElement.setComponentFamily("family1");
        rendererElement.setRendererType("type1");
        rendererElement.setRendererClass("class1");

        renderKitElement.addRendererElement(rendererElement);
        FacesConfigImpl facesConfig = new FacesConfigImpl();
        facesConfig.addRenderKitElement(renderKitElement);

        List configs = new ArrayList();
        configs.add(facesConfig);
        FacesConfigWrapperImpl configWrapper = new FacesConfigWrapperImpl(
                configs);

        // ## Act ##
        Map renderKitElements = configWrapper.getRenderKitElements();
        RenderKitElement renderKit = (RenderKitElement) renderKitElements
                .get("renderKitId1");

        // ## Assert ##
        List rendererElements = renderKit.getRendererElements();
        assertEquals(1, rendererElements.size());
        RendererElement element = (RendererElement) rendererElements.get(0);
        assertEquals("family1", element.getComponentFamily());
        assertEquals("type1", element.getRendererType());
        assertEquals("class1", element.getRendererClass());
    }

    public void testRenderKitElements_SameRenderKitId() throws Exception {
        // ## Arrange ##
        List configs = new ArrayList();
        {
            RenderKitElementImpl renderKitElement = new RenderKitElementImpl();
            renderKitElement.setRenderKitId("renderKitId1");
            renderKitElement.setRenderKitClass("renderKitClass1");

            RendererElementImpl rendererElement = new RendererElementImpl();
            rendererElement.setComponentFamily("family1");
            rendererElement.setRendererType("type1");
            rendererElement.setRendererClass("class1");

            renderKitElement.addRendererElement(rendererElement);
            FacesConfigImpl facesConfig = new FacesConfigImpl();
            facesConfig.addRenderKitElement(renderKitElement);

            configs.add(facesConfig);
        }

        {
            RenderKitElementImpl renderKitElement = new RenderKitElementImpl();
            renderKitElement.setRenderKitId("renderKitId1");
            renderKitElement.setRenderKitClass("renderKitClass1");

            RendererElementImpl rendererElement = new RendererElementImpl();
            rendererElement.setComponentFamily("family2");
            rendererElement.setRendererType("type2");
            rendererElement.setRendererClass("class2");

            renderKitElement.addRendererElement(rendererElement);
            FacesConfigImpl facesConfig = new FacesConfigImpl();
            facesConfig.addRenderKitElement(renderKitElement);

            configs.add(facesConfig);
        }

        FacesConfigWrapperImpl configWrapper = new FacesConfigWrapperImpl(
                configs);

        // ## Act ##
        Map renderKitElements = configWrapper.getRenderKitElements();
        RenderKitElement renderKit = (RenderKitElement) renderKitElements
                .get("renderKitId1");

        // ## Assert ##
        List rendererElements = renderKit.getRendererElements();
        assertEquals(2, rendererElements.size());
        {
            RendererElement element = (RendererElement) rendererElements.get(0);
            assertEquals("family1", element.getComponentFamily());
            assertEquals("type1", element.getRendererType());
            assertEquals("class1", element.getRendererClass());
        }
        {
            RendererElement element = (RendererElement) rendererElements.get(1);
            assertEquals("family2", element.getComponentFamily());
            assertEquals("type2", element.getRendererType());
            assertEquals("class2", element.getRendererClass());
        }
    }

    public void testRenderKitElements_NoSetRenderKitId_SameFacesConfig()
            throws Exception {
        // ## Arrange ##
        List configs = new ArrayList();
        RenderKitElementImpl renderKitElement1 = new RenderKitElementImpl();
        RendererElementImpl rendererElement1 = new RendererElementImpl();
        final String renderKitId = renderKitElement1.getRenderKitId();
        rendererElement1.setComponentFamily("family1");
        rendererElement1.setRendererType("type1");
        rendererElement1.setRendererClass("class1");
        renderKitElement1.addRendererElement(rendererElement1);

        RenderKitElementImpl renderKitElement2 = new RenderKitElementImpl();
        RendererElementImpl rendererElement2 = new RendererElementImpl();
        rendererElement2.setComponentFamily("family2");
        rendererElement2.setRendererType("type2");
        rendererElement2.setRendererClass("class2");
        renderKitElement2.addRendererElement(rendererElement2);

        FacesConfigImpl facesConfig = new FacesConfigImpl();
        facesConfig.addRenderKitElement(renderKitElement1);
        facesConfig.addRenderKitElement(renderKitElement2);

        configs.add(facesConfig);

        FacesConfigWrapperImpl configWrapper = new FacesConfigWrapperImpl(
                configs);

        // ## Act ##
        Map renderKitElements = configWrapper.getRenderKitElements();
        RenderKitElement renderKit = (RenderKitElement) renderKitElements
                .get(renderKitId);

        // ## Assert ##
        List rendererElements = renderKit.getRendererElements();
        assertEquals(2, rendererElements.size());
        RendererElement element1 = (RendererElement) rendererElements.get(0);
        assertEquals("family1", element1.getComponentFamily());
        assertEquals("type1", element1.getRendererType());
        assertEquals("class1", element1.getRendererClass());
        RendererElement element2 = (RendererElement) rendererElements.get(1);
        assertEquals("family2", element2.getComponentFamily());
        assertEquals("type2", element2.getRendererType());
        assertEquals("class2", element2.getRendererClass());
    }
}
