/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.html;

public interface PageDesc {

    String getPageName();

    boolean hasProperty(String name);

    boolean hasItemsProperty(String name);

    boolean hasDynamicProperty(String name);

    boolean hasRedirectScopeProperty();

    boolean hasSubapplicationScopeProperty();

    boolean hasPageScopeProperty();

    boolean isRedirectScopeProperty(String name);

    boolean isSubapplicationScopeProperty(String name);

    boolean isPageScopeProperty(String name);

    String[] getRedirectScopePropertyNames();

    String[] getSubapplicationScopePropertyNames();

    String[] getPageScopePropertyNames();

    boolean hasMethod(String name);

    boolean hasTakeOverDesc(String methodName);

    TakeOverDesc getTakeOverDesc(String methodName);

    boolean isModified();
}
