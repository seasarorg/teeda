package examples.teeda.web.foreach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForeachNestPage {

	private int aaaIndex;

	private int aaaIndexIndex;

	private List aaaItems;

	private List aaaItemsItems;

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

	public List getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(List aaaItems) {
		this.aaaItems = aaaItems;
	}

	public List getAaaItemsItems() {
		return aaaItemsItems;
	}

	public void setAaaItemsItems(List aaaItemsItems) {
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
		aaaItemsItems = new ArrayList();
		for (int i = 0; i < 2; i++) {
			List items = new ArrayList();
			for (int j = 0; j < 2; j++) {
				Map map = new HashMap();
				map.put("foo", String.valueOf(i) + String.valueOf(j));
				items.add(map);
			}
			aaaItemsItems.add(items);
		}

		return null;
	}

}
