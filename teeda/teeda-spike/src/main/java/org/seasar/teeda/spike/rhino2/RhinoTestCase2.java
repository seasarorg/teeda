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
package org.seasar.teeda.spike.rhino2;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import junit.framework.TestCase;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * @author yone
 */
public abstract class RhinoTestCase2 extends TestCase {

    public static final Class TYPE_STRING = String.class;

    public static final Class TYPE_NUMBER = Number.class;

    public static final Class TYPE_BOOLEAN = Boolean.class;

    public static final Class TYPE_STRING_ARRAY = String[].class;

    /**
     * default constructor
     */
    public RhinoTestCase2() {
    }

    public RhinoTestCase2(String name) {
        super(name);
    }

    /**
     * execute JavaScript function. return type is int.
     * 
     * @param funcName
     *            function name
     * @param funcArgs
     *            function arguments
     * @return int value
     * @throws Exception
     */
    public int execJsFuncResultInt(String funcName, Object[] funcArgs)
            throws Exception {
        Object result = execJavaScriptFunc(funcName, funcArgs, TYPE_NUMBER);
        Number number = (Number) Context.jsToJava(result, Number.class);

        return number.intValue();
    }

    /**
     * execute JavaScript function. return type is String.
     * 
     * @param funcName
     *            function name
     * @param funcArgs
     *            function arguments
     * @return String value
     * @throws Exception
     */
    public String execJsFuncResultString(String funcName, Object[] funcArgs)
            throws Exception {
        Object result = execJavaScriptFunc(funcName, funcArgs, TYPE_STRING);
        String string = (String) Context.jsToJava(result, TYPE_STRING);

        return string;
    }

    /**
     * execute JavaScript function. return type is boolean. 
     * @param funcName function name
     * @param funcArgs function arguments
     * @return boolean value
     * @throws Exception
     */
    public boolean execJsFuncResultBoolean(String funcName, Object[] funcArgs)
            throws Exception {
        Object result = execJavaScriptFunc(funcName, funcArgs, TYPE_BOOLEAN);
        Boolean bool = (Boolean) Context.jsToJava(result, TYPE_BOOLEAN);

        return bool.booleanValue();
    }

    public String[] execJsFuncResultStringArray(String funcName,
            Object[] funcArgs) throws Exception {
        Object result = execJavaScriptFunc(funcName, funcArgs,
                TYPE_STRING_ARRAY);
        String[] strArray = (String[]) Context.jsToJava(result,
                TYPE_STRING_ARRAY);

        return strArray;
    }

    public Object execJsFuncResultObject(String funcName, Object[] funcArgs)
            throws Exception {
        Object result = execJavaScriptFunc(funcName, funcArgs, String[].class);

        return result;
    }

    public Object getJsObject(String objName) throws Exception {
        Context context = Context.enter();
        ScriptableObject scope = getScriptableObject(context);
        Object obj = scope.get(objName, scope);
        if (obj == Scriptable.NOT_FOUND) {
            // TODO throw exception ???
            System.out.println(objName + " is not defined.");
            return null;
        }

        return obj;
    }

    protected Object execJavaScriptFunc(String funcName, Object[] funcArgs,
            Class returnType) throws Exception {
        Context context = Context.enter();
        ScriptableObject scope = getScriptableObject(context);
        Object result = null;
        try {
            Object func = scope.get(funcName, scope);
            if (!(func instanceof Function)) {
                System.out.println(funcName
                        + " is undefined or not a function.");
            } else {
                Function f = (Function) func;
                result = f.call(context, scope, scope, funcArgs);
            }
        } finally {
            Context.exit();
        }
        return result;
    }

    protected String getPath() {
        String path = this.getClass().getName();
        path = path.replace('.', '/') + ".js";
        return path;
    }

    protected ScriptableObject getScriptableObject(Context context)
            throws Exception {
        String path = getPath();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream in = null;
        Reader reader = null;
        ScriptableObject scope = null;
        try {
            scope = context.initStandardObjects();
            in = loader.getResourceAsStream(path);
            reader = new InputStreamReader(in, "UTF-8");
            Script script = null;
            try {
                script = context.compileReader(reader,
                        new File(path).getName(), 0, null);
            } catch (EcmaError e) {
                // TODO javascript syntax error
                e.printStackTrace();
                throw e;
            }
            script.exec(context, scope);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return scope;
    }

}