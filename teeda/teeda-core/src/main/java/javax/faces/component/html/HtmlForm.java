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

import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class HtmlForm extends UIForm {

	public static final String COMPONENT_TYPE = "javax.faces.HtmlForm";

	private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Form";

	private static final String DEFAULT_ENCTYPE = "application/x-www-form-urlencoded";

	private String accept_ = null;

	private String acceptcharset_ = null;

	private String dir_ = null;

	private String enctype_ = null;

	private String lang_ = null;

	private String onclick_ = null;

	private String ondblclick_ = null;

	private String onkeydown_ = null;

	private String onkeypress_ = null;

	private String onkeyup_ = null;

	private String onmousedown_ = null;

	private String onmousemove_ = null;

	private String onmouseout_ = null;

	private String onmouseover_ = null;

	private String onmouseup_ = null;

	private String onreset_ = null;

	private String onsubmit_ = null;

	private String style_ = null;

	private String styleClass_ = null;

	private String target_ = null;

	private String title_ = null;

	public HtmlForm() {
		setRendererType(DEFAULT_RENDERER_TYPE);
	}

	public void setAccept(String accept) {
		accept_ = accept;
	}

	public String getAccept() {
		if (accept_ != null)
			return accept_;
		ValueBinding vb = getValueBinding("accept");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setAcceptcharset(String acceptcharset) {
		acceptcharset_ = acceptcharset;
	}

	public String getAcceptcharset() {
		if (acceptcharset_ != null)
			return acceptcharset_;
		ValueBinding vb = getValueBinding("acceptcharset");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setDir(String dir) {
		dir_ = dir;
	}

	public String getDir() {
		if (dir_ != null)
			return dir_;
		ValueBinding vb = getValueBinding("dir");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setEnctype(String enctype) {
		enctype_ = enctype;
	}

	public String getEnctype() {
		if (enctype_ != null)
			return enctype_;
		ValueBinding vb = getValueBinding("enctype");
		return vb != null ? (String) vb.getValue(getFacesContext())
				: DEFAULT_ENCTYPE;
	}

	public void setLang(String lang) {
		lang_ = lang;
	}

	public String getLang() {
		if (lang_ != null)
			return lang_;
		ValueBinding vb = getValueBinding("lang");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setOnclick(String onclick) {
		onclick_ = onclick;
	}

	public String getOnclick() {
		if (onclick_ != null)
			return onclick_;
		ValueBinding vb = getValueBinding("onclick");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setOndblclick(String ondblclick) {
		ondblclick_ = ondblclick;
	}

	public String getOndblclick() {
		if (ondblclick_ != null)
			return ondblclick_;
		ValueBinding vb = getValueBinding("ondblclick");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setOnkeydown(String onkeydown) {
		onkeydown_ = onkeydown;
	}

	public String getOnkeydown() {
		if (onkeydown_ != null)
			return onkeydown_;
		ValueBinding vb = getValueBinding("onkeydown");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setOnkeypress(String onkeypress) {
		onkeypress_ = onkeypress;
	}

	public String getOnkeypress() {
		if (onkeypress_ != null)
			return onkeypress_;
		ValueBinding vb = getValueBinding("onkeypress");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setOnkeyup(String onkeyup) {
		onkeyup_ = onkeyup;
	}

	public String getOnkeyup() {
		if (onkeyup_ != null)
			return onkeyup_;
		ValueBinding vb = getValueBinding("onkeyup");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setOnmousedown(String onmousedown) {
		onmousedown_ = onmousedown;
	}

	public String getOnmousedown() {
		if (onmousedown_ != null)
			return onmousedown_;
		ValueBinding vb = getValueBinding("onmousedown");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setOnmousemove(String onmousemove) {
		onmousemove_ = onmousemove;
	}

	public String getOnmousemove() {
		if (onmousemove_ != null)
			return onmousemove_;
		ValueBinding vb = getValueBinding("onmousemove");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setOnmouseout(String onmouseout) {
		onmouseout_ = onmouseout;
	}

	public String getOnmouseout() {
		if (onmouseout_ != null)
			return onmouseout_;
		ValueBinding vb = getValueBinding("onmouseout");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setOnmouseover(String onmouseover) {
		onmouseover_ = onmouseover;
	}

	public String getOnmouseover() {
		if (onmouseover_ != null)
			return onmouseover_;
		ValueBinding vb = getValueBinding("onmouseover");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setOnmouseup(String onmouseup) {
		onmouseup_ = onmouseup;
	}

	public String getOnmouseup() {
		if (onmouseup_ != null)
			return onmouseup_;
		ValueBinding vb = getValueBinding("onmouseup");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setOnreset(String onreset) {
		onreset_ = onreset;
	}

	public String getOnreset() {
		if (onreset_ != null)
			return onreset_;
		ValueBinding vb = getValueBinding("onreset");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setOnsubmit(String onsubmit) {
		onsubmit_ = onsubmit;
	}

	public String getOnsubmit() {
		if (onsubmit_ != null)
			return onsubmit_;
		ValueBinding vb = getValueBinding("onsubmit");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setStyle(String style) {
		style_ = style;
	}

	public String getStyle() {
		if (style_ != null)
			return style_;
		ValueBinding vb = getValueBinding("style");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setStyleClass(String styleClass) {
		styleClass_ = styleClass;
	}

	public String getStyleClass() {
		if (styleClass_ != null)
			return styleClass_;
		ValueBinding vb = getValueBinding("styleClass");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setTarget(String target) {
		target_ = target;
	}

	public String getTarget() {
		if (target_ != null)
			return target_;
		ValueBinding vb = getValueBinding("target");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setTitle(String title) {
		title_ = title;
	}

	public String getTitle() {
		if (title_ != null)
			return title_;
		ValueBinding vb = getValueBinding("title");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public Object saveState(FacesContext context) {
		Object values[] = new Object[22];
		values[0] = super.saveState(context);
		values[1] = accept_;
		values[2] = acceptcharset_;
		values[3] = dir_;
		values[4] = enctype_;
		values[5] = lang_;
		values[6] = onclick_;
		values[7] = ondblclick_;
		values[8] = onkeydown_;
		values[9] = onkeypress_;
		values[10] = onkeyup_;
		values[11] = onmousedown_;
		values[12] = onmousemove_;
		values[13] = onmouseout_;
		values[14] = onmouseover_;
		values[15] = onmouseup_;
		values[16] = onreset_;
		values[17] = onsubmit_;
		values[18] = style_;
		values[19] = styleClass_;
		values[20] = target_;
		values[21] = title_;
		return ((Object) (values));
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		accept_ = (String) values[1];
		acceptcharset_ = (String) values[2];
		dir_ = (String) values[3];
		enctype_ = (String) values[4];
		lang_ = (String) values[5];
		onclick_ = (String) values[6];
		ondblclick_ = (String) values[7];
		onkeydown_ = (String) values[8];
		onkeypress_ = (String) values[9];
		onkeyup_ = (String) values[10];
		onmousedown_ = (String) values[11];
		onmousemove_ = (String) values[12];
		onmouseout_ = (String) values[13];
		onmouseover_ = (String) values[14];
		onmouseup_ = (String) values[15];
		onreset_ = (String) values[16];
		onsubmit_ = (String) values[17];
		style_ = (String) values[18];
		styleClass_ = (String) values[19];
		target_ = (String) values[20];
		title_ = (String) values[21];
	}
}
