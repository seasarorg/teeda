/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.mock;

import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;

/**
 * @author shot
 */
public class MockRenderKitImpl extends MockRenderKit {

    private Map renderers_ = new HashMap();

    private ResponseStream responseStream_;

    private ResponseWriter responseWriter_;

    private ResponseStateManager responseStateManager_;

    public void addRenderer(String family, String renderType, Renderer renderer) {
        renderers_.put(family + "." + renderType, renderer);
    }

    public ResponseWriter createResponseWriter(Writer writer,
            String contentTypeList, String characterEncoding) {
        return responseWriter_;
    }

    public ResponseStream createResponseStream(final OutputStream out) {
        return responseStream_;
    }

    public Renderer getRenderer(String family, String renderType) {
        return (Renderer) renderers_.get(family + "." + renderType);
    }

    public ResponseStateManager getResponseStateManager() {
        return responseStateManager_;
    }

    public void setResponseStream(ResponseStream responseStream) {
        responseStream_ = responseStream;
    }

    public void setResponseWriter(ResponseWriter responseWriter) {
        responseWriter_ = responseWriter;
    }

    public void setResponseStateManager(
            ResponseStateManager responseStateManager) {
        responseStateManager_ = responseStateManager;
    }

}
