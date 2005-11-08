package javax.faces.webapp;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;


public class FacetTag extends TagSupport {

    private String name_ = null;
    
    public FacetTag(){
    }

    public String getName() {
        return name_;
    }

    public void setName(String name){
        name_ = name;
    }
    
    public void release() {
        super.release();
        name_ = null;
    }

    public int doStartTag() throws JspException{
        return EVAL_BODY_INCLUDE;
    }
}
