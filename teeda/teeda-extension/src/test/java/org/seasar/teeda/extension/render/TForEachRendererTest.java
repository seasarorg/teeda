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
package org.seasar.teeda.extension.render;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.seasar.teeda.extension.component.TForEach;

/**
 * @author higa
 * @author manhole
 */
public class TForEachRendererTest extends RendererTest {

    public void testDecode() throws Exception {
        TForEachRenderer renderer = new TForEachRenderer();
        FacesContext context = getFacesContext();
        Map param = context.getExternalContext().getRequestParameterMap();
        param.put("hogeItems-1:0:aaa", "aaa");
        param.put("hogeItems-1:1:aaa", "bbb");
        param.put("hogeItems-1:2:aaa", "ccc");
        TForEach t = new TForEach() {

            public String getClientId(FacesContext context) {
                return "hogeItems";
            }

            public Integer bindRowIndex(FacesContext context, Integer rowIndex) {
                return null;
            }

        };
        renderer.decode(context, t);
        assertTrue(t.getRowSize() == 3);
    }

    protected Renderer createRenderer() {
        return new TForEachRenderer();
    }

}
