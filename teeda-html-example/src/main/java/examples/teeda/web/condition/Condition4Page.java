package examples.teeda.web.condition;

public class Condition4Page {

	public Integer aaa;
	public Integer[] aaaItems;
	public int aaaIndex;
	public Integer selected;

	public boolean isDisp() {
		return aaaIndex % 2 == 0;
	}

	public void initialize() {
		aaaItems = new Integer[] { 0 };
	}

	public void doFoo() {
		selected = aaaIndex;
		aaaItems = new Integer[aaaItems.length + 1];
		for (int i = 0; i < aaaItems.length; ++i) {
			aaaItems[i] = i;
		}
	}

}
