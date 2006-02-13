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
package org.seasar.teeda.core.config.faces.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.faces.element.RenderKitElement;
import org.seasar.teeda.core.config.faces.element.impl.RenderKitElementImpl;
import org.xml.sax.Attributes;

/**
 * @author shot
 */
public class RenderKitTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 3258125877739861555L;

    public RenderKitTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        RenderKitElement rule = createRenderKitElement();
        context.push(rule);
    }

    public void end(TagHandlerContext context, String body) {
        RenderKitElement rule = (RenderKitElement)context.pop();
        FacesConfig facesConfig = (FacesConfig)context.peek();
        facesConfig.addRenderKitElement(rule);
    }
    
    protected RenderKitElement createRenderKitElement(){
        return new RenderKitElementImpl();
    }
}
