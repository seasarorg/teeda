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
package org.seasar.teeda.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author shot
 */
public class StateManagerUtil {

    private StateManagerUtil() {
    }

    public static boolean isSavingStateInClient(FacesContext context) {
        return FacesContextUtil.getStateManager(context).isSavingStateInClient(
                context);
    }

    public static void assertComponentNoDuplicateId(UIComponent component) {
        assertComponentNoDuplicateIdInternal(component, new ArrayList());
    }

    private static void assertComponentNoDuplicateIdInternal(
            UIComponent component, List idList) {
        String id = component.getId();
        if (id != null && idList.contains(id)) {
            throw new IllegalStateException("Component id:" + id
                    + " has same id in view tree.");
        }
        idList.add(id);
        for (Iterator itr = component.getFacetsAndChildren(); itr.hasNext();) {
            UIComponent child = (UIComponent) itr.next();
            if (component instanceof NamingContainer) {
                assertComponentNoDuplicateId(child);
            } else {
                assertComponentNoDuplicateIdInternal(child, idList);
            }
        }
    }

}
