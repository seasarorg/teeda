/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author manhole
 */
public class MessageBundleTest extends TeedaTestCase {

    public void testNullArg() throws Exception {
        try {
            new MessageBundle(null);
            fail();
        } catch (NullPointerException e) {
        }
    }

    public void testBlankArg() throws Exception {
        try {
            new MessageBundle("");
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    public void testGetValue_canada() throws Exception {
        // ## Arrange ##
        final Locale locale = Locale.CANADA;
        getFacesContext().getViewRoot().setLocale(locale);

        MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1");

        // ## Act ##
        // ## Assert ##
        assertEquals("AAAA_Canada", messageBundle.get("aaaa"));
    }

    public void testGetValue_japan() throws Exception {
        // ## Arrange ##
        final Locale locale = Locale.JAPAN;
        getFacesContext().getViewRoot().setLocale(locale);

        MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1");

        // ## Act ##
        // ## Assert ##
        assertEquals("AAAA_default", messageBundle.get("aaaa"));
    }

    public void testGetValue_localeChange() throws Exception {
        // ## Arrange ##
        final MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1");

        // ## Act ##
        // ## Assert ##
        getFacesContext().getViewRoot().setLocale(Locale.JAPAN);
        assertEquals("AAAA_default", messageBundle.get("aaaa"));

        getFacesContext().getViewRoot().setLocale(Locale.CANADA);
        assertEquals("AAAA_Canada", messageBundle.get("aaaa"));

        getFacesContext().getViewRoot().setLocale(Locale.JAPAN);
        assertEquals("AAAA_default", messageBundle.get("aaaa"));
    }

    public void testClear() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1") {
            Map getResourceBundleMap() {
                return new NullMap() {
                    public void clear() {
                        calls[0] = true;
                    }
                };
            }
        };

        // ## Act ##
        messageBundle.clear();

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testContainsKey() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1") {
            Map getResourceBundleMap() {
                return new NullMap() {
                    public boolean containsKey(Object obj) {
                        calls[0] = true;
                        return false;
                    }
                };
            }
        };

        // ## Act ##
        messageBundle.containsKey("aaa");

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testContainsValue() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1") {
            Map getResourceBundleMap() {
                return new NullMap() {
                    public boolean containsValue(Object obj) {
                        calls[0] = true;
                        return false;
                    }
                };
            }
        };

        // ## Act ##
        messageBundle.containsValue("aaa");

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testEntrySet() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1") {
            Map getResourceBundleMap() {
                return new NullMap() {
                    public Set entrySet() {
                        calls[0] = true;
                        return null;
                    }
                };
            }
        };

        // ## Act ##
        messageBundle.entrySet();

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testGet() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1") {
            Map getResourceBundleMap() {
                return new NullMap() {
                    public Object get(Object obj) {
                        calls[0] = true;
                        return null;
                    }
                };
            }
        };

        // ## Act ##
        messageBundle.get("aaaa");

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testIsEmpty() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1") {
            Map getResourceBundleMap() {
                return new NullMap() {
                    public boolean isEmpty() {
                        calls[0] = true;
                        return false;
                    }
                };
            }
        };

        // ## Act ##
        messageBundle.isEmpty();

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testKeySet() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1") {
            Map getResourceBundleMap() {
                return new NullMap() {
                    public Set keySet() {
                        calls[0] = true;
                        return null;
                    }
                };
            }
        };

        // ## Act ##
        messageBundle.keySet();

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testPut() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1") {
            Map getResourceBundleMap() {
                return new NullMap() {
                    public Object put(Object obj, Object obj1) {
                        calls[0] = true;
                        return null;
                    }
                };
            }
        };

        // ## Act ##
        messageBundle.put("a", "b");

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testPutAll() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1") {
            Map getResourceBundleMap() {
                return new NullMap() {
                    public void putAll(Map map) {
                        calls[0] = true;
                    }
                };
            }
        };

        // ## Act ##
        messageBundle.putAll(null);

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testRemove() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1") {
            Map getResourceBundleMap() {
                return new NullMap() {
                    public Object remove(Object obj) {
                        calls[0] = true;
                        return null;
                    }
                };
            }
        };

        // ## Act ##
        messageBundle.remove("a");

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testSize() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1") {
            Map getResourceBundleMap() {
                return new NullMap() {
                    public int size() {
                        calls[0] = true;
                        return 0;
                    }
                };
            }
        };

        // ## Act ##
        messageBundle.size();

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testValues() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        MessageBundle messageBundle = new MessageBundle(
                "org.seasar.teeda.core.util.MessageBundleTest_test1") {
            Map getResourceBundleMap() {
                return new NullMap() {
                    public Collection values() {
                        calls[0] = true;
                        return null;
                    }
                };
            }
        };

        // ## Act ##
        messageBundle.values();

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    private static class NullMap implements Map {

        public void clear() {
            throw new UnsupportedOperationException();
        }

        public boolean containsKey(Object obj) {
            throw new UnsupportedOperationException();
        }

        public boolean containsValue(Object obj) {
            throw new UnsupportedOperationException();
        }

        public Set entrySet() {
            throw new UnsupportedOperationException();
        }

        public Object get(Object obj) {
            throw new UnsupportedOperationException();
        }

        public boolean isEmpty() {
            throw new UnsupportedOperationException();
        }

        public Set keySet() {
            throw new UnsupportedOperationException();
        }

        public Object put(Object obj, Object obj1) {
            throw new UnsupportedOperationException();
        }

        public void putAll(Map map) {
            throw new UnsupportedOperationException();
        }

        public Object remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        public int size() {
            throw new UnsupportedOperationException();
        }

        public Collection values() {
            throw new UnsupportedOperationException();
        }
    }

}
