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

    public abstract void startElement(String name,
        UIComponent componentForElement) throws IOException;

    public abstract void endElement(String name) throws IOException;

    public abstract void writeAttribute(String name, Object value,
        String componentPropertyName) throws IOException;

    public abstract void writeURIAttribute(String name, Object value,
        String componentPropertyName) throws IOException;

    public abstract void writeComment(Object comment) throws IOException;

    public abstract void writeText(Object text, String property)
        throws IOException;

    public abstract void writeText(char text[], int off, int len)
        throws IOException;

    public abstract ResponseWriter cloneWithWriter(Writer writer);

}