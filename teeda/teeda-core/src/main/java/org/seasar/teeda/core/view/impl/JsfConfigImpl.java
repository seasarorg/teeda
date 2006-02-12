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
package org.seasar.teeda.core.view.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.teeda.core.config.taglib.TagConfig;
import org.seasar.teeda.core.config.taglib.TaglibConfig;
import org.seasar.teeda.core.config.taglib.TaglibManager;
import org.seasar.teeda.core.exception.PrefixNotFoundRuntimeException;
import org.seasar.teeda.core.exception.UriNotFoundRuntimeException;
import org.seasar.teeda.core.view.JsfConfig;

/**
 * @author higa
 *
 */
public class JsfConfigImpl implements JsfConfig {

    private Map taglibUris = new HashMap();

    private Map prefixes = new HashMap();

    private TaglibManager taglibManager;

    private boolean allowJavascript = true;

    public JsfConfigImpl() {
    }

    public void addTaglibUri(String prefix, String uri) {
        taglibUris.put(prefix, uri);
        prefixes.put(uri, prefix);

    }

    public boolean hasTaglibUri(String prefix) {
        return taglibUris.containsKey(prefix);
    }

    public String getTaglibUri(String prefix) {
        String uri = (String) taglibUris.get(prefix);
        if (uri != null) {
            return uri;
        }
        throw new PrefixNotFoundRuntimeException(prefix);
    }

    public String getTaglibPrefix(String uri) {
        String prefix = (String) prefixes.get(uri);
        if (prefix != null) {
            return prefix;
        }
        throw new UriNotFoundRuntimeException(uri);
    }

    public TagConfig getTagConfig(String prefix, String tagName) {
        String uri = getTaglibUri(prefix);
        TaglibConfig taglibConfig = getTaglibManager().getTaglibConfig(uri);
        return taglibConfig.getTagConfig(tagName);
    }

    public TagConfig getTagConfig(String inject) {
        int index = inject.indexOf(':');
        if (index < 0) {
            throw new IllegalArgumentException(inject);
        }
        String prefix = inject.substring(0, index);
        String tagName = inject.substring(index + 1);
        return getTagConfig(prefix, tagName);
    }

    public TaglibManager getTaglibManager() {
        if (taglibManager != null) {
            return taglibManager;
        }
        throw new EmptyRuntimeException("taglibManager");
    }

    public void setTaglibManager(TaglibManager taglibManager) {
        this.taglibManager = taglibManager;
    }

    public boolean isAllowJavascript() {
        return allowJavascript;
    }

    public void setAllowJavascript(boolean allowJavascript) {
        this.allowJavascript = allowJavascript;
    }

}
