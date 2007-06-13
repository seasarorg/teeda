package org.seasar.teeda.it.web.teeda305;

public class Teeda305Page {
	private TestDto testDto;

	private HogeDto[] resultItems;

	private String id;

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}

	public Teeda305Page() {
	}

	public void setTestDto(TestDto testDto) {
		this.testDto = testDto;
	}

	public HogeDto[] getResultItems() {
		return resultItems;
	}

	public void setResultItems(HogeDto[] resultItems) {
		this.resultItems = resultItems;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		testDto.setAaa(id);
		this.id = id;
	}
}
