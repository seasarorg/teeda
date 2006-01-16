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
package org.seasar.teeda.core.render.html;

import java.io.OutputStream;
import java.io.Writer;

import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;

/**
 * @author shot
 */
public class HtmlRenderKitImpl extends RenderKit {

    //TODO impl this
    public void addRenderer(String family, String renderType, Renderer renderer) {
        // TODO Auto-generated method stub

    }

    public ResponseWriter createResponseWriter(Writer writer,
            String contentTypeList, String characterEncoding) {
        // TODO Auto-generated method stub
        return null;
    }

    public ResponseStream createResponseStream(OutputStream outputstream) {
        // TODO Auto-generated method stub
        return null;
    }

    public Renderer getRenderer(String family, String renderType) {
        // TODO Auto-generated method stub
        return null;
    }

    public ResponseStateManager getResponseStateManager() {
        // TODO Auto-generated method stub
        return null;
    }

}
