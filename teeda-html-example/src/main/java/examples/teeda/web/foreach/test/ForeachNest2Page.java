package examples.teeda.web.foreach.test;

/**
 * @author yone
 */
public class ForeachNest2Page {
	private int aaaIndex;

	private String foo;

	private HogeItem[] aaaItems;
	
	private BarItem[] bbbItems;
	
	private String bar;

	public String doTest() {
		return null;
	}
	
	public int getAaaIndex() {
		return aaaIndex;
	}

	public void setAaaIndex(int aaaIndex) {
		this.aaaIndex = aaaIndex;
	}
	
	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}	
	public void setAaaItems(HogeItem[] fooItems) {
		this.aaaItems = fooItems;
	}
	
	public HogeItem[] getAaaItems() {
		if (aaaItems == null) {
			aaaItems = createHogeItems(3);
		}
		return aaaItems;
	}
	
	private HogeItem[] createHogeItems(int size) {
		HogeItem[] hoges = new HogeItem[size];
		for (int i = 0; i < size; i++) {
			hoges[i] = new HogeItem();
			hoges[i].setFoo("Foo " + i);
			hoges[i].setBbbItems(createBarItems(size, i));
		}
		return hoges;
	}
	
	private BarItem[] createBarItems(int size, int index) {
		BarItem[] bars = new BarItem[size];
		for (int i = 0; i < size; i++) {
			bars[i] = new BarItem();
			bars[i].setBar("Bar " + index + "-" + i);
		}
		return bars;
	}

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}	

	public BarItem[] getBbbItems() {
		return bbbItems;
	}

	public void setBbbItems(BarItem[] bbbItems) {
		this.bbbItems = bbbItems;
	}

}