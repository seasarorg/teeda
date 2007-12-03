package examples.teeda.web.foreach;

import java.io.Serializable;

public class ForeachListsPage {

	public String[] aaaItems;
	public Bbb[] bbbItems;
	public String title;
	public String data;

	public void initialize() {
		aaaItems = new String[3];
		aaaItems[0] = "xxx";
		aaaItems[1] = "yyy";
		aaaItems[2] = "zzz";

		bbbItems = new Bbb[3];
		bbbItems[0] = new Bbb("xxx", "XXX");
		bbbItems[1] = new Bbb("yyy", "YYY");
		bbbItems[2] = new Bbb("zzz", "ZZZ");
	}

	public static class Bbb implements Serializable {
		private static final long serialVersionUID = 1L;
		public String title;
		public String data;

		public Bbb(String title, String data) {
			this.title = title;
			this.data = data;
		}
	}

}
