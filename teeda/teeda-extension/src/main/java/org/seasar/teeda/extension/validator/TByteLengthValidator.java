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
package org.seasar.teeda.extension.validator;

import java.io.UnsupportedEncodingException;

import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.ValidatorException;

import org.seasar.framework.util.StringUtil;

/**
 * @author shot
 */
public class TByteLengthValidator extends LengthValidator {

    public static final String VALIDATOR_ID = "teeda.core.ByteLength";

    public static final String MAXIMUM_MESSAGE_ID = "org.seasar.teeda.core.validator.ByteLengthValidator.MAXIMUM";

    public static final String MINIMUM_MESSAGE_ID = "org.seasar.teeda.core.validator.ByteLengthValidator.MINIMUM";

    public static final String ENCODE_MESSAGE_ID = "org.seasar.teeda.core.validator.ByteLengthValidator.ENCODE";

    private String charSet_ = null;

    protected int getConvertedValueLength(Object value) {
        String str = null;
        if (value instanceof String) {
            str = (String) value;
        } else {
            str = value.toString();
        }
        if (StringUtil.isEmpty(charSet_)) {
            return str.getBytes().length;
        }
        try {
            return getBytes(str, charSet_).length;
        } catch (UnsupportedEncodingException e) {
            Object[] args = new Object[] { value, charSet_ };
            throw new ValidatorException(FacesMessageUtil.getMessage(
                    FacesContext.getCurrentInstance(), ENCODE_MESSAGE_ID, args));
        }

    }

    public String getCharSet() {
        return charSet_;
    }

    public void setCharSet(String charSet) {
        charSet_ = charSet;
    }

    private static byte[] getBytes(String value, String charSet)
            throws UnsupportedEncodingException {
        return value.getBytes(charSet);
    }

    protected String getMaximumMessageId() {
        return MAXIMUM_MESSAGE_ID;
    }

    protected String getMinimumMessageId() {
        return MINIMUM_MESSAGE_ID;
    }

}
