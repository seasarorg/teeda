package javax.faces.webapp;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

public abstract class UIComponentBodyTag extends UIComponentTag 
    implements BodyTag {

    protected BodyContent bodyContent = null;

    public UIComponentBodyTag() {
    }

    public int doAfterBody() throws JspException {
        return getDoAfterBodyValue();
    }

    public void release() {
        bodyContent = null;
        super.release();
    }

    public void setBodyContent(BodyContent bodyContent) {
        this.bodyContent = bodyContent;
    }

    public BodyContent getBodyContent() {
        return bodyContent;
    }

    public JspWriter getPreviousOut() {
        return bodyContent.getEnclosingWriter();
    }

    protected int getDoAfterBodyValue() throws JspException {
        return SKIP_BODY;
    }

    protected int getDoStartValue() throws JspException {
        return EVAL_BODY_BUFFERED;
    }
}
