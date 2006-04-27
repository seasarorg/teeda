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
    }

}
