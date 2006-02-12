/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.seasar.teeda.core.config.taglib.TaglibConfig;
import org.seasar.teeda.core.config.taglib.TaglibManager;
import org.seasar.teeda.core.exception.UriNotFoundRuntimeException;

/**
 * @author manhole
 */
public class CompositeTaglibManager implements TaglibManager {

    private List taglibManagers = new ArrayList();

    public TaglibConfig getTaglibConfig(String uri)
            throws UriNotFoundRuntimeException {
        for (Iterator it = taglibManagers.iterator(); it.hasNext();) {
            TaglibManager taglibManager = (TaglibManager) it.next();
            if (taglibManager.hasTaglibConfig(uri)) {
                return taglibManager.getTaglibConfig(uri);
            }
        }
        throw new UriNotFoundRuntimeException(uri);
    }

    public boolean hasTaglibConfig(String uri) {
        for (Iterator it = taglibManagers.iterator(); it.hasNext();) {
            TaglibManager taglibManager = (TaglibManager) it.next();
            if (taglibManager.hasTaglibConfig(uri)) {
                return true;
            }
        }
        return false;
    }

    public void addTaglibManager(TaglibManager taglibManager) {
        taglibManagers.add(taglibManager);
    }

}
