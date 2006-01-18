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
package javax.faces.component;

import org.seasar.teeda.core.unit.AssertUtil;

import junit.framework.TestCase;

/**
 * @author shot
 */
public class ArrayIterator_Test extends TestCase {

    public void testIterate() {
        Object[] o = new Object[] { "a", "b", "c" };
        ArrayIterator_ itr = new ArrayIterator_(o);
        for (int i = 0; itr.hasNext(); i++) {
            String s = (String) itr.next();
            assertEquals(o[i], s);
        }
    }

    public void testRemove() throws Exception {
        // ## Arrange ##
        ArrayIterator_ it = new ArrayIterator_(new String[] { "1", "2" });

        // ## Act ##
        // ## Assert ##
        try {
            it.remove();
            fail();
        } catch (UnsupportedOperationException uoe) {
            AssertUtil.assertExceptionMessageExist(uoe);
        }

    }
}
