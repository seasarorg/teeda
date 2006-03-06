package examples.teeda.action.impl;

import examples.teeda.action.AddAction;
import examples.teeda.dto.AddDto;
import examples.teeda.logic.AddLogic;

public class AddActionImpl implements AddAction {

    private AddDto addDto;

    private AddLogic addLogic;

    public void setAddDto(AddDto addDto) {
        this.addDto = addDto;
    }

    public void setAddLogic(AddLogic addLogic) {
        this.addLogic = addLogic;
    }

    public String calculate() {
    	System.out.println("############################# call calculate ####################");
        int result = addLogic.calculate(addDto);
        addDto.setResult(result);
        return null;
    }
}