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
package org.seasar.teeda.core.config.faces.impl;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import junitx.framework.ListAssert;
import junitx.framework.ObjectAssert;
import junitx.framework.ThrowableAssert;

import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.faces.element.ReferencedBeanElement;
import org.seasar.teeda.core.config.faces.element.impl.FacesConfigImpl;
import org.seasar.teeda.core.config.faces.element.impl.ReferencedBeanElementImpl;

/**
 * @author shot
 */
public class FacesConfigUtilTest extends TestCase {

    /**
     * Constructor for FacesConfigUtilTest.
     * 
     * @param name
     */
    public FacesConfigUtilTest(String name) {
        super(name);
    }

    public void testCollectAllFacesConfig() throws Exception {
        List list = new ArrayList();
        FacesConfig config = new FacesConfigImpl();
        ReferencedBeanElement ref1 = new ReferencedBeanElementImpl();
        ref1.setReferencedBeanName("name1");
        ref1.setReferencedBeanClass("class1");
        config.addReferencedBeanElement(ref1);
        ReferencedBeanElement ref2 = new ReferencedBeanElementImpl();
        ref1.setReferencedBeanName("name2");
        ref1.setReferencedBeanClass("class2");
        config.addReferencedBeanElement(ref2);
        list.add(config);
        FacesConfig f = FacesConfigUtil.collectAllFacesConfig(list);
        List l = f.getReferencedBeanElements();
        ListAssert.assertContains(l, ref1);
        ListAssert.assertContains(l, ref2);
        ObjectAssert.assertSame(ref1, l.get(0));
        ObjectAssert.assertSame(ref2, l.get(1));
    }

    public void testIsAllFacesConfig() throws Exception {
        try {
            FacesConfigUtil.isAllFacesConfig(null);
            fail();
        } catch (Exception expected) {
            ThrowableAssert.assertEquals(expected,
                    new IllegalArgumentException());
        }
        List list = null;
        try {
            list = new ArrayList();
            list.add("aaa");
            FacesConfigUtil.isAllFacesConfig(list);
            fail();
        } catch (Exception expected) {
            ThrowableAssert.assertEquals(expected, new IllegalStateException());
        }
        try {
            list = new ArrayList();
            list.add(new FacesConfigImpl());
            FacesConfigUtil.isAllFacesConfig(list);
        } catch (Exception e) {
            fail();
        }
    }
}
