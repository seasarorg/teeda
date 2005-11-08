package javax.faces.mock;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.jsp.jstl.sql.Result;

public class MockResult implements Result {

	private Object[] obj_ = null;

	public MockResult(Object[] obj) {
		obj_ = obj;
	}

	public SortedMap[] getRows() {
		TreeMap[] treeMaps = new TreeMap[obj_.length];
		for (int i = 0; i < obj_.length; i++) {
			treeMaps[i] = new TreeMap(String.CASE_INSENSITIVE_ORDER);
			treeMaps[i].put(obj_[i], obj_[i]);
		}
		return (SortedMap[]) treeMaps;
	}

	public Object[][] getRowsByIndex() {
		throw new UnsupportedOperationException();
	}

	public String[] getColumnNames() {
		throw new UnsupportedOperationException();
	}

	public int getRowCount() {
		throw new UnsupportedOperationException();
	}

	public boolean isLimitedByMaxRows() {
		throw new UnsupportedOperationException();
	}

}
