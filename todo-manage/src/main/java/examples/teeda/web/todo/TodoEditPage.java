package examples.teeda.web.todo;

import java.util.List;

import org.seasar.teeda.core.exception.AppFacesException;
import org.seasar.teeda.extension.annotation.validator.Length;
import org.seasar.teeda.extension.annotation.validator.Required;

import examples.teeda.entity.Todo;
import examples.teeda.helper.SelectHelper;
import examples.teeda.web.CrudType;

/**
 * @author yone
 */
public class TodoEditPage extends AbstractTodoPage {

	public List getTodoTypeItems() {
		return SelectHelper.getTypeItems();
	}

	public List getTodoStatusItems() {
		return SelectHelper.getStateItems();
	}

	public List getPriorityItems() {
		return SelectHelper.getPriorityItems();
	}

	public TodoEditPage() {
	}

	public String getFooStyle() {
		if (super.isRead()) {
			return "display:none;";
		}
		return null;
	}

	public String initialize() {
		if (getCrudType() == CrudType.UPDATE) {
			Todo data = getTodoDao().selectById(getId());
			if (data == null) {
				throw new AppFacesException("E0000001");
			}
			getTodoDxo().convert(data, this);
		}
		return null;
	}

	public String prerender() {
		return null;
	}

	@Override
	@Required
	public void setTodoType(Integer todotype) {
		super.setTodoType(todotype);
	}

	@Override
	@Required
	@Length(maximum = 128)
	public void setTodoDetail(String tododetail) {
		super.setTodoDetail(tododetail);
	}

	@Override
	@Required
	public void setTodoStatus(Integer todostatus) {
		super.setTodoStatus(todostatus);
	}

	@Override
	@Required
	public void setPriority(Integer priority) {
		super.setPriority(priority);
	}

}