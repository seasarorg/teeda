package org.seasar.teeda.core.mock;

import java.io.OutputStream;
import java.io.Writer;

import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;


public class MockRenderKit extends RenderKit {

    /**
     *
     */

    public void addRenderer(String family, String renderType, Renderer renderer) {
    }

    /**
     *
     */

    public ResponseWriter createResponseWriter(Writer writer,
            String contentTypeList, String characterEncoding) {
        return null;
    }

    /**
     *
     */

    public ResponseStream createResponseStream(OutputStream outputstream) {
        return null;
    }

    /**
     *
     */

    public Renderer getRenderer(String family, String renderType) {
        return null;
    }

    /**
     *
     */

    public ResponseStateManager getResponseStateManager() {
        return null;
    }

}
