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
}
