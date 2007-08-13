package examples.teeda.web.move;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import examples.teeda.web.dto.FooDto;

public class MovetestPage implements Serializable {

	private static final long serialVersionUID = -705460499317370988L;

	private String statement = null;

	private List resultItems = null;

	private String resultData = null;

	private Integer aaa;

	private String bbb;

	private BigDecimal ccc;

	public Integer getAaa() {
		return aaa;
	}

	public String getBbb() {
		return bbb;
	}

	public BigDecimal getCcc() {
		return ccc;
	}

	public void setAaa(Integer aaa) {
		this.aaa = aaa;
	}

	public void setBbb(String bbb) {
		this.bbb = bbb;
	}

	public void setCcc(BigDecimal ccc) {
		this.ccc = ccc;
	}

	public Class doMove() {
		resultItems = new ArrayList(2);
		resultItems.add(new FooDto(new Integer(0), "hogehoge", new BigDecimal(
				125)));
		resultItems.add(new FooDto(new Integer(1), "foofoo",
				new BigDecimal(255)));
		resultData = "aaaaa";
		return ResultMovetestPage.class;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String aStatement) {
		statement = aStatement;
	}

	/**
	 * resultItems
	 * 
	 * @return resultItems
	 */
	public List getResultItems() {
		return resultItems;
	}

	/**
	 * resultItems
	 * 
	 * @param aResultItems
	 *            resultItems
	 */
	public void setResultItems(List aResultItems) {
		resultItems = aResultItems;
	}

	public String getResultData() {
		return resultData;
	}

	public void setResultData(String aResultData) {
		resultData = aResultData;
	}

}
