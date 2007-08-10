package examples.teeda.dto;

import java.io.Serializable;

public class EmptyCntDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String emp;

	private String reserve;

	private EmptyCntDto[] emptyItems;

	private EmptyCntDto[] reserveItems;

	public String getEmp() {
		return emp;
	}

	public void setEmp(String emp) {
		this.emp = emp;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public EmptyCntDto[] getEmptyItems() {
		return emptyItems;
	}

	public void setEmptyItems(EmptyCntDto[] emptyItems) {
		this.emptyItems = emptyItems;
	}

	public EmptyCntDto[] getReserveItems() {
		return reserveItems;
	}

	public void setReserveItems(EmptyCntDto[] reserveItems) {
		this.reserveItems = reserveItems;
	}
}