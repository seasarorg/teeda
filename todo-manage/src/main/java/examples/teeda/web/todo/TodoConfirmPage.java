package examples.teeda.web.todo;

import java.util.Date;

import org.seasar.teeda.extension.annotation.convert.DateTimeConverter;
import org.seasar.teeda.extension.annotation.takeover.TakeOver;
import org.seasar.teeda.extension.annotation.takeover.TakeOverType;

import examples.teeda.entity.Todo;
import examples.teeda.helper.SelectHelper;
import examples.teeda.web.CrudType;

/**
 * @author yone
 */
public class TodoConfirmPage extends AbstractTodoPage {

	public TodoConfirmPage() {
	}

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}

	@TakeOver(type = TakeOverType.NEVER)
	public String doFinishUpdate() {
		switch (getCrudType()) {
		case CrudType.CREATE:
			Todo todo = getTodoDxo().convert(this);
			todo.setId(new Integer(getTodoDao().selectMaxId() + 1));
			getTodoDao().insert(todo);
			break;
		case CrudType.UPDATE:
			getTodoDao().update(getTodoDxo().convert(this));
			break;
		default:
			break;
		}
		return "todoList";
	}

	@Override
	@DateTimeConverter
	public Date getLimitDate() {
		return super.getLimitDate();
	}

	public String getTypeVal() {
		return SelectHelper.getType(super.getTodoType());
	}

	public String getStateVal() {
		return SelectHelper.getState(super.getTodoStatus());
	}

	public String getPriorityVal() {
		return SelectHelper.getPriority(super.getPriority());
	}

	public String getPriorityImgSrc() {
		return SelectHelper.getPriorityImg(super.getPriority());
	}

}