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
package javax.faces.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 *
 */
public class ConvertUtilTest extends TeedaTestCase {

    //This testcase has been deprecated.
    public void testWrappedByConverterException_shouldFindMessageRecursively()
            throws Exception {
        Converter converter = new ChildConverter();
        MockFacesContext context = getFacesContext();
        context.getApplication().setMessageBundle(
                "javax.faces.internal.TestMessages");
        Object[] args = new Object[0];
        Throwable t = new Exception();
        ConverterException e = ConvertUtil.wrappedByConverterException(
                converter, context, args, t);
        assertEquals("AAA", e.getMessage());
    }

    public void testWrappedByConverterException_shouldFindMessageRecursivelyChildIsPrior()
            throws Exception {
        Converter converter = new Child2Converter();
        MockFacesContext context = getFacesContext();
        context.getApplication().setMessageBundle(
                "javax.faces.internal.TestMessages");
        Object[] args = new Object[0];
        Throwable t = new Exception();
        ConverterException e = ConvertUtil.wrappedByConverterException(
                converter, context, args, t);
        assertEquals("BBB", e.getMessage());
    }

    public static class ParentConverter implements Converter {

        public Object getAsObject(FacesContext context, UIComponent component,
                String value) throws ConverterException {
            return null;
        }

        public String getAsString(FacesContext context, UIComponent component,
                Object value) throws ConverterException {
            return null;
        }

    }

    public static class ChildConverter extends ParentConverter {

    }

    public static class Child2Converter extends ParentConverter {

    }

}
