package javax.faces.component.html;

import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class HtmlDataTable extends UIData {

	public static final String COMPONENT_TYPE = "javax.faces.HtmlDataTable";

	private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Table";

	private static final int DEFAULT_BORDER = Integer.MIN_VALUE;

	private String bgcolor_ = null;

	private Integer border_ = null;

	private String cellpadding_ = null;

	private String cellspacing_ = null;

	private String columnClasses_ = null;

	private String dir_ = null;

	private String footerClass_ = null;

	private String frame_ = null;

	private String headerClass_ = null;

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

	private String rowClasses_ = null;

	private String rules_ = null;

	private String style_ = null;

	private String styleClass_ = null;

	private String summary_ = null;

	private String title_ = null;

	private String width_ = null;

	public HtmlDataTable() {
		super();
		setRendererType(DEFAULT_RENDERER_TYPE);
	}

	public void setBgcolor(String bgcolor) {
		bgcolor_ = bgcolor;
	}

	public String getBgcolor() {
		if (bgcolor_ != null)
			return bgcolor_;
		ValueBinding vb = getValueBinding("bgcolor");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setBorder(int border) {
		border_ = new Integer(border);
	}

	public int getBorder() {
		if (border_ != null)
			return border_.intValue();
		ValueBinding vb = getValueBinding("border");
		Integer v = vb != null ? (Integer) vb.getValue(getFacesContext())
				: null;
		return v != null ? v.intValue() : DEFAULT_BORDER;
	}

	public void setCellpadding(String cellpadding) {
		cellpadding_ = cellpadding;
	}

	public String getCellpadding() {
		if (cellpadding_ != null)
			return cellpadding_;
		ValueBinding vb = getValueBinding("cellpadding");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setCellspacing(String cellspacing) {
		cellspacing_ = cellspacing;
	}

	public String getCellspacing() {
		if (cellspacing_ != null)
			return cellspacing_;
		ValueBinding vb = getValueBinding("cellspacing");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setColumnClasses(String columnClasses) {
		columnClasses_ = columnClasses;
	}

	public String getColumnClasses() {
		if (columnClasses_ != null)
			return columnClasses_;
		ValueBinding vb = getValueBinding("columnClasses");
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

	public void setFooterClass(String footerClass) {
		footerClass_ = footerClass;
	}

	public String getFooterClass() {
		if (footerClass_ != null)
			return footerClass_;
		ValueBinding vb = getValueBinding("footerClass");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setFrame(String frame) {
		frame_ = frame;
	}

	public String getFrame() {
		if (frame_ != null)
			return frame_;
		ValueBinding vb = getValueBinding("frame");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setHeaderClass(String headerClass) {
		headerClass_ = headerClass;
	}

	public String getHeaderClass() {
		if (headerClass_ != null)
			return headerClass_;
		ValueBinding vb = getValueBinding("headerClass");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
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

	public void setRowClasses(String rowClasses) {
		rowClasses_ = rowClasses;
	}

	public String getRowClasses() {
		if (rowClasses_ != null)
			return rowClasses_;
		ValueBinding vb = getValueBinding("rowClasses");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public void setRules(String rules) {
		rules_ = rules;
	}

	public String getRules() {
		if (rules_ != null)
			return rules_;
		ValueBinding vb = getValueBinding("rules");
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

	public void setSummary(String summary) {
		summary_ = summary;
	}

	public String getSummary() {
		if (summary_ != null)
			return summary_;
		ValueBinding vb = getValueBinding("summary");
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

	public void setWidth(String width) {
		width_ = width;
	}

	public String getWidth() {
		if (width_ != null)
			return width_;
		ValueBinding vb = getValueBinding("width");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	public Object saveState(FacesContext context) {
		Object values[] = new Object[28];
		values[0] = super.saveState(context);
		values[1] = bgcolor_;
		values[2] = border_;
		values[3] = cellpadding_;
		values[4] = cellspacing_;
		values[5] = columnClasses_;
		values[6] = dir_;
		values[7] = footerClass_;
		values[8] = frame_;
		values[9] = headerClass_;
		values[10] = lang_;
		values[11] = onclick_;
		values[12] = ondblclick_;
		values[13] = onkeydown_;
		values[14] = onkeypress_;
		values[15] = onkeyup_;
		values[16] = onmousedown_;
		values[17] = onmousemove_;
		values[18] = onmouseout_;
		values[19] = onmouseover_;
		values[20] = onmouseup_;
		values[21] = rowClasses_;
		values[22] = rules_;
		values[23] = style_;
		values[24] = styleClass_;
		values[25] = summary_;
		values[26] = title_;
		values[27] = width_;
		return ((Object) (values));
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		bgcolor_ = (String) values[1];
		border_ = (Integer) values[2];
		cellpadding_ = (String) values[3];
		cellspacing_ = (String) values[4];
		columnClasses_ = (String) values[5];
		dir_ = (String) values[6];
		footerClass_ = (String) values[7];
		frame_ = (String) values[8];
		headerClass_ = (String) values[9];
		lang_ = (String) values[10];
		onclick_ = (String) values[11];
		ondblclick_ = (String) values[12];
		onkeydown_ = (String) values[13];
		onkeypress_ = (String) values[14];
		onkeyup_ = (String) values[15];
		onmousedown_ = (String) values[16];
		onmousemove_ = (String) values[17];
		onmouseout_ = (String) values[18];
		onmouseover_ = (String) values[19];
		onmouseup_ = (String) values[20];
		rowClasses_ = (String) values[21];
		rules_ = (String) values[22];
		style_ = (String) values[23];
		styleClass_ = (String) values[24];
		summary_ = (String) values[25];
		title_ = (String) values[26];
		width_ = (String) values[27];
	}
}
