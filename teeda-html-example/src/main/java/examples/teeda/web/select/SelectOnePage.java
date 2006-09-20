package examples.teeda.web.select;

import java.util.ArrayList;
import java.util.List;

public class SelectOnePage {

	private List aaaItems;

	private int aaa;

	public String prerender() {
		aaaItems = new ArrayList();
		AaaDto dto1 = new AaaDto();
		dto1.setValue(0);
		dto1.setLabel("AAAA");
		aaaItems.add(dto1);
		AaaDto dto2 = new AaaDto();
		dto2.setValue(1);
		dto2.setLabel("BBBB");
		aaaItems.add(dto2);
		AaaDto dto3 = new AaaDto();
		dto3.setValue(2);
		dto3.setLabel("CCCC");
		aaaItems.add(dto3);
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
