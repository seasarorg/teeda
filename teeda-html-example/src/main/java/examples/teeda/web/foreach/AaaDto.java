package examples.teeda.web.foreach;

import java.io.Serializable;

public class AaaDto implements Serializable {

	private static final long serialVersionUID = -9109750932040067893L;
	private boolean check;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

}
