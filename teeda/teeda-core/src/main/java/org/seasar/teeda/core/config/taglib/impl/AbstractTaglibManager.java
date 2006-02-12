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

import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.seasar.framework.util.JarFileUtil;
import org.seasar.framework.util.JarURLConnectionUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.URLUtil;
import org.seasar.teeda.core.config.taglib.TaglibConfig;
import org.seasar.teeda.core.config.taglib.TaglibManager;
import org.seasar.teeda.core.exception.UriNotFoundRuntimeException;

/**
 * @author higa
 *  
 */
public abstract class AbstractTaglibManager implements TaglibManager {

	protected static final String FILE_PROTOCOL = "file:";

	protected static final String JAR_PROTOCOL = "jar:";

	protected static final String JAR_PROTOCOL_SUFFIX = "!/";

    protected static final String JAR_EXTENSION = "jar";
    
	protected static final String JAR_FILE_SUFFIX = "." + JAR_EXTENSION;

	private Map taglibConfigs = new HashMap();

	private TaglibConfigBuilderImpl builder = new TaglibConfigBuilderImpl();

	public TaglibConfig getTaglibConfig(String uri)
			throws UriNotFoundRuntimeException {

		TaglibConfig taglibConfig = (TaglibConfig) taglibConfigs.get(uri);
		if (taglibConfig == null) {
			throw new UriNotFoundRuntimeException(uri);
		}
		return taglibConfig;
	}

	public boolean hasTaglibConfig(String uri) {
		return taglibConfigs.containsKey(uri);
	}
	
	public void destroy() {
		taglibConfigs.clear();
	}
	
	protected JarURLConnection openJarURLConnection(URL url) {
		URLConnection conn = URLUtil.openConnection(url);
		if (conn instanceof JarURLConnection) {
			return (JarURLConnection) conn;
		}
		String urlStr = url.toString();
        String ext = ResourceUtil.getExtension(urlStr);
        
        if (ext == null || !ext.equalsIgnoreCase(JAR_EXTENSION)) {
            return null;
        }
		URL jarURL = URLUtil.create(JAR_PROTOCOL + urlStr
					+ JAR_PROTOCOL_SUFFIX);
		return (JarURLConnection) URLUtil.openConnection(jarURL);
	}

	protected void scanJar(JarURLConnection conn) {
		conn.setUseCaches(false);
		JarFile jarFile = JarURLConnectionUtil.getJarFile(conn);
		Enumeration entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = (JarEntry) entries.nextElement();
			String name = entry.getName();
			if (!name.startsWith("META-INF/")) {
				continue;
			}
			if (!name.endsWith(".tld")) {
				continue;
			}
			InputStream is = JarFileUtil.getInputStream(jarFile, entry);
			try {
				scanTld(is);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Throwable ignore) {
					}
				}
			}
		}
	}

	protected void scanTld(InputStream is) {
		TaglibConfig taglibConfig = builder.build(is);
		taglibConfigs.put(taglibConfig.getUri(), taglibConfig);
	}
}