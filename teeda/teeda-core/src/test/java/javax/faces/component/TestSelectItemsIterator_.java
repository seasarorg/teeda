package javax.faces.component;

import javax.faces.model.SelectItem;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockUIComponentBase;

public class TestSelectItemsIterator_ extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestSelectItemsIterator_.class);
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
     * Constructor for TestSelectItemsIterator_.
     * 
     * @param arg0
     */
    public TestSelectItemsIterator_(String arg0) {
        super(arg0);
    }

    public void testHasNext() {
        MockUIComponentBase parent = new MockUIComponentBase();
        parent.setId("hoge");
        MockUIComponent child = new MockUIComponent();
        child.setId("child");
        parent.getChildren().add(child);
        SelectItemsIterator_ itr = new SelectItemsIterator_(parent);
        assertTrue(itr.hasNext());
    }

    public void testNext1() {
        String str = "aaa";
        MockUIComponentBase parent = new MockUIComponentBase();
        parent.setId("hoge");
        UISelectItem child = new UISelectItem();
        child.setId("child");
        child.setItemLabel("label");
        child.setItemValue(str);
        parent.getChildren().add(child);
        SelectItemsIterator_ itr = new SelectItemsIterator_(parent);
        Object o = itr.next();
        assertTrue(o instanceof SelectItem);
        assertEquals("label", ((SelectItem) o).getLabel());
        assertEquals("aaa", ((SelectItem) o).getValue());
    }

    public void testNext2() {
        MockUIComponentBase parent = new MockUIComponentBase();
        parent.setId("hoge");
        UISelectItems items = new UISelectItems();
        SelectItem child = new SelectItem();
        items.setValue(child);
        child.setLabel("label");
        parent.getChildren().add(items);
        SelectItemsIterator_ itr = new SelectItemsIterator_(parent);
        Object o = itr.next();
        assertTrue(o instanceof SelectItem);
        assertEquals("label", ((SelectItem) o).getLabel());
    }

}
