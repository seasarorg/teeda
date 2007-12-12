package examples.teeda.web.foreach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ForeachDeleteListPage {

	private List<ForEachDto> aaaItems;

	private int aaaIndex;

	private String key;

	public List<ForEachDto> getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(List<ForEachDto> aaaItems) {
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

	public void initialize() {
		aaaItems = new ArrayList<ForEachDto>();
		aaaItems.add(new ForEachDto("111"));
		aaaItems.add(new ForEachDto("222"));
		aaaItems.add(new ForEachDto("333"));
		aaaItems.add(new ForEachDto("444"));
		aaaItems.add(new ForEachDto("555"));
	}

	public void doDelete() {
		System.out.println("##### CLICKED INDEX[" + aaaIndex + "] #####");
		aaaItems.remove(aaaIndex);
	}

	public static class ForEachDto implements Serializable {

		private static final long serialVersionUID = 1L;

		private String key;

		public ForEachDto() {
		}

		public ForEachDto(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
	}
}
