package examples.teeda.dao;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;

import examples.teeda.entity.Todo;

/**
 * @author shot
 */
@S2Dao(bean = Todo.class)
public interface TodoDao {

	public Todo[] selectAll();

	@Arguments(value = "ID")
	public Todo selectById(Integer id);

	public int insert(Todo todo);

	public int update(Todo todo);

	public int delete(Todo todo);

	@Sql(value = "SELECT max(ID) FROM TODO")
	public int selectMaxId();
}