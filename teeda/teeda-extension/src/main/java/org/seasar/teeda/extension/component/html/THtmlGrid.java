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

import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;

import org.seasar.teeda.extension.component.TForEach;

/**
 * @author manhole
 */
public class THtmlGrid extends TForEach implements NamingContainer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Grid";

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlGrid";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Grid";

    public static final String GRID = "Grid";

    public static final String GRID_X = "GridX";

    public static final String GRID_Y = "GridY";

    public static final String GRID_XY = "GridXY";

    public static final String SCROLL_HORIZONTAL = "scrollHorizontal";

    public static final String SCROLL_VERTICAL = "scrollVertical";

    public THtmlGrid() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    private String width;

    private boolean scrollVertical;

    private boolean scrollHorizontal;

    private String height;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String width) {
        this.height = width;
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[3];
        values[0] = super.saveState(context);
        values[1] = width;
        values[2] = height;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        width = (String) values[1];
        height = (String) values[2];
    }

    public boolean isScrollHorizontal() {
        return scrollHorizontal;
    }

    public void setScrollHorizontal(boolean scrollHorizontal) {
        this.scrollHorizontal = scrollHorizontal;
    }

    public boolean isScrollVertical() {
        return scrollVertical;
    }

    public void setScrollVertical(boolean scrollVertical) {
        this.scrollVertical = scrollVertical;
    }

}
