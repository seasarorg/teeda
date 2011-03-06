/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package examples.teeda.web.env;

import java.lang.reflect.Method;
import java.net.URL;

/**
 * @author koichik
 * 
 */
public class EnvPage {

	public String s2frameworkJar;
	public String s2extensionJar;
	public String s2tigerJar;

	public String teedaCoreJar;
	public String teedaExtensionJar;
	public String teedaAjaxJar;
	public String teedaTigerJar;

	public String xercesVersion;
	public String xercesJar;
	public String xercesClassLoader;

	public void prerender() {
		try {
			URL url = Thread.currentThread().getContextClassLoader()
					.getResource(
							"org/seasar/framework/container/S2Container.class");
			s2frameworkJar = url == null ? null : url.toExternalForm();
			url = Thread.currentThread().getContextClassLoader().getResource(
					"org/seasar/extension/dbcp/ConnectionPool.class");
			s2extensionJar = url == null ? null : url.toExternalForm();
			url = Thread.currentThread().getContextClassLoader().getResource(
					"org/seasar/extension/jdbc/JdbcManager.class");
			s2tigerJar = url == null ? null : url.toExternalForm();
		} catch (Exception ignore) {
		}
		try {
			URL url = Thread.currentThread().getContextClassLoader()
					.getResource("javax/faces/context/FacesContext.class");
			teedaCoreJar = url == null ? null : url.toExternalForm();
			url = Thread.currentThread().getContextClassLoader().getResource(
					"org/seasar/teeda/extension/ExtensionConstants.class");
			teedaExtensionJar = url == null ? null : url.toExternalForm();
			url = Thread.currentThread().getContextClassLoader().getResource(
					"org/seasar/teeda/ajax/AjaxConstants.class");
			teedaAjaxJar = url == null ? null : url.toExternalForm();
			url = Thread
					.currentThread()
					.getContextClassLoader()
					.getResource(
							"org/seasar/teeda/extension/annotation/convert/NumberConverter.class");
			teedaTigerJar = url == null ? null : url.toExternalForm();
		} catch (Exception ignore) {
		}
		try {
			Class<?> clazz = Class.forName("org.apache.xerces.impl.Version");
			Method m = clazz.getMethod("getVersion");
			xercesVersion = String.class.cast(m.invoke(null));
			ClassLoader cl = clazz.getClassLoader();
			xercesClassLoader = cl == null ? null : cl.toString();

			URL url = Thread.currentThread().getContextClassLoader()
					.getResource("org/apache/xerces/impl/Version.class");
			xercesJar = url == null ? null : url.toExternalForm();
		} catch (Exception ignore) {
		}
	}
}
