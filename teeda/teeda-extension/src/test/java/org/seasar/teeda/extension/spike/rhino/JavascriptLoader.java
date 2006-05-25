package org.seasar.teeda.extension.spike.rhino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

public class JavascriptLoader {

	public static void load(String name, Object obj) {
		if (obj instanceof ScriptableObject) {
			ScriptableObject o = (ScriptableObject) obj;
			Context cx = Context.enter();
			Script s = JavascriptLoader.loadScript(name, cx);
			s.exec(cx, o);
			cx.exit();
		}
	}

	public static Script loadScript(String scriptName, Context cx) {
		String path = scriptName.replace('.', '/') + ".js";
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream in = null;
		BufferedReader reader = null;
		try {
			in = loader.getResourceAsStream(path);
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			return cx.compileReader(reader, scriptName, 1, null);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

}
