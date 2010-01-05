/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.util;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author higa
 * 
 */
public class NullUIComponent extends UIComponentBase {

    public static final UIComponent INSTANCE = new NullUIComponent();

    public String getClientId(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        return "";
    }

    public String getFamily() {
        return null;
    }
}