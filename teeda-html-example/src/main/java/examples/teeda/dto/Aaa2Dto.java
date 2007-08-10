package examples.teeda.dto;

import java.io.Serializable;

public class Aaa2Dto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String bbb;

	public Aaa2Dto() {
		System.out.println("aaa");
	}

	public String getBbb() {
		return bbb;
	}

	public void setBbb(String bbb) {
		this.bbb = bbb;
	}

}
