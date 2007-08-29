package examples.teeda.dto;

import java.io.Serializable;

import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

/**
 * @author yone
 * @author shot
 */
@Component(instance = InstanceType.SESSION)
public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String user;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
