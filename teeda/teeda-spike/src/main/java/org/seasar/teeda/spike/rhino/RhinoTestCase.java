/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.spike.rhino;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

/**
 * 
 * @author mopemope
 */
public abstract class RhinoTestCase extends TestCase {

    public RhinoTestCase() {
    }

    public Test createJsTestCase() throws Throwable {

        String path = this.getClass().getName();
        path = path.replace('.', '/') + ".js";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream in = null;
        Reader reader = null;
        TestSuite suite = new TestSuite(path);
        Context cx = Context.enter();
        try {
            in = loader.getResourceAsStream(path);
            if (in != null) {
                reader = new InputStreamReader(in,
                        RhinoConstants.DEFAULT_ENCODING);
            }

            Scriptable scope = getInitScopeObject(cx);

            builtinAssertMethods(cx, scope);
            loadScript(cx, scope);

            if (reader != null) {
                Script script = cx.compileReader(reader, new File(path)
                        .getName(), 0, null);
                Object result = script.exec(cx, scope);
            }

            Object[] list = scope.getIds();
            for (int i = 0; i < list.length; i++) {
                String name = list[i].toString();
                if (name.toString().startsWith("test")) {
                    Object o = scope.get(name, null);
                    if (o instanceof Function) {
                        scope.get(name, null);
                        Function f = (Function) o;
                        f.call(cx, scope, scope, null);
                    }
                }
            }
            return suite;
        } finally {
            if (in != null) {
                in.close();
            }
            Context.exit();
        }
    }

    private Scriptable getInitScopeObject(Context cx) throws Exception {
        String htmlPath = this.getClass().getName();
        htmlPath = htmlPath.replace('.', '/') + ".html";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(htmlPath);
        if (url != null) {
            htmlPath = url.getFile();
            if (new File(url.getFile()).exists()) {
                ScriptablePageLoader pageLoader = new ScriptablePageLoader();
                Scriptable scope = pageLoader.load(htmlPath);
                if (scope != null) {
                    return scope.getParentScope();
                }
            }
        }
        return cx.initStandardObjects();
    }

    //TODO use ScriptableObject.defineFunctionProperties()
    protected void builtinAssertMethods(Context cx, Scriptable scope) {
        String assertStr = "var assert = Packages.junit.framework.Assert; for(var v in assert){ this[v] = assert[v];}";
        cx.evaluateString(scope, assertStr, "assert", 0, null);
    }

    //TODO use ScriptableObject.defineFunctionProperties()
    protected void loadScript(Context cx, Scriptable scope) {
        String scriptLoader = "this.loadScript = function loadScript(name){Packages.org.seasar.teeda.spike.rhino.JavascriptLoader.load(name, this);}";
        cx.evaluateString(scope, scriptLoader, "scriptLoader", 0, null);
    }
}