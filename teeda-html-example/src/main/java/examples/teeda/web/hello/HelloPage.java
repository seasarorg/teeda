package examples.teeda.web.hello;

import javax.faces.context.FacesContext;

public class HelloPage {

	private String name = "Seasar2";

	private FacesContext facesContext;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String initialize() {
		System.out.println("initialize");
		return null;
	}

	public String prerender() {
		System.out.println("prerender");
		System.out.println("facesContext:" + facesContext);
		return null;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}
}