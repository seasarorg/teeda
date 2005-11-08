package javax.faces.component;


/**
 * TODO test
 */
public class UIColumn extends UIComponentBase {

	public static final String COMPONENT_FAMILY = "javax.faces.Column";

	public static final String COMPONENT_TYPE = "javax.faces.Column";
	
    private static final String FOOTER_FACET_NAME = "footer";

    private static final String HEADER_FACET_NAME = "header";

	public UIColumn(){
	}
	
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
	
	public UIComponent getFooter(){
		return getFacet(FOOTER_FACET_NAME);
	}

	public void setFooter(UIComponent footer){
		ComponentUtils_.assertNotNull(footer, "footer");
		getFacets().put(FOOTER_FACET_NAME, footer);
	}
	
	public UIComponent getHeader(){
		return getFacet(HEADER_FACET_NAME);
	}

	public void setHeader(UIComponent header){
		ComponentUtils_.assertNotNull(header, "header");
		getFacets().put(HEADER_FACET_NAME, header);
	}

}
