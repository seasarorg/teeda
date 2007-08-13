package examples.teeda.web.move;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ResultMovetestPage implements Serializable {

	private static final long serialVersionUID = -705460499317370988L;

	private String statement = null;

	private List resultItems = null;

	private Integer aaa;

	private String bbb;

	private BigDecimal ccc;

	private String resultData = null;

	public String getStatement() {
		return statement;
	}

	public void setStatement(String aStatement) {
		statement = aStatement;
	}

	public List getResultItems() {
		return resultItems;
	}

	public void setResultItems(List aResultItems) {
		resultItems = aResultItems;
	}

	public Integer getAaa() {
		return aaa;
	}

	public void setAaa(Integer aAaa) {
		aaa = aAaa;
	}

	public String getBbb() {
		return bbb;
	}

	public void setBbb(String aBbb) {
		bbb = aBbb;
	}

	public BigDecimal getCcc() {
		return ccc;
	}

	public void setCcc(BigDecimal aCcc) {
		ccc = aCcc;
	}

	public String getResultData() {
		return resultData;
	}

	public void setResultData(String aResultData) {
		resultData = aResultData;
	}

}
