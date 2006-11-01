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
package javax.faces.internal;

import javax.faces.application.FacesMessage;

import junit.framework.TestCase;

/**
 * @author shot
 */
public class FacesMessageResourceTest extends TestCase {

    public void tearDown() throws Exception {
        FacesMessageResource.removeAll();
    }

    public void testGetConverter() throws Exception {
        FacesMessage fm = new FacesMessage("a", "b");
        FacesMessageResource.addFacesMessage("#{a.b}", fm);
        FacesMessage facesMessage = FacesMessageResource
                .getFacesMessage("#{a.b}");
        assertNotNull(facesMessage);
        assertEquals("a", facesMessage.getSummary());
        assertEquals("b", facesMessage.getDetail());
    }

}
