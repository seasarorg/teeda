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
package org.seasar.teeda.ajax.creator;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.seasar.teeda.ajax.AjaxConstants;

/**
 * @author shot
 * 
 */
public class ContentsCreatorFactory {

    private static Map creators = new HashMap();

    private ContentsCreatorFactory() {
    }

    public static ContentsCreator getContentsCreator(
            HttpServletResponse response) {
        String contentType = adjustContentTypeIfNeed(response);
        return getContentsCreator(contentType);
    }

    public static String adjustContentTypeIfNeed(HttpServletResponse response) {
        String contentType = response.getContentType();
        if (contentType == null) {
            contentType = AjaxConstants.CONTENT_TYPE_JSON;
            response.setContentType(contentType);
        }
        return contentType;
    }
    
    private static ContentsCreator getContentsCreator(String contentType) {
        ContentsCreator creator = (ContentsCreator) creators.get(contentType);
        if(creator == null) {
            creator = EmptyContentsCreator.getInstance();
        }
        return creator;
    }
    
    public static void addContentsCreator(String contentType,
            ContentsCreator creator) {
        creators.put(contentType, creator);
    }

    public static void clearContentsCreator() {
        creators.clear();
    }
}
