package javax.faces.component;

import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestUISelectItem extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestUISelectItem.class);
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
	 * Constructor for TestUISelectItem.
	 * @param arg0
	 */
	public TestUISelectItem(String arg0) {
		super(arg0);
	}

	public void testGetFamily(){
		UISelectItem item = new UISelectItem();
		assertEquals(item.getFamily(), UISelectItem.COMPORNENT_FAMILY);
	}
	
	public void testSetItemDescription(){
		UISelectItem item = new UISelectItem();
		String str = "aaa";
		item.setItemDescription(str);
		assertEquals(item.getItemDescription(), str);
	}

	public void testGetItemDescription(){
		UISelectItem item = new UISelectItem();
		MockValueBinding vb = new MockValueBinding();
		String value = "aaa";
		vb.setValue(getFacesContext(), value);
		item.setValueBinding("itemDescription", vb);
		assertEquals(value, item.getItemDescription());
	}
	
	public void testSetItemDisabled(){
		UISelectItem item = new UISelectItem();
		boolean value = true;
		item.setItemDisabled(value);
		assertEquals(item.isItemDisabled(), value);
	}

	public void testGetItemDisabled(){
		UISelectItem item = new UISelectItem();
		MockValueBinding vb = new MockValueBinding();
		boolean value = true;
		vb.setValue(getFacesContext(), new Boolean(value));
		item.setValueBinding("itemDisabled", vb);
		assertEquals(value, item.isItemDisabled());
	}
	
	public void testSetItemLabel(){
		UISelectItem item = new UISelectItem();
		String str = "bbb";
		item.setItemLabel(str);
		assertEquals(item.getItemLabel(), str);
	}

	public void testGetItemLabel(){
		UISelectItem item = new UISelectItem();
		MockValueBinding vb = new MockValueBinding();
		String value = "bbb";
		vb.setValue(getFacesContext(), value);
		item.setValueBinding("itemLabel", vb);
		assertEquals(value, item.getItemLabel());
	}

	public void testSetItemValue(){
		UISelectItem item = new UISelectItem();
		Integer num = new Integer(3);
		item.setItemValue(num);
		assertEquals(item.getItemValue(), num);
	}

	public void testGetItemValue(){
		UISelectItem item = new UISelectItem();
		MockValueBinding vb = new MockValueBinding();
		Integer value = new Integer(3);
		vb.setValue(getFacesContext(), value);
		item.setValueBinding("itemValue", vb);
		assertEquals(value, item.getItemValue());
	}

	public void testSetValue(){
		UISelectItem item = new UISelectItem();
		String str = "a";
		item.setValue(str);
		assertEquals(item.getValue(), str);
	}

	public void testGetValue(){
		UISelectItem item = new UISelectItem();
		MockValueBinding vb = new MockValueBinding();
		String value = "a";
		vb.setValue(getFacesContext(), value);
		item.setValueBinding("value", vb);
		assertEquals(value, item.getValue());
	}	
	
}
