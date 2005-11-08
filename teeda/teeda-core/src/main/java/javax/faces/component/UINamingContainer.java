package javax.faces.component;


public class UINamingContainer extends UIComponentBase implements
		NamingContainer {

	public static final String COMPONENT_FAMILY = "javax.faces.NamingContainer";

	public static final String COMPONENT_TYPE = "javax.faces.NamingContainer";
	
	public UINamingContainer(){
		super();
	}
	
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

}
