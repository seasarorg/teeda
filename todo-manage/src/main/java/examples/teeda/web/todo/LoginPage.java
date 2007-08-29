package examples.teeda.web.todo;

import javax.faces.internal.FacesMessageUtil;

import org.seasar.teeda.extension.annotation.validator.Length;
import org.seasar.teeda.extension.annotation.validator.Required;

import examples.teeda.common.TodoConstant;

/**
 * @author yone
 */
public class LoginPage extends AbstractTodoPage {

	private static final String DUMMY_CHECK_VALUE = "teeda";

	@Required
	@Length(minimum = 3)
	private String user;

	@Required
	@Length(minimum = 5)
	private String pass;

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUser() {
		return super.getUser();
	}

	public void setUser(String user) {
		super.setUser(user);
	}

	/**
	 * このサンプルは簡易ログインです. 実際は認証チェックを行う必要があります.
	 */
	public Class doLogin() {
		if (DUMMY_CHECK_VALUE.equals(getUser())
				&& DUMMY_CHECK_VALUE.equals(pass)) {
			setSessionAttribute(TodoConstant.SESSION_BIND_LOGIN, new Boolean(
					true));
			return TodoListPage.class;
		} else {
			setSessionAttribute(TodoConstant.SESSION_BIND_LOGIN, new Boolean(
					false));
			FacesMessageUtil.addErrorMessage("E0000003");
		}
		return null;
	}
}