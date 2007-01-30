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

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TBigDecimalConverterTest extends TeedaTestCase {

    public void testGetAsString() throws Exception {
        TBigDecimalConverter converter = new TBigDecimalConverter();
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
}
