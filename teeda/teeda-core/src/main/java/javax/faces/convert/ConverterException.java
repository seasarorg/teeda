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
package javax.faces.convert;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;

/**
 * @author shot
 */
public class ConverterException extends FacesException {

    private static final long serialVersionUID = 1L;

    private FacesMessage facesMessage_ = null;

    public ConverterException() {
        super();
    }

    public ConverterException(String message) {
        super(message);
    }

    public ConverterException(Throwable cause) {
        super(cause);
    }

    public ConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConverterException(FacesMessage facesMessage) {
        super(facesMessage.getDetail());
        facesMessage_ = facesMessage;
    }

    public ConverterException(FacesMessage facesMessage, Throwable cause) {
        super(facesMessage.getDetail(), cause);
        facesMessage_ = facesMessage;
    }

    public FacesMessage getFacesMessage() {
        return facesMessage_;
    }

}
