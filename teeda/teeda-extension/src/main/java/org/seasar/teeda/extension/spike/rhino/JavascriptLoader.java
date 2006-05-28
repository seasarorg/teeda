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
package org.seasar.teeda.extension.spike.rhino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

/**
 * 
 * @author mopemope
 *
 */
public class JavascriptLoader {

	public static void load(String name, Object obj) {
		if (obj instanceof ScriptableObject) {
			ScriptableObject o = (ScriptableObject) obj;
			Context cx = Context.enter();
			Script s = loadScript(name, cx);
			s.exec(cx, o);
			Context.exit();
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
