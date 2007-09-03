package examples.teeda.web.teeda358;

import org.seasar.teeda.extension.annotation.scope.PageScope;
import org.seasar.teeda.extension.annotation.scope.SubapplicationScope;

public class TestPage {
	@PageScope
	public String pageField;

	@SubapplicationScope
	public String subAppField;

	public Class initialize() {
		pageField = "pageField";
		subAppField = "subAppField";
		return null;
	}

	public Class prerender() {
		System.out.println("prerender pageFiled=" + pageField);
		System.out.println("prerender subAppField=" + subAppField);
		return null;
	}

	public void doUpdate() {
		System.out.println("doUpdate pageFiled=" + pageField);
		System.out.println("doUpdate subAppField=" + subAppField);
	}
}