package examples.teeda.web.foreach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForeachRadioPage {

	private String aaa;

	private List aaaItems = new ArrayList();

	private int dtoIndex;

	private MyDto[] dtoItems;

	private MyDto dto;

	public String getAaa() {
		return aaa;
	}

	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

	public int getDtoIndex() {
		return dtoIndex;
	}

	public void setDtoIndex(int myDtoIndex) {
		this.dtoIndex = myDtoIndex;
	}

	public MyDto[] getDtoItems() {
		return dtoItems;
	}

	public void setDtoItems(MyDto[] dtoItems) {
		this.dtoItems = dtoItems;
	}

	public Class initialize() {
		dtoItems = new MyDto[2];
		MyDto myDto = new MyDto();
		myDto.setAaa("1");
		dtoItems[0] = myDto;
		myDto = new MyDto();
		myDto.setAaa("0");
		dtoItems[1] = myDto;

		Map m = new HashMap();
		m.put("label", "aaa");
		m.put("value", "0");
		aaaItems.add(m);
		m = new HashMap();
		m.put("label", "bbb");
		m.put("value", "1");
		aaaItems.add(m);
		m = new HashMap();
		m.put("label", "ccc");
		m.put("value", "2");
		aaaItems.add(m);
		return null;
	}

	public Class prerender() {
		return null;
	}

	public Class doSubmit() {
		System.out.println("doSubmit");
		return null;
	}

	/**
	 * @return the aaaItems
	 */
	public List getAaaItems() {
		return aaaItems;
	}

	/**
	 * @param aaaItems
	 *            the aaaItems to set
	 */
	public void setAaaItems(List aaaItems) {
		this.aaaItems = aaaItems;
	}
}
