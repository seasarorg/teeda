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
package org.seasar.teeda.core.exception;

import junit.framework.TestCase;
import junitx.framework.StringAssert;

import org.seasar.teeda.core.mock.NullUIComponent;

/**
 * @author manhole
 */
public class NoEditableValueHolderRuntimeExceptionTest extends TestCase {

    public void testGetMessage() throws Exception {
        NoEditableValueHolderRuntimeException ex = new NoEditableValueHolderRuntimeException(
                NullUIComponent.class);
        StringAssert.assertContains("ETDA0016", ex.getMessage());
    }

    public void testGetComponentClass() throws Exception {
        NoEditableValueHolderRuntimeException ex = new NoEditableValueHolderRuntimeException(
                NullUIComponent.class);
        assertEquals(NullUIComponent.class, ex.getComponentClass());
    }

}
