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

	private ScriptableObject scope;

	public RhinoTestCase() {

	}

	public RhinoTestCase(String testName, ScriptableObject scope) {
		super(testName);
		this.setName(testName);
		this.scope = scope;
	}
	/*
	public void testScript() {

	}*/

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

			String assert = "var assert = Packages.junit.framework.Assert; for(var v in assert){ this[v] = assert[v];}";
			cx.evaluateString(scope, assert, "assert", 0, null);
			String scriptLoader = "this.loadScript = function loadScript(name){Packages.org.seasar.teeda.extension.spike.rhino.JavascriptLoader.load(name, this);}";
			cx.evaluateString(scope, scriptLoader, "scriptLoader", 0, null);

			Script script = cx.compileReader(reader, new File(path).getName(),
					0, null);
			Object result = script.exec(cx, scope);
			Object[] list = scope.getIds();
			cx.exit();
			for (int i = 0; i < list.length; i++) {
				String name = list[i].toString();
				if (name.toString().startsWith("test")) {
					Object o = scope.get(name, null);
					Function f = (Function) o;
					RhinoTestCase test = new RhinoTestCase(name, scope);
					suite.addTest(test);
				}
			}
			return suite;
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	protected void runTest() throws Throwable {
		Context cx = Context.enter();
		Object o = scope.get(this.getName(), null);
		Function func = (Function) o;
		func.call(cx, scope, scope, null);
		cx.exit();
	}

}