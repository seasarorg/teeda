package examples.teeda.web.todo;

import examples.teeda.entity.Todo;

/**
 * @author yone
 */
public interface TodoDxo {

	public Todo convert(AbstractTodoPage src);

	public void convert(Todo src, AbstractTodoPage dest);
}