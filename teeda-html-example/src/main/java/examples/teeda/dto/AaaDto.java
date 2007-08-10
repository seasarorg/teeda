package examples.teeda.dto;

import java.io.Serializable;

public class AaaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String bbb = "Teeda";

	public AaaDto() {
	}

	public String getBbb() {
		return bbb;
	}

	public void setBbb(String bbb) {
		this.bbb = bbb;
	}

}
