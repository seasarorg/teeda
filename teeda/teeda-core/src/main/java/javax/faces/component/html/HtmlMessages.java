package javax.faces.component.html;

import javax.faces.component.UIMessages;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class HtmlMessages extends UIMessages {

	public static final String COMPONENT_TYPE = "javax.faces.HtmlMessages";

	private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Messages";

	private static final String DEFAULT_LAYOUT = "list";

	private static final boolean DEFAULT_TOOLTIP = false;

	private String errorClass_ = null;

	private String errorStyle_ = null;

	private String fatalClass_ = null;

	private String fatalStyle_ = null;

	private String infoClass_ = null;

	private String infoStyle_ = null;

	private String layout_ = null;

	private String style_ = null;

	private String styleClass_ = null;

	private String title_ = null;

	private Boolean tooltip_ = null;

	private String warnClass_ = null;

	private String warnStyle_ = null;

	public HtmlMessages() {
		super();
		setRendererType(DEFAULT_RENDERER_TYPE);
	}

	public void setErrorClass(String errorClass) {
		errorClass_ = errorClass;
	}

	public String getErrorClass() {
		if (errorClass_ != null)
			return errorClass_;
		ValueBinding vb = getValueBinding("errorClass");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setErrorStyle(String errorStyle) {
		errorStyle_ = errorStyle;
	}

	public String getErrorStyle() {
		if (errorStyle_ != null)
			return errorStyle_;
		ValueBinding vb = getValueBinding("errorStyle");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setFatalClass(String fatalClass) {
		fatalClass_ = fatalClass;
	}

	public String getFatalClass() {
		if (fatalClass_ != null)
			return fatalClass_;
		ValueBinding vb = getValueBinding("fatalClass");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setFatalStyle(String fatalStyle) {
		fatalStyle_ = fatalStyle;
	}

	public String getFatalStyle() {
		if (fatalStyle_ != null)
			return fatalStyle_;
		ValueBinding vb = getValueBinding("fatalStyle");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setInfoClass(String infoClass) {
		infoClass_ = infoClass;
	}

	public String getInfoClass() {
		if (infoClass_ != null)
			return infoClass_;
		ValueBinding vb = getValueBinding("infoClass");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setInfoStyle(String infoStyle) {
		infoStyle_ = infoStyle;
	}

	public String getInfoStyle() {
		if (infoStyle_ != null)
			return infoStyle_;
		ValueBinding vb = getValueBinding("infoStyle");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setLayout(String layout) {
		layout_ = layout;
	}

	public String getLayout() {
		if (layout_ != null)
			return layout_;
		ValueBinding vb = getValueBinding("layout");
		return vb != null ? (String) vb.getValue(getFacesContext())
				: DEFAULT_LAYOUT;
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

	public void setTitle(String title) {
		title_ = title;
	}

	public String getTitle() {
		if (title_ != null)
			return title_;
		ValueBinding vb = getValueBinding("title");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setTooltip(boolean tooltip) {
		tooltip_ = Boolean.valueOf(tooltip);
	}

	public boolean isTooltip() {
		if (tooltip_ != null)
			return tooltip_.booleanValue();
		ValueBinding vb = getValueBinding("tooltip");
		Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
				: null;
		return v != null ? v.booleanValue() : DEFAULT_TOOLTIP;
	}

	public void setWarnClass(String warnClass) {
		warnClass_ = warnClass;
	}

	public String getWarnClass() {
		if (warnClass_ != null)
			return warnClass_;
		ValueBinding vb = getValueBinding("warnClass");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setWarnStyle(String warnStyle) {
		warnStyle_ = warnStyle;
	}

	public String getWarnStyle() {
		if (warnStyle_ != null)
			return warnStyle_;
		ValueBinding vb = getValueBinding("warnStyle");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public Object saveState(FacesContext context) {
		Object values[] = new Object[14];
		values[0] = super.saveState(context);
		values[1] = errorClass_;
		values[2] = errorStyle_;
		values[3] = fatalClass_;
		values[4] = fatalStyle_;
		values[5] = infoClass_;
		values[6] = infoStyle_;
		values[7] = layout_;
		values[8] = style_;
		values[9] = styleClass_;
		values[10] = title_;
		values[11] = tooltip_;
		values[12] = warnClass_;
		values[13] = warnStyle_;
		return ((Object) (values));
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		errorClass_ = (String) values[1];
		errorStyle_ = (String) values[2];
		fatalClass_ = (String) values[3];
		fatalStyle_ = (String) values[4];
		infoClass_ = (String) values[5];
		infoStyle_ = (String) values[6];
		layout_ = (String) values[7];
		style_ = (String) values[8];
		styleClass_ = (String) values[9];
		title_ = (String) values[10];
		tooltip_ = (Boolean) values[11];
		warnClass_ = (String) values[12];
		warnStyle_ = (String) values[13];
	}
}
