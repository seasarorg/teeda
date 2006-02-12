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
package org.seasar.teeda.core.config.taglib.impl;

import java.net.JarURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletContext;

import org.seasar.framework.log.Logger;

/**
 * @author higa
 *  
 */
public class ServletContextTaglibManagerImpl extends AbstractTaglibManager {
	
	private static Logger logger = Logger.getLogger(ServletContextTaglibManagerImpl.class);

	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void init() {
		scanJars("/WEB-INF/lib/");
	}

	public void scanJars(String libPath) {
		Set jars = servletContext.getResourcePaths(libPath);
		if (jars != null) {
			for (Iterator i = jars.iterator(); i.hasNext();) {
				String path = (String) i.next();
				if (path.toLowerCase().endsWith(".jar")) {
					scanJar(path);
				}
			}
		}
	}

	public void scanJar(String jarPath) {
		try {
			URL url = servletContext.getResource(jarPath);
			JarURLConnection conn = openJarURLConnection(url);
			if (conn == null) {
				return;
			}
			scanJar(conn);
		} catch (Throwable t) {
			logger.log(t);
		}
	}
}