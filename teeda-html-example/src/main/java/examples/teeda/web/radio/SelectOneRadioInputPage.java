/**
 *
 */
package examples.teeda.web.radio;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shot
 */
public class SelectOneRadioInputPage {

	public static final String bbb_lengthValidator = "minimum=3";

	private int aaa;

	private List aaaItems;

	private Integer bbb;

	public String initialize() {
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

	public Integer getBbb() {
		return bbb;
	}

	public void setBbb(Integer bbb) {
		this.bbb = bbb;
	}

}
