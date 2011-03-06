/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package javax.faces.component;

import java.util.Iterator;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class UISelectOneTest extends UIInputTest {

    public void testValidateValue_Available() throws Exception {
        // ## Arrange ##
        S2Container container = new S2ContainerImpl();
        SingletonS2ContainerFactory.setContainer(container);
        SingletonS2ContainerFactory.init();
        UISelectOne selectOne = arrangeForValidateTest();
        MockFacesContext context = getFacesContext();

        // ## Act ##
        selectOne.validateValue(context, "bv");

        // ## Assert ##
        assertEquals(true, selectOne.isValid());
        Iterator messages = context.getMessages();
        assertEquals(false, messages.hasNext());
    }

    private UISelectOne arrangeForValidateTest() {
        UISelectOne selectOne = createUISelectOne();
        selectOne.setRendererType(null);
        selectOne.setValid(true);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("av");
            selectItem.setItemLabel("al");
            selectOne.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("bv");
            selectItem.setItemLabel("bl");
            selectOne.getChildren().add(selectItem);
        }
        return selectOne;
    }

    /*
     public void testValidateValue_NotAvailable() throws Exception {
     // ## Arrange ##
     UISelectOne selectOne = arrangeForValidateTest();
     MockFacesContext context = getFacesContext();

     // ## Act ##
     selectOne.validateValue(context, "c");

     // ## Assert ##
     assertEquals(false, selectOne.isValid());
     Iterator messages = context.getMessages();
     assertEquals(true, messages.hasNext());
     messages.next();
     assertEquals(false, messages.hasNext());
     }
     */
    private UISelectOne createUISelectOne() {
        return (UISelectOne) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UISelectOne();
    }

}
