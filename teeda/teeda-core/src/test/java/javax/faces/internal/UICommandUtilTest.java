/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package javax.faces.internal;

import javax.faces.component.UICommand;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class UICommandUtilTest extends TeedaTestCase {

    public void testSetAndGetSubmittedCommand() throws Exception {
        MockFacesContext context = getFacesContext();
        UICommand command = new UICommand();
        command.setId("aaa");
        UICommandUtil.setSubmittedCommand(context, command);
        assertEquals("aaa", UICommandUtil.getSubmittedCommand(context));
    }
}
