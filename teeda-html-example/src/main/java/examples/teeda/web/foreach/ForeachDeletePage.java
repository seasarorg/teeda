package examples.teeda.web.foreach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForeachDeletePage {

	private ForEachDto[] aaaItems;

	private int aaaIndex;

	private int clickIndex;

	private String key;

	public ForEachDto[] getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(ForEachDto[] aaaItems) {
		this.aaaItems = aaaItems;
	}

	public int getAaaIndex() {
		return aaaIndex;
	}

	public void setAaaIndex(int bbbIndex) {
		this.aaaIndex = bbbIndex;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getClickIndex() {
		return this.clickIndex;
	}

	public void setClickIndex(int clickIndex) {
		this.clickIndex = clickIndex;
	}

	public void initialize() {
		aaaItems = new ForEachDto[] { new ForEachDto(), new ForEachDto(),
				new ForEachDto(), new ForEachDto(), new ForEachDto() };
		aaaItems[0].setKey("111");
		aaaItems[1].setKey("222");
		aaaItems[2].setKey("333");
		aaaItems[3].setKey("444");
		aaaItems[4].setKey("555");
	}

	public String doDelete() {
		System.out.println("##### CLICKED INDEX[" + clickIndex + "] #####");
		List aaaList = new ArrayList(Arrays.asList(aaaItems));
		aaaList.remove(clickIndex - 1);
		aaaItems = (ForEachDto[]) aaaList
				.toArray(new ForEachDto[aaaList.size()]);
		return null;
	}

	public static class ForEachDto implements Serializable {

		private static final long serialVersionUID = 1L;

		private String key;

		public ForEachDto() {
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
	}
}
