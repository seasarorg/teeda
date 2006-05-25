package org.seasar.teeda.extension.spike.rhino;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

public class RhinoTestRunner extends TestRunner {

	public static void main(String args[]) {
		RhinoTestRunner runner = new RhinoTestRunner();

		try {
			TestResult r = runner.start(args);

			if (!r.wasSuccessful())
				System.exit(FAILURE_EXIT);

			System.exit(SUCCESS_EXIT);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(EXCEPTION_EXIT);
		}
	}

	public Test getTest(String scriptName) {
		clearStatus();
		String fileName = scriptName;
		Context cx = Context.enter();
		FileReader filin = null;
		BufferedReader bufin = null;
		TestSuite suite = new TestSuite();

		try {
			filin = new FileReader(fileName);
			bufin = new BufferedReader(filin);
			
			ScriptableObject scope = cx.initStandardObjects();
			String assert = "var assert = Packages.junit.framework.Assert; for(var v in assert){ this[v] = assert[v];}";
			cx.evaluateString(scope, assert, "assert", 0, null);
			String scriptLoader = "this.loadScript = function loadScript(name){Packages.org.seasar.teeda.extension.spike.rhino.JavascriptLoader.load(name, this);}";
			cx.evaluateString(scope, scriptLoader, "scriptLoader", 0, null);

			Script script = cx.compileReader(bufin, fileName, 0, null);
			Object result = script.exec(cx, scope);
			cx.exit();
			Object[] list = scope.getIds();
			for (int i = 0; i < list.length; i++) {
				String name = list[i].toString();
				if (name.toString().startsWith("test")) {
					RhinoTestCase test = new RhinoTestCase(name, scope);
					suite.addTest(test);
				}
			}
			return suite;
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				bufin.close();
				filin.close();
			} catch (Exception ex) {

			}
		}
		return null;
	}

}
