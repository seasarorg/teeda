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
package org.seasar.teeda.core.render.html;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.AssertionUtil;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;

import org.seasar.teeda.core.context.html.HtmlResponseWriter;
import org.seasar.teeda.core.render.AbstractRenderKit;
import org.seasar.teeda.core.util.HtmlRenderKitUtil;

/**
 * @author shot
 * @author manhole
 */
public class HtmlRenderKitImpl extends AbstractRenderKit {

    private Map renderers_ = new HashMap();

    private ResponseStateManager responseStateManager_;

    private ResponseWriter responseWriter_;

    public HtmlRenderKitImpl() {
    }

    public void addRenderer(String family, String renderType, Renderer renderer) {
        AssertionUtil.assertNotNull("family", family);
        AssertionUtil.assertNotNull("renderType", renderType);
        AssertionUtil.assertNotNull("renderer", renderer);
        String key = getGeneratedKey(family, renderType);
        renderers_.put(key, renderer);
    }

    public Renderer getRenderer(String family, String renderType) {
        AssertionUtil.assertNotNull("family", family);
        AssertionUtil.assertNotNull("renderType", renderType);
        String key = getGeneratedKey(family, renderType);
        return (Renderer) renderers_.get(key);
    }

    public ResponseStream createResponseStream(final OutputStream out) {
        return new ResponseStream() {

            public void write(int b) throws IOException {
                out.write(b);
            }

            public void close() throws IOException {
                out.close();
            }

            public void flush() throws IOException {
                out.flush();
            }

            public void write(byte[] bytes) throws IOException {
                out.write(bytes, 0, bytes.length);
            }

            public void write(byte[] bytes, int off, int len)
                    throws IOException {
                out.write(bytes, off, len);
            }

        };
    }

    public ResponseWriter createResponseWriter(Writer writer,
            String contentTypeList, String characterEncoding) {
        HtmlResponseWriter htmlResponseWriter = null;
        if (responseWriter_ instanceof HtmlResponseWriter) {
            htmlResponseWriter = (HtmlResponseWriter) responseWriter_;
        } else {
            htmlResponseWriter = new HtmlResponseWriter();
        }
        htmlResponseWriter.setWriter(writer);
        String contentType = HtmlRenderKitUtil.getContentType(contentTypeList);
        if (contentType == null) {
            throw new IllegalArgumentException("contentType");
        }
        htmlResponseWriter.setContentType(contentType);
        htmlResponseWriter.setCharacterEncoding(characterEncoding);
        return htmlResponseWriter;
    }

    public ResponseStateManager getResponseStateManager() {
        return responseStateManager_;
    }

    protected void setResponseWriter(ResponseWriter responseWriter) {
        responseWriter_ = responseWriter;
    }

    protected String getGeneratedKey(String family, String renderType) {
        return family + "." + renderType;
    }

    protected void setResponseStateManager(
            ResponseStateManager responseStateManager) {
        responseStateManager_ = responseStateManager;
    }

}
