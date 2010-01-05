/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import javax.faces.component.ComponentUtil_;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 */
public class HtmlDataTable extends UIData {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlDataTable";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Table";

    private static final int DEFAULTBORDER = Integer.MIN_VALUE;

    private String bgcolor = null;

    private Integer border = null;

    private String cellpadding = null;

    private String cellspacing = null;

    private String columnClasses = null;

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

    public HtmlDataTable() {
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
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.BGCOLOR_ATTR);
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
        return v != null ? v.intValue() : DEFAULTBORDER;
    }

    public void setCellpadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public String getCellpadding() {
        if (cellpadding != null) {
            return cellpadding;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.CELLPADDING_ATTR);
    }

    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public String getCellspacing() {
        if (cellspacing != null) {
            return cellspacing;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.CELLSPACING_ATTR);
    }

    public void setColumnClasses(String columnClasses) {
        this.columnClasses = columnClasses;
    }

    public String getColumnClasses() {
        if (columnClasses != null) {
            return columnClasses;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.COLUMN_CLASSES_ATTR);
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        if (dir != null) {
            return dir;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.DIR_ATTR);
    }

    public void setFooterClass(String footerClass) {
        this.footerClass = footerClass;
    }

    public String getFooterClass() {
        if (footerClass != null) {
            return footerClass;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.FOOTER_CLASS_ATTR);
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getFrame() {
        if (frame != null) {
            return frame;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.FRAME_ATTR);
    }

    public void setHeaderClass(String headerClass) {
        this.headerClass = headerClass;
    }

    public String getHeaderClass() {
        if (headerClass != null) {
            return headerClass;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.HEADER_CLASS_ATTR);
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        if (lang != null) {
            return lang;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.LANG_ATTR);
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnclick() {
        if (onclick != null) {
            return onclick;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONCLICK_ATTR);
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public String getOndblclick() {
        if (ondblclick != null) {
            return ondblclick;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONDBLCLICK_ATTR);
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public String getOnkeydown() {
        if (onkeydown != null) {
            return onkeydown;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONKEYDOWN_ATTR);
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public String getOnkeypress() {
        if (onkeypress != null) {
            return onkeypress;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONKEYPRESS_ATTR);
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public String getOnkeyup() {
        if (onkeyup != null) {
            return onkeyup;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONKEYUP_ATTR);
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public String getOnmousedown() {
        if (onmousedown != null) {
            return onmousedown;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEDOWN_ATTR);
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public String getOnmousemove() {
        if (onmousemove != null) {
            return onmousemove;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEMOVE_ATTR);
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getOnmouseout() {
        if (onmouseout != null) {
            return onmouseout;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEOUT_ATTR);
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getOnmouseover() {
        if (onmouseover != null) {
            return onmouseover;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEOVER_ATTR);
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public String getOnmouseup() {
        if (onmouseup != null) {
            return onmouseup;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ONMOUSEUP_ATTR);
    }

    public void setRowClasses(String rowClasses) {
        this.rowClasses = rowClasses;
    }

    public String getRowClasses() {
        if (rowClasses != null) {
            return rowClasses;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.ROW_CLASSES_ATTR);
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getRules() {
        if (rules != null) {
            return rules;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.RULES_ATTR);
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        if (style != null) {
            return style;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.STYLE_ATTR);
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        if (styleClass != null) {
            return styleClass;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.STYLE_CLASS_ATTR);
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        if (summary != null) {
            return summary;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.SUMMARY_ATTR);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        if (title != null) {
            return title;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.TITLE_ATTR);
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        if (width != null) {
            return width;
        }
        return ComponentUtil_.getValueBindingValueAsString(this,
                JsfConstants.WIDTH_ATTR);
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[28];
        values[0] = super.saveState(context);
        values[1] = bgcolor;
        values[2] = border;
        values[3] = cellpadding;
        values[4] = cellspacing;
        values[5] = columnClasses;
        values[6] = dir;
        values[7] = footerClass;
        values[8] = frame;
        values[9] = headerClass;
        values[10] = lang;
        values[11] = onclick;
        values[12] = ondblclick;
        values[13] = onkeydown;
        values[14] = onkeypress;
        values[15] = onkeyup;
        values[16] = onmousedown;
        values[17] = onmousemove;
        values[18] = onmouseout;
        values[19] = onmouseover;
        values[20] = onmouseup;
        values[21] = rowClasses;
        values[22] = rules;
        values[23] = style;
        values[24] = styleClass;
        values[25] = summary;
        values[26] = title;
        values[27] = width;
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
        dir = (String) values[6];
        footerClass = (String) values[7];
        frame = (String) values[8];
        headerClass = (String) values[9];
        lang = (String) values[10];
        onclick = (String) values[11];
        ondblclick = (String) values[12];
        onkeydown = (String) values[13];
        onkeypress = (String) values[14];
        onkeyup = (String) values[15];
        onmousedown = (String) values[16];
        onmousemove = (String) values[17];
        onmouseout = (String) values[18];
        onmouseover = (String) values[19];
        onmouseup = (String) values[20];
        rowClasses = (String) values[21];
        rules = (String) values[22];
        style = (String) values[23];
        styleClass = (String) values[24];
        summary = (String) values[25];
        title = (String) values[26];
        width = (String) values[27];
    }

}
