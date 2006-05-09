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
package javax.faces.component.html;

import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author shot
 */
public class HtmlPanelGrid extends UIPanel {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlPanelGrid";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Grid";

    private static final int DEFAULT_BORDER = Integer.MIN_VALUE;

    private static final int DEFAULT_COLUMNS = Integer.MIN_VALUE;

    private String bgcolor = null;

    private Integer border = null;

    private String cellpadding = null;

    private String cellspacing = null;

    private String columnClasses = null;

    private Integer columns = null;

    private String dir = null;

    private String footerClass = null;

    private String frame = null;

    private String headerClass = null;

    private String lang = null;

    private String onclick = null;

    private String ondblclick = null;

    private String onkeydown = null;

    private String onkeypress = null;

    private String onkeyup = null;

    private String onmousedown = null;

    private String onmousemove = null;

    private String onmouseout = null;

    private String onmouseover = null;

    private String onmouseup = null;

    private String rowClasses = null;

    private String rules = null;

    private String style = null;

    private String styleClass = null;

    private String summary = null;

    private String title = null;

    private String width = null;

    public HtmlPanelGrid() {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getBgcolor() {
        if (bgcolor != null) {
            return bgcolor;
        }
        ValueBinding vb = getValueBinding("bgcolor");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setBorder(int border) {
        this.border = new Integer(border);
    }

    public int getBorder() {
        if (border != null) {
            return border.intValue();
        }
        ValueBinding vb = getValueBinding("border");
        Integer v = vb != null ? (Integer) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.intValue() : DEFAULT_BORDER;
    }

    public void setCellpadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public String getCellpadding() {
        if (cellpadding != null) {
            return cellpadding;
        }
        ValueBinding vb = getValueBinding("cellpadding");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public String getCellspacing() {
        if (cellspacing != null) {
            return cellspacing;
        }
        ValueBinding vb = getValueBinding("cellspacing");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setColumnClasses(String columnClasses) {
        this.columnClasses = columnClasses;
    }

    public String getColumnClasses() {
        if (columnClasses != null) {
            return columnClasses;
        }
        ValueBinding vb = getValueBinding("columnClasses");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setColumns(int columns) {
        this.columns = new Integer(columns);
    }

    public int getColumns() {
        if (columns != null) {
            return columns.intValue();
        }
        ValueBinding vb = getValueBinding("columns");
        Integer v = vb != null ? (Integer) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.intValue() : DEFAULT_COLUMNS;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        if (dir != null) {
            return dir;
        }
        ValueBinding vb = getValueBinding("dir");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setFooterClass(String footerClass) {
        this.footerClass = footerClass;
    }

    public String getFooterClass() {
        if (footerClass != null) {
            return footerClass;
        }
        ValueBinding vb = getValueBinding("footerClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getFrame() {
        if (frame != null) {
            return frame;
        }
        ValueBinding vb = getValueBinding("frame");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setHeaderClass(String headerClass) {
        this.headerClass = headerClass;
    }

    public String getHeaderClass() {
        if (headerClass != null) {
            return headerClass;
        }
        ValueBinding vb = getValueBinding("headerClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        if (lang != null) {
            return lang;
        }
        ValueBinding vb = getValueBinding("lang");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnclick() {
        if (onclick != null) {
            return onclick;
        }
        ValueBinding vb = getValueBinding("onclick");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public String getOndblclick() {
        if (ondblclick != null) {
            return ondblclick;
        }
        ValueBinding vb = getValueBinding("ondblclick");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public String getOnkeydown() {
        if (onkeydown != null) {
            return onkeydown;
        }
        ValueBinding vb = getValueBinding("onkeydown");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public String getOnkeypress() {
        if (onkeypress != null) {
            return onkeypress;
        }
        ValueBinding vb = getValueBinding("onkeypress");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public String getOnkeyup() {
        if (onkeyup != null) {
            return onkeyup;
        }
        ValueBinding vb = getValueBinding("onkeyup");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public String getOnmousedown() {
        if (onmousedown != null) {
            return onmousedown;
        }
        ValueBinding vb = getValueBinding("onmousedown");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public String getOnmousemove() {
        if (onmousemove != null) {
            return onmousemove;
        }
        ValueBinding vb = getValueBinding("onmousemove");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getOnmouseout() {
        if (onmouseout != null) {
            return onmouseout;
        }
        ValueBinding vb = getValueBinding("onmouseout");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getOnmouseover() {
        if (onmouseover != null) {
            return onmouseover;
        }
        ValueBinding vb = getValueBinding("onmouseover");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public String getOnmouseup() {
        if (onmouseup != null) {
            return onmouseup;
        }
        ValueBinding vb = getValueBinding("onmouseup");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setRowClasses(String rowClasses) {
        this.rowClasses = rowClasses;
    }

    public String getRowClasses() {
        if (rowClasses != null) {
            return rowClasses;
        }
        ValueBinding vb = getValueBinding("rowClasses");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getRules() {
        if (rules != null) {
            return rules;
        }
        ValueBinding vb = getValueBinding("rules");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        if (style != null) {
            return style;
        }
        ValueBinding vb = getValueBinding("style");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        if (styleClass != null) {
            return styleClass;
        }
        ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        if (summary != null) {
            return summary;
        }
        ValueBinding vb = getValueBinding("summary");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        if (title != null) {
            return title;
        }
        ValueBinding vb = getValueBinding("title");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        if (width != null) {
            return width;
        }
        ValueBinding vb = getValueBinding("width");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[29];
        values[0] = super.saveState(context);
        values[1] = bgcolor;
        values[2] = border;
        values[3] = cellpadding;
        values[4] = cellspacing;
        values[5] = columnClasses;
        values[6] = columns;
        values[7] = dir;
        values[8] = footerClass;
        values[9] = frame;
        values[10] = headerClass;
        values[11] = lang;
        values[12] = onclick;
        values[13] = ondblclick;
        values[14] = onkeydown;
        values[15] = onkeypress;
        values[16] = onkeyup;
        values[17] = onmousedown;
        values[18] = onmousemove;
        values[19] = onmouseout;
        values[20] = onmouseover;
        values[21] = onmouseup;
        values[22] = rowClasses;
        values[23] = rules;
        values[24] = style;
        values[25] = styleClass;
        values[26] = summary;
        values[27] = title;
        values[28] = width;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        bgcolor = (String) values[1];
        border = (Integer) values[2];
        cellpadding = (String) values[3];
        cellspacing = (String) values[4];
        columnClasses = (String) values[5];
        columns = (Integer) values[6];
        dir = (String) values[7];
        footerClass = (String) values[8];
        frame = (String) values[9];
        headerClass = (String) values[10];
        lang = (String) values[11];
        onclick = (String) values[12];
        ondblclick = (String) values[13];
        onkeydown = (String) values[14];
        onkeypress = (String) values[15];
        onkeyup = (String) values[16];
        onmousedown = (String) values[17];
        onmousemove = (String) values[18];
        onmouseout = (String) values[19];
        onmouseover = (String) values[20];
        onmouseup = (String) values[21];
        rowClasses = (String) values[22];
        rules = (String) values[23];
        style = (String) values[24];
        styleClass = (String) values[25];
        summary = (String) values[26];
        title = (String) values[27];
        width = (String) values[28];
    }
}
