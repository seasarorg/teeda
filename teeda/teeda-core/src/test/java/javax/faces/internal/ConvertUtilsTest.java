package javax.faces.internal;

import javax.faces.component.html.HtmlInputText;
import javax.faces.convert.IntegerConverter;

import junitx.framework.ArrayAssert;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class ConvertUtilsTest extends TeedaTestCase {

    public void testCreateConversionMessage() throws Exception {
        IntegerConverter converter = new IntegerConverter();
        assertEquals(converter.getClass().getName() + ".CONVERSION",
                ConvertUtil.createConversionMessage(converter));
    }

    public void testCreateExceptionMessageArgs_withComponentId()
            throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("aaa");
        Object[] args = ConvertUtil
                .createExceptionMessageArgs(component, "bbb");
        ArrayAssert.assertEquals(new String[] { "aaa", "bbb" }, args);
    }

    public void testCreateExceptionMessageArgs_withLabel() throws Exception {
        HtmlInputText component = new HtmlInputText();
        component.setId("aaa");
        component.setLabel("ccc");
        Object[] args = ConvertUtil
                .createExceptionMessageArgs(component, "bbb");
        ArrayAssert.assertEquals(new String[] { "ccc", "bbb" }, args);
    }

}
