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
package org.seasar.teeda.core.util;

import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;

import org.seasar.teeda.core.mock.MockPropertyResolver;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class PropertyResolverUtilTest extends TeedaTestCase {

    public void testGetValue() {
        setPropertyResolver(new MockWrappedPropertyResolver());
        Object o = PropertyResolverUtil.getValue(getApplication(), "hoge",
                "foo", null);
        assertNotNull(o);
        assertTrue(o instanceof MockWrappedPropertyResolver.ObjectValue);
        MockWrappedPropertyResolver.ObjectValue value = (MockWrappedPropertyResolver.ObjectValue) o;
        assertEquals("hoge", value.getBase());
        assertEquals("foo", value.getProperty());

        o = PropertyResolverUtil.getValue(getApplication(), "hoge", "foo",
                new Integer(1));
        assertNotNull(o);
        assertTrue(o instanceof MockWrappedPropertyResolver.IndexValue);
        MockWrappedPropertyResolver.IndexValue index = (MockWrappedPropertyResolver.IndexValue) o;
        assertEquals("hoge", index.getBase());
        assertEquals(1, index.getIndex());
    }

    public void testGetType() {
        setPropertyResolver(new MockWrappedPropertyResolver());
        Class clazz = PropertyResolverUtil.getType(getApplication(), "a", "b",
                null);
        assertEquals(MockWrappedPropertyResolver.ObjectValue.class, clazz);

        clazz = PropertyResolverUtil.getType(getApplication(), "a", "b",
                new Integer(2));
        assertEquals(MockWrappedPropertyResolver.IndexValue.class, clazz);
    }

    public void testIsReadOnly() {
        setPropertyResolver(new MockWrappedPropertyResolver());
        assertFalse(PropertyResolverUtil.isReadOnly(getApplication(), "hoge",
                "foo", null));
        assertTrue(PropertyResolverUtil.isReadOnly(getApplication(), "hoge",
                "foo", new Integer(3)));
    }

    public static class MockWrappedPropertyResolver extends
            MockPropertyResolver {

        public MockWrappedPropertyResolver() {
        }

        public Object getValue(Object base, int index)
                throws EvaluationException, PropertyNotFoundException {
            return new IndexValue(base, index);
        }

        public Object getValue(Object base, Object property)
                throws EvaluationException, PropertyNotFoundException {
            return new ObjectValue(base, property);
        }

        public Class getType(Object base, int index)
                throws EvaluationException, PropertyNotFoundException {
            return new IndexValue(base, index).getClass();
        }

        public Class getType(Object base, Object property)
                throws EvaluationException, PropertyNotFoundException {
            return new ObjectValue(base, property).getClass();
        }

        public boolean isReadOnly(Object base, int index)
                throws EvaluationException, PropertyNotFoundException {
            return true;
        }

        public boolean isReadOnly(Object base, Object property)
                throws EvaluationException, PropertyNotFoundException {
            return false;
        }

        public static class ObjectValue {
            private Object base_;

            private Object property_;

            public ObjectValue(Object base, Object property) {
                base_ = base;
                property_ = property;
            }

            public Object getBase() {
                return base_;
            }

            public Object getProperty() {
                return property_;
            }
        }

        public static class IndexValue {
            private Object base_;

            private int index_;

            public IndexValue(Object base, int index) {
                base_ = base;
                index_ = index;
            }

            public Object getBase() {
                return base_;
            }

            public int getIndex() {
                return index_;
            }
        }
    }

}
