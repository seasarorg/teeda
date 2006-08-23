package examples.teeda.web.select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectOne2Page {

	private List aaaItems;

	private int aaa;

	public String initialize() {
		aaaItems = new ArrayList();
		Map map1 = new HashMap();
		map1.put("label", "AAAA");
		map1.put("value", new Integer(0));
		aaaItems.add(map1);
		Map map2 = new HashMap();
		map2.put("label", "BBBB");
		map2.put("value", new Integer(1));
		aaaItems.add(map2);
		Map map3 = new HashMap();
		map3.put("label", "CCCC");
		map3.put("value", new Integer(2));
		aaaItems.add(map3);
		return null;
	}

	public List getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(List aaaItems) {
		this.aaaItems = aaaItems;
	}

	public int getAaa() {
		return aaa;
	}

	public void setAaa(int aaa) {
		this.aaa = aaa;
	}

	public String doAction() {
		return null;
	}
}
