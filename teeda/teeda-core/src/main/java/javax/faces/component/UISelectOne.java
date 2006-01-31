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
package javax.faces.component;

import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtils;
import javax.faces.internal.SelectItemsIterator;

/**
 * @author shot
 * @author manhole
 */
public class UISelectOne extends UIInput {

    public static final String COMPONENT_FAMILY = "javax.faces.SelectOne";

    public static final String COMPONENT_TYPE = "javax.faces.SelectOne";

    public static final String INVALID_MESSAGE_ID = "javax.faces.component.UISelectOne.INVALID";

    private static final String DEFAULT_RENDER_TYPE = "javax.faces.Menu";

    public UISelectOne() {
        setRendererType(DEFAULT_RENDER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    protected void validateValue(FacesContext context, Object value) {
        ComponentUtils_.assertNotNull("context", context);
        super.validateValue(context, value);
        if (!isValid() || value == null) {
            return;
        }

        if (!ComponentUtils_.valueMatches(value, new SelectItemsIterator(this))) {
            Object[] args = { getId() };
            FacesMessageUtils.addErrorMessage(context, this,
                    INVALID_MESSAGE_ID, args);
            setValid(false);
        }
    }
}
