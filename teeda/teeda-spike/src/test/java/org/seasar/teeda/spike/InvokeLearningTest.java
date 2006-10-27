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
package org.seasar.teeda.spike;

import junit.framework.TestCase;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author shot
 */
public class InvokeLearningTest extends TestCase {

    public void test1() throws Exception {
        Hoge hoge = new Hoge();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(hoge.getClass());
        String s = (String) beanDesc.invoke(hoge, "invoke", null);
        assertTrue(hoge.i == 1);
        assertNull(s);
    }

}
