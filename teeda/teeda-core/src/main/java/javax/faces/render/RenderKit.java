package javax.faces.render;

import java.io.Writer;
import java.io.OutputStream;

import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;

public abstract class RenderKit {

	public abstract void addRenderer(String family, String renderType,
			Renderer renderer);

	public abstract ResponseWriter createResponseWriter(Writer writer,
			String contentTypeList, String characterEncoding);

	public abstract ResponseStream createResponseStream(
			OutputStream outputstream);

	public abstract Renderer getRenderer(String family, String renderType);

	public abstract ResponseStateManager getResponseStateManager();

}
