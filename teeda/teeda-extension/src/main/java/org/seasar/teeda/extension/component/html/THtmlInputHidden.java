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

import java.io.Serializable;
import java.util.List;

import javax.faces.component.UIInput;

/**
 * @author manhole
 */
public class THtmlInputHidden extends UIInput {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlInputHidden";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Hidden";

    public THtmlInputHidden() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public static class ComponentHolder implements Serializable {

        private static final long serialVersionUID = 1L;

        /*
         * 個々の要素の型
         */
        private String componentClassName;

        /*
         * 配列型の場合に配列型
         */
        private String arrayClassName;

        private List value;

        public List getValue() {
            return value;
        }

        public void setValue(List value) {
            this.value = value;
        }

        public String getComponentClassName() {
            return componentClassName;
        }

        public void setComponentClassName(String componentClassName) {
            this.componentClassName = componentClassName;
        }

        public String getArrayClassName() {
            return arrayClassName;
        }

        public void setArrayClassName(String holderClassName) {
            this.arrayClassName = holderClassName;
        }

    }

}
