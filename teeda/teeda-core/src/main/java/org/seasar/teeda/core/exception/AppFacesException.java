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
package org.seasar.teeda.core.exception;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;

/**
 * @author higa
 */
public class AppFacesException extends FacesException {

    private static final long serialVersionUID = 1L;

    private String messageCode;

    private Object[] args;

    private String message;

    private FacesMessage facesMessage;

    public AppFacesException() {
    }

    public AppFacesException(String messageCode) {
        this(messageCode, null, null);
    }

    public AppFacesException(String messageCode, Object[] args) {
        this(messageCode, args, null);
    }

    public AppFacesException(String messageCode, Object[] args, Throwable cause) {
        super(cause);
        final FacesContext context = FacesContext.getCurrentInstance();
        final FacesMessage facesMessage = FacesMessageUtil.getMessage(context,
                messageCode, args);
        String message = facesMessage.getDetail();
        if (message == null) {
            message = facesMessage.getSummary();
        }
        this.messageCode = messageCode;
        this.args = args;
        this.message = message;
        this.facesMessage = facesMessage;
    }

    public final String getMessageCode() {
        return messageCode;
    }

    public final Object[] getArgs() {
        return args;
    }

    public String getMessage() {
        return message;
    }

    public FacesMessage getFacesMessage() {
        return facesMessage;
    }

    public void setFacesMessage(FacesMessage facesMessage) {
        this.facesMessage = facesMessage;
    }

}