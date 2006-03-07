package examples.teeda.logic.impl;

import examples.teeda.dto.AddDto;
import examples.teeda.logic.AddLogic;

public class AddLogicImpl implements AddLogic {

	public AddLogicImpl() {
	}

	public int calculate(AddDto addDto) {
		return addDto.getArg1() + addDto.getArg2();
	}

}
