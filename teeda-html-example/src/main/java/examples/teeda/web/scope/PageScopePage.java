package examples.teeda.web.scope;

import org.seasar.teeda.extension.annotation.scope.PageScope;
import org.seasar.teeda.extension.annotation.scope.SubapplicationScope;

public class PageScopePage {

	@PageScope
	private String message;

	@SubapplicationScope
	private String message2;

	@PageScope
	private String message3;

	public String getMessage3() {
		return message3;
	}

	public void setMessage3(String message3) {
		this.message3 = message3;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public Class doPageScopeExecute() {
		return null;
	}

	public Class initialize() {
		message = "init1";
		message2 = "init2";
		message3 = "init3";
		return null;
	}

	public Class prerender() {
		System.out.println("page scope : " + message);
		System.out.println("subapp scope : " + message2);
		System.out.println("page scope : " + message3);
		return null;
	}

	public String getLayout() {
		return null;
	}
}
