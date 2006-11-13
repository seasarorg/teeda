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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author manhole
 *
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class ComponentStates {

    private Map savedStates = new HashMap();

    public void restoreDescendantState(FacesContext context,
            UIComponent component) {
        for (final Iterator it = component.getFacetsAndChildren(); it.hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            NamingContainerUtil.refreshClientId(child);
            if (child instanceof EditableValueHolder) {
                final EditableValueHolder holder = (EditableValueHolder) child;
                final String clientId = child.getClientId(context);
                SavedState state = (SavedState) savedStates.get(clientId);
                if (state == null) {
                    state = new SavedState();
                    savedStates.put(clientId, state);
                }
                state.restore(holder);
            }
            restoreDescendantState(context, child);
        }
    }

    public void saveDescendantComponentStates(FacesContext context,
            UIComponent component) {
        for (final Iterator it = component.getFacetsAndChildren(); it.hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof EditableValueHolder) {
                final EditableValueHolder holder = (EditableValueHolder) child;
                final SavedState state = new SavedState();
                state.save(holder);
                savedStates.put(child.getClientId(context), state);
            }
            saveDescendantComponentStates(context, child);
        }
    }

    public void clear() {
        savedStates.clear();
    }

}
