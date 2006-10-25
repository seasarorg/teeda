package examples.teeda.web.dto;

import java.math.BigDecimal;

public class FooDto {

	private Integer aaa;

	private String bbb;

	private BigDecimal ccc;

	private Integer editStatus;

	private Integer bbbEditStatus;

	public FooDto() {
	}

	public FooDto(Integer aaa, String bbb, BigDecimal ccc) {
		this.aaa = aaa;
		this.bbb = bbb;
		this.ccc = ccc;
	}

	public Integer getAaa() {
		return aaa;
	}

	public void setAaa(Integer aaa) {
		this.aaa = aaa;
	}

	public String getBbb() {
		return bbb;
	}

	public void setBbb(String bbb) {
		this.bbb = bbb;
	}

	public BigDecimal getCcc() {
		return ccc;
	}

	public void setCcc(BigDecimal ccc) {
		this.ccc = ccc;
	}

	public Integer getEditStatus() {
		return editStatus;
	}

	public void setEditStatus(Integer editStatus) {
		this.editStatus = editStatus;
		System.out.println("[editStatus] " + editStatus.intValue());
	}

	public Integer getBbbEditStatus() {
		return bbbEditStatus;
	}

	public void setBbbEditStatus(Integer bbbEditStatus) {
		this.bbbEditStatus = bbbEditStatus;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("aaa=" + this.aaa);
		buff.append(",bbb=" + this.bbb);
		buff.append(",ccc=" + this.ccc);
		buff.append(",editStatus=" + this.editStatus.intValue());
		buff.append(",bbbEditStatus=" + this.bbbEditStatus.intValue());

		return buff.toString();
	}

}
