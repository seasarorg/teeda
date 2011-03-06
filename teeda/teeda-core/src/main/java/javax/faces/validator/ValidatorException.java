/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package javax.faces.validator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;

/**
 * @author shot
 */
public class ValidatorException extends FacesException {

    private static final long serialVersionUID = 1L;

    private FacesMessage facesMessage = null;

    private String messageId;

    private Object[] args;

    public ValidatorException(FacesMessage facesMessage) {
        this(facesMessage, null);
    }

    public ValidatorException(FacesMessage facesMessage, Throwable cause) {
        super(facesMessage.getDetail(), cause);
        this.facesMessage = facesMessage;
    }

    public ValidatorException(FacesMessage facesMessage, String messageId,
            Object[] args) {
        this(facesMessage);
        this.messageId = messageId;
        this.args = args;
    }

    public ValidatorException(FacesMessage facesMessage, Throwable cause,
            String messageId, Object[] args) {
        this(facesMessage, cause);
        this.messageId = messageId;
        this.args = args;
    }

    public FacesMessage getFacesMessage() {
        return facesMessage;
    }

    public String getMessageId() {
        return messageId;
    }

    public Object[] getArgs() {
        return args;
    }
}