package examples.teeda.web.foreach;

public class AaaPage {

	public AaaDto[] aaaItems;
	public int aaaIndex;
	public AaaDto aaa;

	public boolean check;
	public String name;

	public void prerender() {

		aaaItems = new AaaDto[3];

		AaaDto dto1 = new AaaDto();
		dto1.setName("item1");
		AaaDto dto2 = new AaaDto();
		dto2.setName("item2");
		AaaDto dto3 = new AaaDto();
		dto3.setName("item3");

		aaaItems[0] = dto1;
		aaaItems[1] = dto2;
		aaaItems[2] = dto3;
	}

	public void doTest() {
		for (int i = 0; i < aaaItems.length; ++i) {
			AaaDto dto = aaaItems[i];
			System.out.println(dto.getName() + ", " + dto.isCheck());
		}
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLayout() {
		return null;
	}

}
