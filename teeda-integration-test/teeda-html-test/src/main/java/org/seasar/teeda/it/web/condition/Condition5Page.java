package org.seasar.teeda.it.web.condition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.extension.annotation.validator.Required;

public class Condition5Page {

	public List<AaaDto> aaaItems;
	public int aaaIndex;
	public AaaDto aaa;
	@Required
	public String bbb;

	public boolean isDisp() {
		return aaaIndex % 2 == 0;
	}

	public void initialize() {
		aaaItems = new ArrayList<AaaDto>();
	}

	public void doSubmit() {
		aaaItems.add(new AaaDto(""));
	}

	public static class AaaDto implements Serializable {
		private static final long serialVersionUID = 1L;
		public String bbb;

		public AaaDto(String bbb) {
			this.bbb = bbb;
		}

	}

}
