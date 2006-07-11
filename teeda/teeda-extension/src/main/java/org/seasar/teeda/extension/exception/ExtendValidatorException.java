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
package org.seasar.teeda.extension.exception;

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

/**
 * @author shot
 *
 */
public class ExtendValidatorException extends ValidatorException {

    private static final long serialVersionUID = 1L;

    private FacesMessage facesMessage = null;

    private String[] messsageIds = null;

    public ExtendValidatorException(FacesMessage facesMessage) {
        this(facesMessage, null, null);
    }

    public ExtendValidatorException(FacesMessage facesMessage, Throwable cause) {
        this(facesMessage, cause, null);
    }

    public ExtendValidatorException(FacesMessage facesMessage,
            String[] messageIds) {
        this(facesMessage, null, messageIds);
    }

    public ExtendValidatorException(FacesMessage facesMessage, Throwable cause,
            String[] messageIds) {
        super(facesMessage, cause);
        this.facesMessage = facesMessage;
        this.messsageIds = messageIds;
    }

    public FacesMessage getFacesMessage() {
        return facesMessage;
    }

    public String[] getMesssageIds() {
        return messsageIds;
    }

}
