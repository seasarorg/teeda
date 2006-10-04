/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockRenderKit;
import org.seasar.teeda.core.mock.MockRenderer;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class TConditionTest extends TeedaTestCase {

    public void test_DecodeShouldBeCalled() throws Exception {
        final boolean[] calls = new boolean[] { false };
        MockRenderKit renderKit = getRenderKit();
        renderKit.addRenderer("org.seasar.teeda.extension.Condition",
                "org.seasar.teeda.extension.Condition", new MockRenderer() {
                    public void decode(FacesContext context,
                            UIComponent component) {
                        calls[0] = true;
                    }
                });
        TCondition component = new TCondition();
        component.setRendered(true);
        component.processDecodes(getFacesContext());
        assertTrue(calls[0]);
    }

}
