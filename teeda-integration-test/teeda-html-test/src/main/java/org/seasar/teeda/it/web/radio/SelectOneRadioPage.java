/**
 *
 */
package org.seasar.teeda.it.web.radio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shot
 */
public class SelectOneRadioPage {

	private int aaa = 2;

	private List aaaItems;

	public String prerender() {
		aaaItems = new ArrayList();
		Map map1 = new HashMap();
		map1.put("value", new Integer(0));
		map1.put("label", "AAAA");
		aaaItems.add(map1);
		Map map2 = new HashMap();
		map2.put("value", new Integer(1));
		map2.put("label", "BBBB");
		aaaItems.add(map2);
		Map map3 = new HashMap();
		map3.put("value", new Integer(2));
		map3.put("label", "CCCC");
		aaaItems.add(map3);
		return null;
	}

	public int getAaa() {
		return aaa;
	}

	public void setAaa(int aaa) {
		this.aaa = aaa;
	}

	public List getAaaItems() {
		return aaaItems;
	}

	public void setAaaItems(List aaaItems) {
		this.aaaItems = aaaItems;
	}

	public String doAction() {
		return null;
	}
}
