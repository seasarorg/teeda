/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package javax.faces.internal;

import java.util.ArrayList;
import java.util.List;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 * @author manhole
 */
public class IgnoreAttribute {

    private final List names = new ArrayList();

    public IgnoreAttribute() {
        setupDefaultIgnores();
    }

    public Object[] getAttributeNames() {
        return names.toArray();
    }

    public void addAttributeName(final String name) {
        AssertionUtil.assertNotNull("name", name);
        names.add(name);
    }

    public void clear() {
        names.clear();
        setupDefaultIgnores();
    }

    protected void setupDefaultIgnores() {
        addAttributeName("attributes");
        addAttributeName("actionListeners");
        addAttributeName("children");
        addAttributeName("childCount");
        addAttributeName("converter");
        addAttributeName("facets");
        addAttributeName("facetsAndChildren");
        addAttributeName("family");
        addAttributeName("localValue");
        addAttributeName("localValueSet");
        addAttributeName("parent");
        addAttributeName("submittedValue");
        addAttributeName("transient");
        addAttributeName("rendered");
        addAttributeName("rendererType");
        addAttributeName("rendersChildren");
        addAttributeName("valid");
        addAttributeName("validator");
        addAttributeName("validators");
        addAttributeName("valueChangeListener");
        addAttributeName("valueChangeListeners");
    }

}
