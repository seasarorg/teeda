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
package org.seasar.teeda.core.render;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;

import org.seasar.teeda.core.exception.NoEditableValueHolderRuntimeException;

/**
 * @author shot
 */
public class EditableValueHolderDecoder extends AbstractDecoder {

    protected ValueHolderWrapper createValueHolderWrapper(UIComponent component) {
        if (!(component instanceof EditableValueHolder)) {
            throw new NoEditableValueHolderRuntimeException(component
                    .getClass());
        }
        return new ValueHolderWrapper((EditableValueHolder)component);
    }

}
