/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.util;

import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author higa
 * 
 */
public class EmptyElementUtil implements JsfConstants {

    private static final String[] EMPTY_ELEMENT_TAGNAMES = { BASE_ELEM,
            META_ELEM, LINK_ELEM, FRAME_ELEM, HR_ELEM, BR_ELEM, BASEFONT_ELEM,
            PARAM_ELEM, IMG_ELEM, AREA_ELEM, INPUT_ELEM, ISINDEX_ELEM, COL_ELEM };

    protected EmptyElementUtil() {
    }

    public static boolean isEmptyElement(String tagName) {
        if (StringUtil.isEmpty(tagName)) {
            throw new EmptyRuntimeException("tagName");
        }
        String name = tagName.toLowerCase();
        for (int i = 0; i < EMPTY_ELEMENT_TAGNAMES.length; ++i) {
            if (EMPTY_ELEMENT_TAGNAMES[i].equals(name)) {
                return true;
            }
        }
        return false;
    }
}