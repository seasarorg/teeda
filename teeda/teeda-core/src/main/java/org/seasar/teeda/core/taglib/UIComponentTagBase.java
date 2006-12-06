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
package org.seasar.teeda.core.taglib;

import javax.faces.application.Application;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.webapp.UIComponentTag;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.el.SimpleMethodBinding;
import org.seasar.teeda.core.exception.NoEditableValueHolderRuntimeException;
import org.seasar.teeda.core.exception.NoUICommandRuntimeException;
import org.seasar.teeda.core.exception.NoValueHolderRuntimeException;
import org.seasar.teeda.core.exception.NoValueReferenceRuntimeException;
import org.seasar.teeda.core.util.BindingUtil;

/**
 * @author yone
 * @author shot
 * @author manhole
 */
public abstract class UIComponentTagBase extends UIComponentTag {

    private static final Class[] VALIDATOR_ARGTYPES = { FacesContext.class,
            UIComponent.class, Object.class };

    private static final Class[] ACTION_LISTENER_ARGS = { ActionEvent.class };

    private static final Class[] VALUE_LISTENER_ARGS = { ValueChangeEvent.class };

    private String styleClass = null;

    private String title = null;

    private String enabledClass = null;

    private String disabledClass = null;

    private String onclick = null;

    private String ondblclick = null;

    private String onmousedown = null;

    private String onmouseover = null;

    private String onmousemove = null;

    private String onmouseout = null;

    private String onkeypress = null;

    private String onkeydown = null;

    private String onkeyup = null;

    private String lang = null;

    private String dir = null;

    private String height = null;

    private String width = null;

    private String cellspacing = null;

    private String cellpadding = null;

    private String disabled = null;

    private String size = null;

    private String tabindex = null;

    private String checked = null;

    private String border = null;

    private String readonly = null;

    private String ismap = null;

    private String maxlength = null;

    private String rows = null;

    private String cols = null;

    private String formatStyle = null;

    private String dateStyle = null;

    private String timeStyle = null;

    private String timezone = null;

    private String formatPattern = null;

    private String accept = null;

    private String acceptcharset = null;

    private String accesskey = null;

    private String action = null;

    private String alt = null;

    private String charset = null;

    private String coords = null;

    private String enctype = null;

    private String htmlFor = null;

    private String href = null;

    private String hreflang = null;

    private String hspace = null;

    private String longdesc = null;

    private String method = null;

    private String multiple = null;

    private String name = null;

    private String onblur = null;

    private String onchange = null;

    private String onfocus = null;

    private String onmouseup = null;

    private String onreset = null;

    private String onselect = null;

    private String onsubmit = null;

    private String rel = null;

    private String rev = null;

    private String selected = null;

    private String shape = null;

    private String src = null;

    private String style = null;

    private String target = null;

    private String type = null;

    private String usemap = null;

    private String value = null;

    private String summary = null;

    private String bgcolor = null;

    private String frame = null;

    private String rules = null;

    private String converter = null;

    public UIComponentTagBase() {
        super();
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getAcceptcharset() {
        return acceptcharset;
    }

    public void setAcceptcharset(String acceptcharset) {
        this.acceptcharset = acceptcharset;
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getCellpadding() {
        return cellpadding;
    }

    public void setCellpadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public String getCellspacing() {
        return cellspacing;
    }

    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getCols() {
        return cols;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }

    public String getConverter() {
        return converter;
    }

    public void setConverter(String converter) {
        this.converter = converter;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public String getDateStyle() {
        return dateStyle;
    }

    public void setDateStyle(String dateStyle) {
        this.dateStyle = dateStyle;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getDisabledClass() {
        return disabledClass;
    }

    public void setDisabledClass(String disabledClass) {
        this.disabledClass = disabledClass;
    }

    public String getEnabledClass() {
        return enabledClass;
    }

    public void setEnabledClass(String enabledClass) {
        this.enabledClass = enabledClass;
    }

    public String getEnctype() {
        return enctype;
    }

    public void setEnctype(String enctype) {
        this.enctype = enctype;
    }

    public String getFormatPattern() {
        return formatPattern;
    }

    public void setFormatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
    }

    public String getFormatStyle() {
        return formatStyle;
    }

    public void setFormatStyle(String formatStyle) {
        this.formatStyle = formatStyle;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getHreflang() {
        return hreflang;
    }

    public void setHreflang(String hreflang) {
        this.hreflang = hreflang;
    }

    public String getHspace() {
        return hspace;
    }

    public void setHspace(String hspace) {
        this.hspace = hspace;
    }

    public String getHtmlFor() {
        return htmlFor;
    }

    public void setHtmlFor(String htmlFor) {
        this.htmlFor = htmlFor;
    }

    public String getIsmap() {
        return ismap;
    }

    public void setIsmap(String ismap) {
        this.ismap = ismap;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }

    public String getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnblur() {
        return onblur;
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public String getOnchange() {
        return onchange;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOndblclick() {
        return ondblclick;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public String getOnfocus() {
        return onfocus;
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    public String getOnkeydown() {
        return onkeydown;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public String getOnkeypress() {
        return onkeypress;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public String getOnkeyup() {
        return onkeyup;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public String getOnmousedown() {
        return onmousedown;
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public String getOnmousemove() {
        return onmousemove;
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public String getOnmouseout() {
        return onmouseout;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getOnmouseover() {
        return onmouseover;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getOnmouseup() {
        return onmouseup;
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public String getOnreset() {
        return onreset;
    }

    public void setOnreset(String onreset) {
        this.onreset = onreset;
    }

    public String getOnselect() {
        return onselect;
    }

    public void setOnselect(String onselect) {
        this.onselect = onselect;
    }

    public String getOnsubmit() {
        return onsubmit;
    }

    public void setOnsubmit(String onsubmit) {
        this.onsubmit = onsubmit;
    }

    public String getReadonly() {
        return readonly;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTabindex() {
        return tabindex;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTimeStyle() {
        return timeStyle;
    }

    public void setTimeStyle(String timeStyle) {
        this.timeStyle = timeStyle;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsemap() {
        return usemap;
    }

    public void setUsemap(String usemap) {
        this.usemap = usemap;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    /*
     * actionはセットしない。
     * ActionSourceかどうかをinstanceofすればセットしても良さそうな気はするが。
     */
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, JsfConstants.ACCEPT_ATTR, accept);
        setComponentProperty(component, JsfConstants.ACCEPTCHARSET_ATTR,
                acceptcharset);
        setComponentProperty(component, JsfConstants.ACCESSKEY_ATTR, accesskey);
        //setComponentProperty(component, JsfConstants.ACTION_ATTR, action);
        setComponentProperty(component, JsfConstants.ALT_ATTR, alt);
        setComponentProperty(component, JsfConstants.BGCOLOR_ATTR, bgcolor);
        setComponentProperty(component, JsfConstants.BORDER_ATTR, border);
        setComponentProperty(component, JsfConstants.CELLPADDING_ATTR,
                cellpadding);
        setComponentProperty(component, JsfConstants.CELLSPACING_ATTR,
                cellspacing);
        setComponentProperty(component, JsfConstants.CHARSET_ATTR, charset);
        setComponentProperty(component, JsfConstants.CHECKED_ATTR, checked);
        setComponentProperty(component, JsfConstants.COLS_ATTR, cols);
        if (component instanceof ValueHolder) {
            setConverterProperty(component, converter);
        }
        setComponentProperty(component, JsfConstants.COORDS_ATTR, coords);
        setComponentProperty(component, JsfConstants.DATE_STYLE_ATTR, dateStyle);
        setComponentProperty(component, JsfConstants.DIR_ATTR, dir);
        setComponentProperty(component, JsfConstants.DISABLED_ATTR, disabled);
        setComponentProperty(component, JsfConstants.DISABLED_CLASS_ATTR,
                disabledClass);
        setComponentProperty(component, JsfConstants.ENABLED_CLASS_ATTR,
                enabledClass);
        setComponentProperty(component, JsfConstants.ENCTYPE_ATTR, enctype);
        setComponentProperty(component, JsfConstants.FORMAT_PATTERN_ATTR,
                formatPattern);
        setComponentProperty(component, JsfConstants.FORMAT_STYLE_ATTR,
                formatStyle);
        setComponentProperty(component, JsfConstants.FRAME_ATTR, frame);
        setComponentProperty(component, JsfConstants.HEIGHT_ATTR, height);
        setComponentProperty(component, JsfConstants.HREF_ATTR, href);
        setComponentProperty(component, JsfConstants.HREFLANG_ATTR, hreflang);
        setComponentProperty(component, JsfConstants.HSPACE_ATTR, hspace);
        setComponentProperty(component, JsfConstants.HTML_FOR_ATTR, htmlFor);
        setComponentProperty(component, JsfConstants.ISMAP_ATTR, ismap);
        setComponentProperty(component, JsfConstants.LANG_ATTR, lang);
        setComponentProperty(component, JsfConstants.LONGDESC_ATTR, longdesc);
        setComponentProperty(component, JsfConstants.MAXLENGTH_ATTR, maxlength);
        setComponentProperty(component, JsfConstants.METHOD_ATTR, method);
        setComponentProperty(component, JsfConstants.MULTIPLE_ATTR, multiple);
        setComponentProperty(component, JsfConstants.NAME_ATTR, name);

        setComponentProperty(component, JsfConstants.ONBLUR_ATTR, onblur);
        setComponentProperty(component, JsfConstants.ONCHANGE_ATTR, onchange);
        setComponentProperty(component, JsfConstants.ONCLICK_ATTR, onclick);
        setComponentProperty(component, JsfConstants.ONDBLCLICK_ATTR,
                ondblclick);
        setComponentProperty(component, JsfConstants.ONFOCUS_ATTR, onfocus);
        setComponentProperty(component, JsfConstants.ONKEYDOWN_ATTR, onkeydown);
        setComponentProperty(component, JsfConstants.ONKEYPRESS_ATTR,
                onkeypress);
        setComponentProperty(component, JsfConstants.ONKEYUP_ATTR, onkeyup);
        setComponentProperty(component, JsfConstants.ONMOUSEDOWN_ATTR,
                onmousedown);
        setComponentProperty(component, JsfConstants.ONMOUSEMOVE_ATTR,
                onmousemove);
        setComponentProperty(component, JsfConstants.ONMOUSEOUT_ATTR,
                onmouseout);
        setComponentProperty(component, JsfConstants.ONMOUSEOVER_ATTR,
                onmouseover);
        setComponentProperty(component, JsfConstants.ONMOUSEUP_ATTR, onmouseup);
        setComponentProperty(component, JsfConstants.ONRESET_ATTR, onreset);
        setComponentProperty(component, JsfConstants.ONSELECT_ATTR, onselect);
        setComponentProperty(component, JsfConstants.ONSUBMIT_ATTR, onsubmit);
        setComponentProperty(component, JsfConstants.READONLY_ATTR, readonly);
        setComponentProperty(component, JsfConstants.REL_ATTR, rel);
        setComponentProperty(component, JsfConstants.REV_ATTR, rev);
        setComponentProperty(component, JsfConstants.ROWS_ATTR, rows);
        setComponentProperty(component, JsfConstants.RULES_ATTR, rules);
        setComponentProperty(component, JsfConstants.SELECTED_ATTR, selected);
        setComponentProperty(component, JsfConstants.SHAPE_ATTR, shape);
        setComponentProperty(component, JsfConstants.SIZE_ATTR, size);
        setComponentProperty(component, JsfConstants.SRC_ATTR, src);
        setComponentProperty(component, JsfConstants.STYLE_ATTR, style);
        setComponentProperty(component, JsfConstants.STYLE_CLASS_ATTR,
                styleClass);
        setComponentProperty(component, JsfConstants.SUMMARY_ATTR, summary);
        setComponentProperty(component, JsfConstants.TABINDEX_ATTR, tabindex);
        setComponentProperty(component, JsfConstants.TARGET_ATTR, target);
        setComponentProperty(component, JsfConstants.TIME_STYLE_ATTR, timeStyle);
        setComponentProperty(component, JsfConstants.TIMEZONE_ATTR, timezone);
        setComponentProperty(component, JsfConstants.TITLE_ATTR, title);
        setComponentProperty(component, JsfConstants.TYPE_ATTR, type);
        setComponentProperty(component, JsfConstants.USEMAP_ATTR, usemap);
        setComponentProperty(component, JsfConstants.VALUE_ATTR, value);
        setComponentProperty(component, JsfConstants.WIDTH_ATTR, width);
    }

    public void release() {
        super.release();
        styleClass = null;
        title = null;
        enabledClass = null;
        disabledClass = null;
        onclick = null;
        ondblclick = null;
        onmousedown = null;
        onmouseover = null;
        onmousemove = null;
        onmouseout = null;
        onkeypress = null;
        onkeydown = null;
        onkeyup = null;
        lang = null;
        dir = null;
        height = null;
        width = null;
        cellspacing = null;
        cellpadding = null;
        disabled = null;
        size = null;
        tabindex = null;
        checked = null;
        border = null;
        readonly = null;
        ismap = null;
        maxlength = null;
        rows = null;
        cols = null;
        formatStyle = null;
        dateStyle = null;
        timeStyle = null;
        timezone = null;
        formatPattern = null;
        accept = null;
        acceptcharset = null;
        accesskey = null;
        action = null;
        alt = null;
        charset = null;
        coords = null;
        enctype = null;
        htmlFor = null;
        href = null;
        hreflang = null;
        hspace = null;
        longdesc = null;
        method = null;
        multiple = null;
        name = null;
        onblur = null;
        onchange = null;
        onfocus = null;
        onmouseup = null;
        onreset = null;
        onselect = null;
        onsubmit = null;
        rel = null;
        rev = null;
        selected = null;
        shape = null;
        src = null;
        style = null;
        target = null;
        type = null;
        usemap = null;
        value = null;
        summary = null;
        bgcolor = null;
        frame = null;
        rules = null;
        converter = null;
    }

    protected void setComponentProperty(UIComponent component,
            String propertyName, String value) {
        UIComponentTagPropertyUtil.setComponentProperty(component,
                propertyName, value);
    }

    protected void setConverterProperty(UIComponent component, String value) {
        if (value == null) {
            return;
        }
        if (!(component instanceof ValueHolder)) {
            throw new NoValueHolderRuntimeException(component.getClass());
        }
        if (isValueReference(value)) {
            BindingUtil.setValueBinding(component, JsfConstants.CONVERTER_ATTR,
                    value);
        } else {
            Converter converter = createConverter(value);
            ((ValueHolder) component).setConverter(converter);
        }
    }

    protected void setValidatorProperty(UIComponent component, String value) {
        if (value == null) {
            return;
        }
        if (!(component instanceof EditableValueHolder)) {
            throw new NoEditableValueHolderRuntimeException(component
                    .getClass());
        }
        if (!isValueReference(value)) {
            throw new NoValueReferenceRuntimeException(value);
        }
        MethodBinding mb = createMethodBinding(value, VALIDATOR_ARGTYPES);
        ((EditableValueHolder) component).setValidator(mb);
    }

    protected void setActionProperty(UIComponent component, String value) {
        if (value == null) {
            return;
        }
        if (!(component instanceof UICommand)) {
            throw new NoUICommandRuntimeException(component.getClass());
        }
        MethodBinding mb = null;
        if (isValueReference(value)) {
            mb = createMethodBinding(value, null);
        } else {
            mb = new SimpleMethodBinding(value);
        }
        ((UICommand) component).setAction(mb);
    }

    protected void setActionListenerProperty(UIComponent component, String value) {
        if (value == null) {
            return;
        }
        if (!(component instanceof UICommand)) {
            throw new NoUICommandRuntimeException(component.getClass());
        }
        MethodBinding mb = null;
        if (!isValueReference(value)) {
            throw new NoValueReferenceRuntimeException(value);
        }
        mb = createMethodBinding(value, ACTION_LISTENER_ARGS);
        ((UICommand) component).setActionListener(mb);
    }

    protected void setValueChangeListenerProperty(UIComponent component,
            String value) {
        if (value == null) {
            return;
        }
        if (!(component instanceof EditableValueHolder)) {
            throw new NoEditableValueHolderRuntimeException(component
                    .getClass());
        }
        if (!isValueReference(value)) {
            throw new NoValueReferenceRuntimeException(value);
        }
        MethodBinding mb = createMethodBinding(value, VALUE_LISTENER_ARGS);
        ((EditableValueHolder) component).setValueChangeListener(mb);

    }

    protected Application getApplication() {
        return FacesContext.getCurrentInstance().getApplication();
    }

    protected Converter createConverter(String value) {
        return getApplication().createConverter(value);
    }

    protected MethodBinding createMethodBinding(String value, Class[] argTypes) {
        return getApplication().createMethodBinding(value, argTypes);
    }

}
