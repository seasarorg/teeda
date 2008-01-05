/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.it.learning.js;

import junit.framework.TestCase;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

public class ScriptTest extends TestCase {

    public void test1() throws Exception {
        Context context = Context.enter();
        Scriptable scriptable = context.initStandardObjects();
        Script script = context.compileString(
                "function plus(a, b) { return a + b; }", null, 0, null);
        script.exec(context, scriptable);
        Function function = (Function) scriptable.get("plus", null);
        Object result = function.call(context, scriptable, null, new Object[] {
                new Integer(1), new Integer(123) });
        System.out.println(result + ", " + result.getClass());
        Number actual = (Number) result;
        assertEquals(124, actual.intValue());

        Object[] ids = scriptable.getIds();
        assertEquals(1, ids.length);
        assertEquals("plus", ids[0]);
    }

}
