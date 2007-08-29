package examples.teeda.interceptor;

import javax.servlet.http.HttpSession;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.S2Container;

import examples.teeda.common.TodoConstant;
import examples.teeda.exception.LoginAuthException;

/**
 * @author yone
 * @author shot
 */
public class LoginAuthInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	private S2Container container;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object ret = null;
		Boolean loginFlg = (Boolean) getSessionAttribute(TodoConstant.SESSION_BIND_LOGIN);
		if (loginFlg == null || !loginFlg.booleanValue()) {
			throw new LoginAuthException("E00009");
		}
		Throwable cause = null;
		try {
			ret = invocation.proceed();
		} catch (final Throwable t) {
			cause = t;
		}
		if (cause != null) {
			throw cause;
		}
		return ret;
	}

	public S2Container getContainer() {
		return container;
	}

	public void setContainer(S2Container container) {
		this.container = container.getRoot();
	}

	private Object getSessionAttribute(String key) {
		HttpSession session = (HttpSession) container.getExternalContext()
				.getSession();
		return session.getAttribute(key);
	}
}