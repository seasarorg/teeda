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

import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.teeda.ajax.AjaxConstants;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * 
 * @author shot
 *
 */
public class ContentsCreatorFactoryTest extends TeedaTestCase {

    public void testGetContentsCreator() throws Exception {
        MockHttpServletResponse response = getResponse();
        response.setContentType(AjaxConstants.CONTENT_TYPE_JSON);
        ContentsCreatorFactory.addContentsCreator(
                AjaxConstants.CONTENT_TYPE_JSON, new ContentsCreator() {
                    public Contents create(Object target) {
                        return new Contents() {
                            public String getContentsAsText() {
                                return "aaa";
                            }
                        };
                    }
                });
        ContentsCreator creator = ContentsCreatorFactory
                .getContentsCreator(response);
        assertNotNull(creator);
        assertEquals("aaa", creator.create("a").getContentsAsText());
    }

    public void testAdjustContentTypeIfNeed_contentTypeDefaultIsJSON()
            throws Exception {
        MockHttpServletResponse response = getResponse();
        response.setContentType(null);
        assertEquals(AjaxConstants.CONTENT_TYPE_JSON, ContentsCreatorFactory
                .adjustContentTypeIfNeed(response));
    }

    public void testAdjustContentTypeIfNeed_contentTypeConfigured()
            throws Exception {
        MockHttpServletResponse response = getResponse();
        response.setContentType("aaa");
        assertEquals("aaa", ContentsCreatorFactory
                .adjustContentTypeIfNeed(response));
    }

}
