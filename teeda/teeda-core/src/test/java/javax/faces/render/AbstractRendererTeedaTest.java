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
package javax.faces.render;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.render.ComponentIdLookupStrategy;
import org.seasar.teeda.core.render.DefaultComponentIdLookupStrategy;
import org.seasar.teeda.core.render.html.support.RenderAttributesImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author manhole
 */
public abstract class AbstractRendererTeedaTest extends TeedaTestCase {

    private ComponentIdLookupStrategy idLookupStrategy;

    private static RenderAttributesImpl defaultRenderAttributes;

    private RenderAttributesImpl renderAttributes = defaultRenderAttributes;

    static {
        defaultRenderAttributes = new RenderAttributesImpl();
        defaultRenderAttributes.initialize();
    }

    protected void setUp() throws Exception {
        super.setUp();
        idLookupStrategy = new DefaultComponentIdLookupStrategy();
    }

    protected void encodeByRenderer(Renderer renderer, UIComponent component)
            throws IOException {
        encodeByRenderer(renderer, getFacesContext(), component);
    }

    protected void encodeByRenderer(Renderer renderer, FacesContext context,
            UIComponent component) throws IOException {
        renderer.encodeBegin(context, component);
        if (renderer.getRendersChildren()) {
            renderer.encodeChildren(context, component);
        }
        renderer.encodeEnd(context, component);
    }

    protected ComponentIdLookupStrategy getComponentIdLookupStrategy() {
        return idLookupStrategy;
    }

    protected void encodeComponent(FacesContext context, UIComponent component)
            throws IOException {
        component.encodeBegin(context);
        if (component.getRendersChildren()) {
            component.encodeChildren(context);
        } else {
            encodeDescendantComponent(context, component);
        }
        component.encodeEnd(context);
    }

    protected void encodeDescendantComponent(FacesContext context,
            UIComponent component) throws IOException {
        for (final Iterator it = component.getChildren().iterator(); it
                .hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child.isRendered()) {
                encodeComponent(context, child);
            }
        }
    }

    protected RenderAttributesImpl getRenderAttributes() {
        return renderAttributes;
    }

    protected void setRenderAttributes(RenderAttributesImpl renderAttributes) {
        this.renderAttributes = renderAttributes;
    }

}
