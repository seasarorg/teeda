package examples.teeda.web.foreach;

import java.io.Serializable;

public class ForeachNestPage {

	private int aaaIndex;

	private int aaaIndexIndex;

	private FooDto[] aaaItems;

	private FooDto[][] aaaItemsItems;

	private String foo;

	public int getAaaIndex() {
		return aaaIndex;
	}

	public void setAaaIndex(int aaaIndex) {
		this.aaaIndex = aaaIndex;
	}

	public int getAaaIndexIndex() {
		return aaaIndexIndex;
	}

	public void setAaaIndexIndex(int aaaIndexIndex) {
		this.aaaIndexIndex = aaaIndexIndex;
	}

	public FooDto[] getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(FooDto[] aaaItems) {
		this.aaaItems = aaaItems;
	}

	public FooDto[][] getAaaItemsItems() {
		return aaaItemsItems;
	}

	public void setAaaItemsItems(FooDto[][] aaaItemsItems) {
		this.aaaItemsItems = aaaItemsItems;
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public String initialize() {
		return null;
	}

	public String prerender() {
		aaaItemsItems = new FooDto[2][2];
		for (int i = 0; i < 2; i++) {
			FooDto[] items = new FooDto[2];
			for (int j = 0; j < 2; j++) {
				FooDto fooDto = new FooDto();
				fooDto.setFoo(String.valueOf(i) + String.valueOf(j));
				items[j] = fooDto;
			}
			aaaItemsItems[i] = items;
		}

		return null;
	}

	public static final class FooDto implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String foo;

		/**
		 * @return Returns the foo.
		 */
		public String getFoo() {
			return foo;
		}

		/**
		 * @param foo
		 *            The foo to set.
		 */
		public void setFoo(String foo) {
			this.foo = foo;
		}
	}
}
