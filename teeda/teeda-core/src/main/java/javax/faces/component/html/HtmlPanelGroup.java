package javax.faces.component.html;

import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class HtmlPanelGroup extends UIPanel {

	public static final String COMPONENT_TYPE = "javax.faces.HtmlPanelGroup";

	private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Group";

	private String style_ = null;

	private String styleClass_ = null;

	public HtmlPanelGroup() {
		setRendererType(DEFAULT_RENDERER_TYPE);
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

	public Object saveState(FacesContext context) {
		Object values[] = new Object[3];
		values[0] = super.saveState(context);
		values[1] = style_;
		values[2] = styleClass_;
		return ((Object) (values));
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		style_ = (String) values[1];
		styleClass_ = (String) values[2];
	}
}
