package javax.faces.context;

import java.io.IOException;
import java.io.Writer;

import javax.faces.component.UIComponent;

public abstract class ResponseWriter extends Writer {

	public ResponseWriter() {
	}

	public abstract String getContentType();

	public abstract String getCharacterEncoding();

	public abstract void flush() throws IOException;

	public abstract void startDocument() throws IOException;

	public abstract void endDocument() throws IOException;

	public abstract void startElement(String s, UIComponent uicomponent)
			throws IOException;

	public abstract void endElement(String s) throws IOException;

	public abstract void writeAttribute(String s, Object obj, String s1)
			throws IOException;

	public abstract void writeURIAttribute(String s, Object obj, String s1)
			throws IOException;

	public abstract void writeComment(Object obj) throws IOException;

	public abstract void writeText(Object obj, String s) throws IOException;

	public abstract void writeText(char ac[], int i, int j) throws IOException;

	public abstract ResponseWriter cloneWithWriter(Writer writer);
}