package examples.teeda.web.foreach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ForeachInputRadioPage {

	public String aaa;

	public String bbb;

	public int dtoIndex;

	public List<MyDto> dtoItems;

	public MyDto dto;

	public String aaaRadioValue;
	public String aaaRadioLabel;
	public String bbbRadioValue;
	public String bbbRadioLabel;

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

		return null;
	}

	public Class prerender() {
		return null;
	}

	public Class doSubmit() {
		System.out.println("doSubmit");
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
