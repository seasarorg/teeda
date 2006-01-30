package org.seasar.teeda.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectMany;
import javax.faces.model.SelectItem;

import junit.framework.TestCase;
import junitx.framework.ObjectAssert;

import org.seasar.teeda.core.mock.MockUIInput;

public class SelectItemUtilTest extends TestCase {

    public void testUISelectItem() throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        {
            UISelectItem uiSelectItem = new UISelectItem();
            uiSelectItem.setItemValue("a");
            uiSelectItem.setItemLabel("b");
            uiSelectItem.setItemDescription("c");
            component.getChildren().add(uiSelectItem);
        }

        // ## Act ##
        List l = SelectItemUtil.accrueSelectItemList(component);

        // ## Assert ##
        assertEquals(1, l.size());
        SelectItem item = (SelectItem) l.get(0);
        assertEquals("a", item.getValue());
        assertEquals("b", item.getLabel());
        assertEquals("c", item.getDescription());
    }

    public void testEmptyUISelectItems() throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        component.getChildren().add(new UISelectItems());

        // ## Act ##
        List l = SelectItemUtil.accrueSelectItemList(component);

        // ## Assert ##
        assertEquals(0, l.size());
    }

    public void testUISelectItems_SelectItem() throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        {
            UISelectItems uiSelectItems = new UISelectItems();
            SelectItem selectItem = new SelectItem();
            selectItem.setValue("val");
            selectItem.setLabel("lab");
            selectItem.setDescription("desc");
            uiSelectItems.setValue(selectItem);
            component.getChildren().add(uiSelectItems);
        }

        // ## Act ##
        List l = SelectItemUtil.accrueSelectItemList(component);

        // ## Assert ##
        assertEquals(1, l.size());
        SelectItem item = (SelectItem) l.get(0);
        assertEquals("val", item.getValue());
        assertEquals("lab", item.getLabel());
        assertEquals("desc", item.getDescription());
    }

    public void testUISelectItems_SelectItemArray() throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        {
            UISelectItems uiSelectItems = new UISelectItems();
            SelectItem item1 = new SelectItem("v1", "l1", "d1");
            SelectItem item2 = new SelectItem("v2", "l2", "d2");
            uiSelectItems.setValue(new SelectItem[] { item1, item2 });
            component.getChildren().add(uiSelectItems);
        }

        // ## Act ##
        List l = SelectItemUtil.accrueSelectItemList(component);

        // ## Assert ##
        assertEquals(2, l.size());
        {
            SelectItem item = (SelectItem) l.get(0);
            assertEquals("v1", item.getValue());
            assertEquals("l1", item.getLabel());
            assertEquals("d1", item.getDescription());
        }
        {
            SelectItem item = (SelectItem) l.get(1);
            assertEquals("v2", item.getValue());
            assertEquals("l2", item.getLabel());
            assertEquals("d2", item.getDescription());
        }
    }

    public void testUISelectItems_SelectItemCollection() throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        {
            UISelectItems uiSelectItems = new UISelectItems();
            List l = new ArrayList();
            l.add(new SelectItem("v1", "l1", "d1"));
            l.add(new SelectItem("v2", "l2", "d2"));
            l.add(new SelectItem("v3", "l3", "d3"));
            uiSelectItems.setValue((Collection) l);
            component.getChildren().add(uiSelectItems);
        }

        // ## Act ##
        List l = SelectItemUtil.accrueSelectItemList(component);

        // ## Assert ##
        assertEquals(3, l.size());
        {
            SelectItem item = (SelectItem) l.get(0);
            assertEquals("v1", item.getValue());
            assertEquals("l1", item.getLabel());
            assertEquals("d1", item.getDescription());
        }
        {
            SelectItem item = (SelectItem) l.get(1);
            assertEquals("v2", item.getValue());
            assertEquals("l2", item.getLabel());
            assertEquals("d2", item.getDescription());
        }
        {
            SelectItem item = (SelectItem) l.get(2);
            assertEquals("v3", item.getValue());
            assertEquals("l3", item.getLabel());
            assertEquals("d3", item.getDescription());
        }
    }

    public void testUISelectItems_SelectItemMap() throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        {
            UISelectItems uiSelectItems = new UISelectItems();
            Map m = new LinkedHashMap();
            m.put("l1", "v1");
            m.put("l2", "v2");
            uiSelectItems.setValue(m);
            component.getChildren().add(uiSelectItems);
        }

        // ## Act ##
        List l = SelectItemUtil.accrueSelectItemList(component);

        // ## Assert ##
        assertEquals(2, l.size());
        {
            SelectItem item = (SelectItem) l.get(0);
            assertEquals("v1", item.getValue());
            assertEquals("l1", item.getLabel());
            assertEquals(null, item.getDescription());
        }
        {
            SelectItem item = (SelectItem) l.get(1);
            assertEquals("v2", item.getValue());
            assertEquals("l2", item.getLabel());
            assertEquals(null, item.getDescription());
        }
    }

    public void testChildrenAreNotUISelectItemNorUISelectItems()
            throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        MockUIInput invalid = new MockUIInput();
        ObjectAssert.assertNotInstanceOf(UISelectItem.class, invalid);
        ObjectAssert.assertNotInstanceOf(UISelectItems.class, invalid);
        component.getChildren().add(invalid);

        // ## Act ##
        List l = SelectItemUtil.accrueSelectItemList(component);

        // ## Assert ##
        assertEquals("only valid children are UISelectItem or UISelectItems",
                0, l.size());
    }

}
