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
package org.seasar.teeda.extension.convert;

import java.math.BigDecimal;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockUIInput;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TBigDecimalConverterTest extends TeedaTestCase {

    public void testGetAsObject_convertSuccess() throws Exception {
        TBigDecimalConverter converter = new TBigDecimalConverter();
        String value = "123000000000";
        Object o = converter.getAsObject(getFacesContext(),
                new MockUIComponent(), value);
        assertTrue(o instanceof BigDecimal);
        BigDecimal b = (BigDecimal) o;
        assertEquals(Long.valueOf(value).longValue(), b.longValue());
    }

    public void testGetAsObject_notTargeted() throws Exception {
        TBigDecimalConverter converter = new TBigDecimalConverter();
        String value = "123000000000";
        converter.setTarget("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "bbb");
        MockUIInput mockUIInput = new MockUIInput();
        Object o = converter.getAsObject(getFacesContext(), mockUIInput, value);
        assertEquals(value, o);
        assertNull(mockUIInput.getValueBinding("value"));
    }

    public void testGetAsString() throws Exception {
        TBigDecimalConverter converter = new TBigDecimalConverter();
        converter.setRoundingMode(new Integer(-1));
        converter.setScale(new Integer(-1));
        converter.setPattern("###,###,###.###");
        String s = converter.getAsString(getFacesContext(),
                new MockUIComponent(), new BigDecimal("123456789.100"));
        assertEquals("123,456,789.1", s);
    }

    public void testGetAsString_withRoundingMode() throws Exception {
        TBigDecimalConverter converter = new TBigDecimalConverter();
        converter.setPattern("###,###,###.###");
        converter.setRoundingMode(new Integer(BigDecimal.ROUND_HALF_UP));
        String s = converter.getAsString(getFacesContext(),
                new MockUIComponent(), new BigDecimal("123456789.1555"));
        assertEquals("123,456,789.156", s);
    }

    public void testConvertTargetPointed() throws Exception {
        TBigDecimalConverter converter = new TBigDecimalConverter();
        converter.setPattern("###,###,###.###");
        converter.setTarget("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "aaa");
        String s = converter.getAsString(getFacesContext(),
                new MockUIComponent(), new BigDecimal("123456789.100"));
        assertEquals("123,456,789.1", s);
    }

    public void testConvertTargetPointed2() throws Exception {
        TBigDecimalConverter converter = new TBigDecimalConverter();
        converter.setPattern("###,###,###.###");
        converter.setTarget("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "aaa");
        try {
            converter.getAsString(getFacesContext(), new MockUIComponent(),
                    new BigDecimal("AAA"));
        } catch (Exception expected) {
            assertNotNull(expected);
        }
    }

    // エラーで判断せずにtargetじゃなければコンバート処理しない
    public void testConvertTargetNotPointed() throws Exception {
        TBigDecimalConverter converter = new TBigDecimalConverter();
        converter.setPattern("###,###,###.###");
        converter.setTarget("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "bbb");
        String s = converter.getAsString(getFacesContext(),
                new MockUIComponent(), new BigDecimal("123456789.100"));
        assertEquals(null, s);
    }

    public void testConvertTargetNotPointed2() throws Exception {
        TBigDecimalConverter converter = new TBigDecimalConverter();
        converter.setPattern("###,###,###.###");
        converter.setTarget("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "bbb");
        String s = converter.getAsString(getFacesContext(),
                new MockUIComponent(), "AAA");
        assertEquals("AAA", s);
    }
}
