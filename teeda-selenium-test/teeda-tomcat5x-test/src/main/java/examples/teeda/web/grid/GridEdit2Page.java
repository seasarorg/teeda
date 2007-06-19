package examples.teeda.web.grid;

import java.io.Serializable;
import java.util.Map;

public class GridEdit2Page {

	private boolean aaa;

	private int bbb;

	private int fooIndex;

	private FooDto[] fooItems;

	private Map param;

	public boolean isAaa() {
		return aaa;
	}

	public void setAaa(boolean aaa) {
		this.aaa = aaa;
	}

	public int getFooIndex() {
		return fooIndex;
	}

	public void setFooIndex(int fooIndex) {
		this.fooIndex = fooIndex;
	}

	public FooDto[] getFooItems() {
		return fooItems;
	}

	public void setFooItems(FooDto[] fooItems) {
		this.fooItems = fooItems;
	}

	public String initialize() {
		fooItems = new FooDto[2];
		FooDto dto = new FooDto();
		dto.setAaa(true);
		fooItems[0] = dto;
		dto = new FooDto();
		dto.setAaa(false);
		fooItems[1] = dto;
		return null;
	}

	public String prerender() {
		System.out.println(param);
		return null;
	}

	public static class FooDto implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private boolean aaa;

		private int bbb;

		/**
		 * @return the aaa
		 */
		public boolean isAaa() {
			return aaa;
		}

		/**
		 * @param aaa
		 *            the aaa to set
		 */
		public void setAaa(boolean aaa) {
			this.aaa = aaa;
		}

		/**
		 * @return the bbb
		 */
		public int getBbb() {
			return bbb;
		}

		/**
		 * @param bbb
		 *            the bbb to set
		 */
		public void setBbb(int bbb) {
			this.bbb = bbb;
		}
	}

	/**
	 * @return the param
	 */
	public Map getParam() {
		return param;
	}

	/**
	 * @param param
	 *            the param to set
	 */
	public void setParam(Map param) {
		this.param = param;
	}

	/**
	 * @return the bbb
	 */
	public int getBbb() {
		return bbb;
	}

	/**
	 * @param bbb
	 *            the bbb to set
	 */
	public void setBbb(int bbb) {
		this.bbb = bbb;
	}
}
