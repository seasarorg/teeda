package examples.teeda.web.selecttest;

import java.io.Serializable;

public class SelectPage {
	public static class SelectDto implements Serializable {
		private static final long serialVersionUID = 7160741786750045907L;
		public String value;
		public String label;

		public SelectDto(String value, String label) {
			this.value = value;
			this.label = label;
		}
	}

	public SelectDto[] hogeItems;

	public String hoge;

	public Boolean hogeExist;

	public Class prerender() {
		hogeItems = new SelectDto[3];
		hogeItems[0] = new SelectDto("01", "あ");
		hogeItems[1] = new SelectDto("02", "い");
		hogeItems[2] = new SelectDto("03", "う");
		hogeExist = Boolean.TRUE;

		return null;
	}

	public Class doUpdate() {
		System.out.println("hogeの値：[" + hoge + "]");
		return null;
	}
}
