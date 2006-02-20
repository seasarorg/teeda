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
package javax.faces.webapp;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author shot
 */
public abstract class UIComponentBodyTag extends UIComponentTag 
    implements BodyTag {

    protected BodyContent bodyContent = null;

    public UIComponentBodyTag() {
    }

    public int doAfterBody() throws JspException {
        return getDoAfterBodyValue();
    }
    
    public void doInitBody() throws JspException {
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
