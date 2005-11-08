package javax.faces.component.html;

import javax.faces.component.UIInput;

public class HtmlInputHidden extends UIInput {

	public static final String COMPONENT_TYPE = "javax.faces.HtmlInputHidden";

	private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Hidden";

	public HtmlInputHidden() {
		super();
		setRendererType(DEFAULT_RENDERER_TYPE);
	}
}
