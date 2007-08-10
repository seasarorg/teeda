package examples.teeda.web.ajax;

import java.io.Serializable;

public class ListDto implements Serializable {

	private static final long serialVersionUID = 1L;

	public int no;

	public String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

}
