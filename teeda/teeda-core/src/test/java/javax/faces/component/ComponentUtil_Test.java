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
package javax.faces.component;

import java.util.Locale;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ComponentUtil_Test extends TeedaTestCase {

    public void testGetLocale1() throws Exception {
        MockFacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        root.setLocale(Locale.CANADA);
        context.setViewRoot(root);
        Locale l = ComponentUtil_.getLocale(context);
        assertEquals(Locale.CANADA, l);
    }

    public void testGetLocale_viewRootNull() throws Exception {
        MockFacesContext context = getFacesContext();
        Locale l = ComponentUtil_.getLocale(context);
        assertEquals(Locale.getDefault(), l);
    }

}
