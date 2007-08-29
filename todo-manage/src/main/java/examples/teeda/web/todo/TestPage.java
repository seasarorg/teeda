package examples.teeda.web.todo;

import examples.teeda.dto.TestDto;
import examples.teeda.entity.Emp;

public class TestPage {
	private TestDto testDto;

	private Emp[] resultItems;

	private String id;

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}

	public TestPage() {
	}

	public void setTestDto(TestDto testDto) {
		this.testDto = testDto;
	}

	public Emp[] getResultItems() {
		return resultItems;
	}

	public void setResultItems(Emp[] resultItems) {
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
