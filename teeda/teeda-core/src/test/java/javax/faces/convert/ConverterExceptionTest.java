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
package javax.faces.convert;

import javax.faces.application.FacesMessage;

import junit.framework.TestCase;

public class ConverterExceptionTest extends TestCase {

    public void testConverterException() {
        ConverterException e = new ConverterException();
        assertNull(e.getFacesMessage());
    }

    public void testConverterExceptionString() {
        ConverterException e = new ConverterException("a");
        assertEquals("a", e.getMessage());
    }

    public void testConverterExceptionThrowable() {
        Throwable t = new Throwable("a");
        ConverterException e = new ConverterException(t);
        assertEquals(t, e.getCause());
        assertEquals(t.getMessage(), e.getCause().getMessage());
    }

    public void testConverterExceptionStringThrowable() {
        String s = "a";
        Throwable t = new Throwable("b");
        ConverterException e = new ConverterException(s, t);

        assertEquals(s, e.getMessage());

        assertEquals(t, e.getCause());
        assertEquals(t.getMessage(), e.getCause().getMessage());
    }

    public void testConverterExceptionFacesMessage() {
        FacesMessage facesMessage = new FacesMessage("summary", "detail");
        ConverterException e = new ConverterException(facesMessage);

        assertNotNull(e.getFacesMessage());
        assertEquals(facesMessage, e.getFacesMessage());
    }

    public void testConverterExceptionFacesMessageThrowable() {
        FacesMessage facesMessage = new FacesMessage("summary", "detail");
        Throwable t = new Throwable("t");
        ConverterException e = new ConverterException(facesMessage, t);
        assertNotNull(e.getFacesMessage());
        assertEquals(facesMessage, e.getFacesMessage());
        assertEquals(t, e.getCause());
    }

    public void testGetFacesMessage() {
        String s = "summary";
        String d = "detail";
        FacesMessage facesMessage = new FacesMessage(s, d);
        ConverterException e = new ConverterException(facesMessage);

        assertNotNull(e.getFacesMessage());
        assertEquals(facesMessage, e.getFacesMessage());
        assertEquals(facesMessage.getDetail(), e.getMessage());
    }

}
