package javax.faces.component;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * TODO test saveState, restoreState
 */
public class TestUIOutput extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestUIOutput.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Constructor for TestUIOutput.
	 * @param arg0
	 */
	public TestUIOutput(String arg0) {
		super(arg0);
	}

	public void testGetFamily(){
		UIOutput output = new UIOutput();
		assertEquals(UIOutput.COMPONENT_FAMILY, output.getFamily());
	}
	
	public void testSetConverter(){
		UIOutput output = new UIOutput();
		Converter converter = new Converter(){

			public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
				return null;
			}

			public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
				return null;
			}
			
			public String toString(){
				return "converter";
			}
		};
		
		output.setConverter(converter);
		assertEquals(converter.toString(), output.getConverter().toString());
	}
	
	public void testGetConverter(){
		UIOutput output = new UIOutput();
		Converter converter = new Converter(){

			public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
				return null;
			}

			public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
				return null;
			}
			
			public String toString(){
				return "converter";
			}
		};
		MockValueBinding vb = new MockValueBinding();
		vb.setValue(getFacesContext(), converter);
		output.setValueBinding("converter", vb);
		assertEquals("converter", output.getConverter().toString());
	}

	public void testGetLocalValue(){
		UIOutput output = new UIOutput();
		output.setValue("aaa");
		assertEquals("aaa", output.getLocalValue());
	}

	public void testSetValue(){
		UIOutput output = new UIOutput();
		output.setValue("aaa");
		assertEquals("aaa", output.getValue());
	}

	public void testGetValue(){
		UIOutput output = new UIOutput();
		MockValueBinding vb = new MockValueBinding();
		vb.setValue(getFacesContext(), "bbb");
		output.setValueBinding("value", vb);
		assertEquals("bbb", output.getValue());
	}

}
