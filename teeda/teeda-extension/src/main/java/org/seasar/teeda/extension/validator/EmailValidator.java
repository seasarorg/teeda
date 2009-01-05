/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import org.seasar.framework.util.StringUtil;

/**
 * @author shot
 */
public class EmailValidator extends TRegularExpressionValidator {

    public static final String VALIDATOR_ID = EmailValidator.class.getName();

    public static final String EMAIL_VALIDATOR_MESSAGE_ID = VALIDATOR_ID
            + ".INVALID";

    public static final String DEFAULT_EXPRESSION = "^\\p{ASCII}+@(([-a-z0-9]+\\.)*[a-z]+|\\[\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\])";

    public EmailValidator() {
        setPattern(DEFAULT_EXPRESSION);
    }

    public String getMessageId() {
        return !StringUtil.isEmpty(messageId) ? messageId
                : EMAIL_VALIDATOR_MESSAGE_ID;
    }

}
