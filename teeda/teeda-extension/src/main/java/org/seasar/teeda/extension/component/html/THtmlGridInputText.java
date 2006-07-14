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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.UIInput;

/**
 * @author manhole
 */
public class THtmlGridInputText extends UIInput {

    public static final String COMPONENT_FAMILY = UIInput.COMPONENT_FAMILY;

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlGridInputText";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.GridInputText";

    public THtmlGridInputText() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return THtmlGridInputText.COMPONENT_FAMILY;
    }

}
