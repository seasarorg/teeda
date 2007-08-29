package examples.teeda.web.todo;

import java.util.Date;

import javax.servlet.http.HttpSession;

import examples.teeda.dao.TodoDao;
import examples.teeda.dto.UserDto;
import examples.teeda.web.AbstractCrudPage;

public abstract class AbstractTodoPage extends AbstractCrudPage {

	private TodoDao todoDao;

	private TodoDxo todoDxo;

	private Integer id;

	private Integer todoType;

	private String todoDetail;

	private Integer todoStatus;

	private Integer priority;

	private Date limitDate;

	private UserDto userDto;

	private String user;

	public String getUser() {
		return userDto.getUser();
	}

	public void setUser(String user) {
		this.user = user;
		if (userDto != null) {
			this.userDto.setUser(user);
		}
	}

	public AbstractTodoPage() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTodoType() {
		return this.todoType;
	}

	public void setTodoType(Integer todotype) {
		this.todoType = todotype;
	}

	public String getTodoDetail() {
		return this.todoDetail;
	}

	public void setTodoDetail(String tododetail) {
		this.todoDetail = tododetail;
	}

	public Integer getTodoStatus() {
		return this.todoStatus;
	}

	public void setTodoStatus(Integer todostatus) {
		this.todoStatus = todostatus;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getLimitDate() {
		return this.limitDate;
	}

	public void setLimitDate(Date limitdate) {
		this.limitDate = limitdate;
	}

	public TodoDao getTodoDao() {
		return this.todoDao;
	}

	public void setTodoDao(TodoDao todoDao) {
		this.todoDao = todoDao;
	}

	public TodoDxo getTodoDxo() {
		return this.todoDxo;
	}

	public void setTodoDxo(TodoDxo todoDxo) {
		this.todoDxo = todoDxo;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	private HttpSession session;

	public void setSessionScope(HttpSession session) {
		this.session = session;
	}

	public HttpSession getSessionScope() {
		return this.session;
	}

	public void setSessionAttribute(String key, Object value) {
		this.session.setAttribute(key, value);
	}

	public void removeSessionAttribute(String key) {
		this.session.removeAttribute(key);
	}
}