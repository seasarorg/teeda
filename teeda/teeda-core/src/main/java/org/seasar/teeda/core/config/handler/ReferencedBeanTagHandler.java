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
package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.ReferencedBeanElement;
import org.seasar.teeda.core.config.element.impl.ReferencedBeanElementImpl;
import org.xml.sax.Attributes;

/**
 * @author shot
 */
public class ReferencedBeanTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 1L;

    public ReferencedBeanTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        ReferencedBeanElement navigationCase = createReferencedBeanElement();
        context.push(navigationCase);
    }

    
    public void end(TagHandlerContext context, String body) {
        ReferencedBeanElement refBean = (ReferencedBeanElement)context.pop();
        FacesConfig config = (FacesConfig)context.peek();
        config.addReferencedBeanElement(refBean);
    }
    
    protected ReferencedBeanElement createReferencedBeanElement(){
        return new ReferencedBeanElementImpl();
    }
}
