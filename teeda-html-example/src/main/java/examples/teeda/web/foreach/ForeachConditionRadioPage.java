package examples.teeda.web.foreach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ForeachConditionRadioPage {

	public String selected;

	public String aaa;

	public String bbb;

	public int dtoIndex;

	public List<MyDto> dtoItems;

	public MyDto dto;

	public String aaaRadioValue;
	public String aaaRadioLabel;
	public String bbbRadioValue;
	public String bbbRadioLabel;

	public boolean isDisplay() {
		return dtoIndex % 2 == 0;
	}

	public Class initialize() {
		dtoItems = new ArrayList<MyDto>();
		MyDto myDto = new MyDto();
		myDto.aaaRadioValue = "1";
		myDto.aaaRadioLabel = "One";
		myDto.bbbRadioValue = "a";
		myDto.bbbRadioLabel = "A";
		dtoItems.add(myDto);

		myDto = new MyDto();
		myDto.aaaRadioValue = "2";
		myDto.aaaRadioLabel = "Two";
		myDto.bbbRadioValue = "b";
		myDto.bbbRadioLabel = "B";
		dtoItems.add(myDto);

		myDto = new MyDto();
		myDto.aaaRadioValue = "3";
		myDto.aaaRadioLabel = "Three";
		myDto.bbbRadioValue = "c";
		myDto.bbbRadioLabel = "C";
		dtoItems.add(myDto);

		myDto = new MyDto();
		myDto.aaaRadioValue = "4";
		myDto.aaaRadioLabel = "Four";
		myDto.bbbRadioValue = "d";
		myDto.bbbRadioLabel = "D";
		dtoItems.add(myDto);

		myDto = new MyDto();
		myDto.aaaRadioValue = "5";
		myDto.aaaRadioLabel = "Five";
		myDto.bbbRadioValue = "e";
		myDto.bbbRadioLabel = "E";
		dtoItems.add(myDto);

		return null;
	}

	public Class prerender() {
		return null;
	}

	public Class doSubmit() {
		selected = aaa + ":" + bbb;
		return null;
	}

	public static class MyDto implements Serializable {
		private static final long serialVersionUID = 1L;
		public String aaaRadioValue;
		public String aaaRadioLabel;
		public String bbbRadioValue;
		public String bbbRadioLabel;
	}

}
