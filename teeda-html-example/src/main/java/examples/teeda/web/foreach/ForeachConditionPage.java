package examples.teeda.web.foreach;

import java.io.Serializable;

import org.seasar.teeda.extension.annotation.validator.Required;

public class ForeachConditionPage {

	@Required
	public String required;

	public String selected;

	public ForEachDto[] aaaItems;

	public int aaaIndex;

	public String key;

	public void initialize() {
		aaaItems = new ForEachDto[] { new ForEachDto("1"), new ForEachDto("2"),
				new ForEachDto("3"), new ForEachDto("4"), new ForEachDto("5") };
	}

	public boolean isEven() {
		return (aaaIndex + 1) % 2 == 0;
	}

	public boolean isOdd() {
		return (aaaIndex + 1) % 2 != 0;
	}

	public void doEven() {
		selected = "even selected.";
	}

	public void doOdd() {
		selected = "odd selected.";
	}

	public static class ForEachDto implements Serializable {

		private static final long serialVersionUID = 1L;

		public String key;

		public ForEachDto() {
		}

		public ForEachDto(String key) {
			this.key = key;
		}
	}
}
