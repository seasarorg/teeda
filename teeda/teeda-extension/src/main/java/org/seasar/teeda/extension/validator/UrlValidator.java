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
package org.seasar.teeda.extension.validator;

import org.seasar.framework.util.StringUtil;

/**
 * @author shot
 */
public class UrlValidator extends TRegularExpressionValidator {

    public static final String VALIDATOR_ID = UrlValidator.class.getName();

    public static final String URL_VALIDATOR_MESSAGE_ID = VALIDATOR_ID
            + ".INVALID";

    public static final String DEFAULT_EXPRESSION = "https?:\\/\\/[-_.!~*'()a-zA-Z0-9;/?:@&=+$,%#]+";

    public UrlValidator() {
        setPattern(DEFAULT_EXPRESSION);
    }

    public String getMessageId() {
        return !StringUtil.isEmpty(messageId) ? messageId
                : URL_VALIDATOR_MESSAGE_ID;
    }

}
