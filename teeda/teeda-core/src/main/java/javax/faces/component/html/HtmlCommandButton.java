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

import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author shot
 */
public class HtmlCommandButton extends UICommand {

	public static final String COMPONENT_TYPE = "javax.faces.HtmlCommandButton";

	private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Button";

	private static final boolean DEFAULT_DISABLED = false;

	private static final boolean DEFAULT_READONLY = false;

	private static final String DEFAULT_TYPE = "submit";

	private String accesskey_ = null;

	private String alt_ = null;

	private String dir_ = null;

	private Boolean disabled_ = null;

	private String image_ = null;

	private String lang_ = null;

	private String onblur_ = null;

	private String onchange_ = null;

	private String onclick_ = null;

	private String ondblclick_ = null;

	private String onfocus_ = null;

	private String onkeydown_ = null;

	private String onkeypress_ = null;

	private String onkeyup_ = null;

	private String onmousedown_ = null;

	private String onmousemove_ = null;

	private String onmouseout_ = null;

	private String onmouseover_ = null;

	private String onmouseup_ = null;

	private String onselect_ = null;

	private Boolean readonly_ = null;

	private String style_ = null;

	private String styleClass_ = null;

	private String tabindex_ = null;

	private String title_ = null;

	private String type_ = null;

	public HtmlCommandButton() {
		super();
		setRendererType(DEFAULT_RENDERER_TYPE);
	}

	public void setAccesskey(String accesskey) {
		accesskey_ = accesskey;
	}

	public String getAccesskey() {
		if (accesskey_ != null){
			return accesskey_;
		}
		Object value = getValueBindingValue("accesskey");
		return (value != null) ? (String)value : null;
	}

	public void setAlt(String alt) {
		alt_ = alt;
	}

	public String getAlt() {
		if (alt_ != null){
			return alt_;
		}
		Object value = getValueBindingValue("alt");
		return (value != null) ? (String)value : null;
	}

	public void setDir(String dir) {
		dir_ = dir;
	}

	public String getDir() {
		if (dir_ != null){
			return dir_;
		}
		Object value = getValueBindingValue("dir");
		return (value != null) ? (String)value : null;
	}

	public void setDisabled(boolean disabled) {
		disabled_ = Boolean.valueOf(disabled);
	}

	public boolean isDisabled() {
		if (disabled_ != null){
			return disabled_.booleanValue();
		}
		Boolean value = (Boolean)getValueBindingValue("disabled");
		return (value != null) ? value.booleanValue() : DEFAULT_DISABLED;		
	}

	public void setImage(String image) {
		image_ = image;
	}

	public String getImage() {
		if (image_ != null){
			return image_;
		}
		Object value = getValueBindingValue("image");
		return (value != null) ? (String)value : null;
	}

	public void setLang(String lang) {
		lang_ = lang;
	}

	public String getLang() {
		if (lang_ != null){
			return lang_;
		}
		Object value = getValueBindingValue("lang");
		return (value != null) ? (String)value : null;
	}

	public void setOnblur(String onblur) {
		onblur_ = onblur;
	}

	public String getOnblur() {
		if (onblur_ != null){
			return onblur_;
		}
		Object value = getValueBindingValue("onblur");
		return (value != null) ? (String)value : null;
	}

	public void setOnchange(String onchange) {
		onchange_ = onchange;
	}

	public String getOnchange() {
		if (onchange_ != null){
			return onchange_;
		}
		Object value = getValueBindingValue("onchange");
		return (value != null) ? (String)value : null;
	}

	public void setOnclick(String onclick) {
		onclick_ = onclick;
	}

	public String getOnclick() {
		if (onclick_ != null){
			return onclick_;
		}
		Object value = getValueBindingValue("onclick");
		return (value != null) ? (String)value : null;
	}

	public void setOndblclick(String ondblclick) {
		ondblclick_ = ondblclick;
	}

	public String getOndblclick() {
		if (ondblclick_ != null){
			return ondblclick_;
		}
		Object value = getValueBindingValue("ondblclick");
		return (value != null) ? (String)value : null;
	}

	public void setOnfocus(String onfocus) {
		onfocus_ = onfocus;
	}

	public String getOnfocus() {
		if (onfocus_ != null){
			return onfocus_;
		}
		Object value = getValueBindingValue("onfocus");
		return (value != null) ? (String)value : null;
	}

	public void setOnkeydown(String onkeydown) {
		onkeydown_ = onkeydown;
	}

	public String getOnkeydown() {
		if (onkeydown_ != null){
			return onkeydown_;
		}
		Object value = getValueBindingValue("onkeydown");
		return (value != null) ? (String)value : null;
	}

	public void setOnkeypress(String onkeypress) {
		onkeypress_ = onkeypress;
	}

	public String getOnkeypress() {
		if (onkeypress_ != null){
			return onkeypress_;
		}
		Object value = getValueBindingValue("onkeypress");
		return (value != null) ? (String)value : null;
	}

	public void setOnkeyup(String onkeyup) {
		onkeyup_ = onkeyup;
	}

	public String getOnkeyup() {
		if (onkeyup_ != null){
			return onkeyup_;
		}
		Object value = getValueBindingValue("onkeyup");
		return (value != null) ? (String)value : null;
	}

	public void setOnmousedown(String onmousedown) {
		onmousedown_ = onmousedown;
	}

	public String getOnmousedown() {
		if (onmousedown_ != null){
			return onmousedown_;
		}
		Object value = getValueBindingValue("onmousedown");
		return (value != null) ? (String)value : null;
	}

	public void setOnmousemove(String onmousemove) {
		onmousemove_ = onmousemove;
	}

	public String getOnmousemove() {
		if (onmousemove_ != null){
			return onmousemove_;
		}
		Object value = getValueBindingValue("onmousemove");
		return (value != null) ? (String)value : null;
	}

	public void setOnmouseout(String onmouseout) {
		onmouseout_ = onmouseout;
	}

	public String getOnmouseout() {
		if (onmouseout_ != null){
			return onmouseout_;
		}
		Object value = getValueBindingValue("onmouseout");
		return (value != null) ? (String)value : null;
	}

	public void setOnmouseover(String onmouseover) {
		onmouseover_ = onmouseover;
	}

	public String getOnmouseover() {
		if (onmouseover_ != null){
			return onmouseover_;
		}
		Object value = getValueBindingValue("onmouseover");
		return (value != null) ? (String)value : null;
	}

	public void setOnmouseup(String onmouseup) {
		onmouseup_ = onmouseup;
	}

	public String getOnmouseup() {
		if (onmouseup_ != null){
			return onmouseup_;
		}
		Object value = getValueBindingValue("onmouseup");
		return (value != null) ? (String)value : null;
	}

	public void setOnselect(String onselect) {
		onselect_ = onselect;
	}

	public String getOnselect() {
		if (onselect_ != null){
			return onselect_;
		}
		Object value = getValueBindingValue("onselect");
		return (value != null) ? (String)value : null;
	}

	public void setReadonly(boolean readonly) {
		readonly_ = Boolean.valueOf(readonly);
	}

	public boolean isReadonly() {
		if (readonly_ != null){
			return readonly_.booleanValue();
		}
		Boolean value = (Boolean)getValueBindingValue("readonly");
		return (value != null) ? value.booleanValue() : DEFAULT_READONLY;
	}

	public void setStyle(String style) {
		style_ = style;
	}

	public String getStyle() {
		if (style_ != null){
			return style_;
		}
		Object value = getValueBindingValue("style");
		return (value != null) ? (String)value : null;
	}

	public void setStyleClass(String styleClass) {
		styleClass_ = styleClass;
	}

	public String getStyleClass() {
		if (styleClass_ != null){
			return styleClass_;
		}
		Object value = getValueBindingValue("styleClass");
		return (value != null) ? (String)value : null;
	}

	public void setTabindex(String tabindex) {
		tabindex_ = tabindex;
	}

	public String getTabindex() {
		if (tabindex_ != null){
			return tabindex_;
		}
		Object value = getValueBindingValue("tabindex");
		return (value != null) ? (String)value : null;
	}

	public void setTitle(String title) {
		title_ = title;
	}

	public String getTitle() {
		if (title_ != null){
			return title_;
		}
		Object value = getValueBindingValue("title");
		return (value != null) ? (String)value : null;
	}

	public void setType(String type) {
		type_ = type;
	}

	public String getType() {
		if (type_ != null){
			return type_;
		}
		Object value = getValueBindingValue("type");
		return (value != null) ? (String)value : DEFAULT_TYPE;
	}

	public Object saveState(FacesContext context) {
		Object values[] = new Object[27];
		values[0] = super.saveState(context);
		values[1] = accesskey_;
		values[2] = alt_;
		values[3] = dir_;
		values[4] = disabled_;
		values[5] = image_;
		values[6] = lang_;
		values[7] = onblur_;
		values[8] = onchange_;
		values[9] = onclick_;
		values[10] = ondblclick_;
		values[11] = onfocus_;
		values[12] = onkeydown_;
		values[13] = onkeypress_;
		values[14] = onkeyup_;
		values[15] = onmousedown_;
		values[16] = onmousemove_;
		values[17] = onmouseout_;
		values[18] = onmouseover_;
		values[19] = onmouseup_;
		values[20] = onselect_;
		values[21] = readonly_;
		values[22] = style_;
		values[23] = styleClass_;
		values[24] = tabindex_;
		values[25] = title_;
		values[26] = type_;
		return values;
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		accesskey_ = (String) values[1];
		alt_ = (String) values[2];
		dir_ = (String) values[3];
		disabled_ = (Boolean) values[4];
		image_ = (String) values[5];
		lang_ = (String) values[6];
		onblur_ = (String) values[7];
		onchange_ = (String) values[8];
		onclick_ = (String) values[9];
		ondblclick_ = (String) values[10];
		onfocus_ = (String) values[11];
		onkeydown_ = (String) values[12];
		onkeypress_ = (String) values[13];
		onkeyup_ = (String) values[14];
		onmousedown_ = (String) values[15];
		onmousemove_ = (String) values[16];
		onmouseout_ = (String) values[17];
		onmouseover_ = (String) values[18];
		onmouseup_ = (String) values[19];
		onselect_ = (String) values[20];
		readonly_ = (Boolean) values[21];
		style_ = (String) values[22];
		styleClass_ = (String) values[23];
		tabindex_ = (String) values[24];
		title_ = (String) values[25];
		type_ = (String) values[26];
	}
	
	private Object getValueBindingValue(String bindingName){
		ValueBinding vb = getValueBinding(bindingName);
		return (vb != null) ? vb.getValue(getFacesContext()) : null;
	}

}
