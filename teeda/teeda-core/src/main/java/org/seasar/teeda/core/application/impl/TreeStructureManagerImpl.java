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
package org.seasar.teeda.core.application.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;

import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.application.TreeStructure;
import org.seasar.teeda.core.application.TreeStructureManager;

/**
 * @author shot
 */
public class TreeStructureManagerImpl implements TreeStructureManager,
        Serializable {

    private static final long serialVersionUID = 1L;

    public TreeStructure buildTreeStructure(UIComponent component) {
        TreeStructure struct = new UIComponentTreeStructure(component);
        struct.setChildren(buildChildrenTreeStructure(component));
        struct.setFacets(buildFacetsTreeStructure(component));
        return struct;
    }

    public UIComponent restoreTreeStructure(TreeStructure structure) {
        String className = structure.getComponentClassName();
        String id = structure.getComponentId();
        UIComponent component = (UIComponent) ClassUtil.newInstance(className);
        component.setId(id);
        restoreChildrenTreeStructure(component, structure.getChildren());
        restoreFacetsTreeStructure(component, structure.getFacets());
        return component;
    }

    protected TreeStructure[] buildChildrenTreeStructure(UIComponent component) {
        if (component.getChildCount() > 0) {
            List structs = new ArrayList();
            for (Iterator itr = component.getChildren().iterator(); itr
                    .hasNext();) {
                UIComponent child = (UIComponent) itr.next();
                if (!child.isTransient()) {
                    TreeStructure childStruct = buildTreeStructure(child);
                    structs.add(childStruct);
                }
            }
            return (TreeStructure[]) structs.toArray(new TreeStructure[structs
                    .size()]);
        }
        return null;
    }

    protected Object[] buildFacetsTreeStructure(UIComponent component) {
        Map facets = component.getFacets();
        if (!facets.isEmpty()) {
            List structs = new ArrayList();
            for (Iterator it = facets.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                UIComponent child = (UIComponent) entry.getValue();
                if (!child.isTransient()) {
                    String facetName = (String) entry.getKey();
                    TreeStructure struct = buildTreeStructure(child);
                    structs.add(new Object[] { facetName, struct });
                }
            }
            return structs.toArray();
        }
        return null;
    }

    protected void restoreChildrenTreeStructure(UIComponent component,
            TreeStructure[] structs) {
        for (int i = 0; i < structs.length; ++i) {
            UIComponent child = restoreTreeStructure(structs[i]);
            component.getChildren().add(child);
        }
    }

    protected void restoreFacetsTreeStructure(UIComponent component,
            Object[] facets) {
        for (int i = 0, len = facets.length; i < len; i++) {
            Object[] array = (Object[]) facets[i];
            String facetName = (String) array[0];
            TreeStructure struct = (TreeStructure) array[1];
            UIComponent child = restoreTreeStructure(struct);
            component.getFacets().put(facetName, child);
        }
    }

    private static class UIComponentTreeStructure extends TreeStructure {

        private static final long serialVersionUID = 1L;

        public UIComponentTreeStructure(UIComponent component) {
            super(component.getClass().getName(), component.getId());
        }
    }

}
