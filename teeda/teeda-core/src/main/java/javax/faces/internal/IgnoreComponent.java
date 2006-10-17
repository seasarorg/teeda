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
package javax.faces.internal;

import java.util.ArrayList;
import java.util.List;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 *
 */
public class IgnoreComponent {

    private final List ignoreComponentNameList;

    public IgnoreComponent() {
        ignoreComponentNameList = new ArrayList();
        setupDefaultIgnores();
    }

    public Object[] getIgnoreComponentNames() {
        return ignoreComponentNameList.toArray();
    }

    public void addIgnoreComponentName(final String componentName) {
        AssertionUtil.assertNotNull("ignorerable", componentName);
        ignoreComponentNameList.add(componentName);
    }

    public void clear() {
        ignoreComponentNameList.clear();
        setupDefaultIgnores();
    }

    protected void setupDefaultIgnores() {
        addIgnoreComponentName("attributes");
        addIgnoreComponentName("children");
        addIgnoreComponentName("childCount");
        addIgnoreComponentName("converter");
        addIgnoreComponentName("facets");
        addIgnoreComponentName("facetsAndChildren");
        addIgnoreComponentName("family");
        addIgnoreComponentName("localValue");
        addIgnoreComponentName("localValueSet");
        addIgnoreComponentName("parent");
        addIgnoreComponentName("submittedValue");
        addIgnoreComponentName("transient");
        addIgnoreComponentName("rendered");
        addIgnoreComponentName("rendererType");
        addIgnoreComponentName("rendersChildren");
        addIgnoreComponentName("valid");
        addIgnoreComponentName("validator");
        addIgnoreComponentName("validators");
        addIgnoreComponentName("valueChangeListener");
        addIgnoreComponentName("valueChangeListeners");
    }

}
