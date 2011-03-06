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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.core.taglib.html.OutputTextTag;
import org.seasar.teeda.extension.component.html.THtmlOutputText;

/**
 * @author shot
 * @author yone
 */
public class TOutputTextTag extends OutputTextTag {

    private String tagName;

    private String invisible;

    private String omittag;

    public String getComponentType() {
        return THtmlOutputText.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlOutputText.DEFAULT_RENDERER_TYPE;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getInvisible() {
        return invisible;
    }

    public void setInvisible(String invisible) {
        this.invisible = invisible;
    }

    public String getOmittag() {
        return omittag;
    }

    public void setOmittag(String omittag) {
        this.omittag = omittag;
    }

    public void release() {
        super.release();
        tagName = null;
        invisible = null;
        omittag = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, "tagName", tagName);
        setComponentProperty(component, "invisible", invisible);
        setComponentProperty(component, "omittag", omittag);
    }

}
