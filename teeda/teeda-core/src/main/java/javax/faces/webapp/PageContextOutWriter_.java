package javax.faces.webapp;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.PageContext;

class PageContextOutWriter_ extends Writer {

    private PageContext pageContext_;

    public PageContextOutWriter_(PageContext pageContext) {
        pageContext_ = pageContext;
    }

    public void close() throws IOException {
        pageContext_.getOut().close();
    }

    public void flush() throws IOException {
        pageContext_.getOut().flush();
    }

    public void write(char cbuf[], int off, int len) throws IOException {
        pageContext_.getOut().write(cbuf, off, len);
    }

    public void write(int c) throws IOException {
        pageContext_.getOut().write(c);
    }

    public void write(char cbuf[]) throws IOException {
        pageContext_.getOut().write(cbuf);
    }

    public void write(String str) throws IOException {
        pageContext_.getOut().write(str);
    }

    public void write(String str, int off, int len) throws IOException {
        pageContext_.getOut().write(str, off, len);
    }
}
