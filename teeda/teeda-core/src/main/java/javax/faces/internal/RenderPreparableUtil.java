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
package javax.faces.internal;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.render.RenderPreparableRenderer;

/**
 * @author higa
 *
 */
public abstract class RenderPreparableUtil {

    protected RenderPreparableUtil() {
    }

    public static void encodePrepareForRenderer(FacesContext context,
            UIComponent component) throws IOException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        Renderer renderer = UIComponentUtil.getRenderer(context, component);
        if (renderer != null && renderer instanceof RenderPreparableRenderer) {
            ((RenderPreparableRenderer) renderer).encodePrepare(context,
                    component);
        }
    }

    public static void encodePrepareForComponent(FacesContext context,
            UIComponent component) throws IOException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        final String componentName = component.getClass().getName();
        //TEEDA-264
        if (!component.isRendered() && !(componentName.endsWith("TCondition"))) {
            return;
        }
        if ((component instanceof RenderPreparable)) {
            ((RenderPreparable) component).encodePrepare(context);
        }
        if (component.getChildCount() > 0) {
            for (Iterator it = component.getChildren().iterator(); it.hasNext();) {
                UIComponent child = (UIComponent) it.next();
                encodePrepareForComponent(context, child);
            }
        }
    }
}