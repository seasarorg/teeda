package org.seasar.teeda.extension.spike.rhino;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

public class RhinoTestCase extends TestCase {

    private ScriptableObject globalScope;

    public RhinoTestCase() {
    }

    public RhinoTestCase(String name, ScriptableObject globalScope) {
        super(name);
        this.globalScope = globalScope;
    }
    
    public Test createJsTestCase() throws Throwable {

        String path = this.getClass().getName();
        path = path.replace('.', '/') + ".js";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream in = null;
        Reader reader = null;
        TestSuite suite = new TestSuite(path);
        try {
            in = loader.getResourceAsStream(path);
            reader = new InputStreamReader(in, "UTF-8");
            Context cx = Context.enter();
            ScriptableObject scope = cx.initStandardObjects();

            builtinAssertMethods(cx, scope);
            loadScript(cx, scope);

            Script script = cx.compileReader(reader, new File(path).getName(),
                    0, null);
            Object result = script.exec(cx, scope);
            Object[] list = scope.getIds();
            Context.exit();
            for (int i = 0; i < list.length; i++) {
                String name = list[i].toString();
                if (name.toString().startsWith("test")) {
                    Object o = scope.get(name, null);
                    Function f = (Function) o;
                    suite.addTest(new RhinoTestCase(name, scope));
                }
            }
            return suite;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    protected ScriptableObject getGlobalScope() {
        return globalScope;
    }

    protected void runTest() throws Throwable {
        Context cx = Context.enter();
        Object o = getGlobalScope().get(this.getName(), null);
        Function func = (Function) o;
        func.call(cx, getGlobalScope(), getGlobalScope(), null);
        Context.exit();
    }

    //TODO use ScriptableObject.defineFunctionProperties()
    protected void builtinAssertMethods(Context cx, ScriptableObject scope) {
        String assertStr = "var assert = Packages.junit.framework.Assert; for(var v in assert){ this[v] = assert[v];}";
        cx.evaluateString(scope, assertStr, "assert", 0, null);
    }

    //TODO use ScriptableObject.defineFunctionProperties()
    protected void loadScript(Context cx, ScriptableObject scope) {
        String scriptLoader = "this.loadScript = function loadScript(name){Packages.org.seasar.teeda.extension.spike.rhino.JavascriptLoader.load(name, this);}";
        cx.evaluateString(scope, scriptLoader, "scriptLoader", 0, null);
    }
}