package examples.teeda.entity;

import java.io.Serializable;
import java.util.Date;

import org.seasar.dao.annotation.tiger.Bean;

import examples.teeda.helper.SelectHelper;

/**
 * @author yone
 * @author shot
 */
@Bean(table = "TODO")
public class Todo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer todoType;

	private String todoDetail;

	private Integer todoStatus;

	private Integer priority;

	private Date limitDate;

	private boolean delete;

	public Todo() {
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

	public void setTodoType(Integer empno) {
		this.todoType = empno;
	}

	public Integer getTodoStatus() {
		return this.todoStatus;
	}

	public void setTodoStatus(Integer mgrid) {
		this.todoStatus = mgrid;
	}

	public Date getLimitDate() {
		return this.limitDate;
	}

	public void setLimitDate(Date hiredate) {
		this.limitDate = hiredate;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer deptid) {
		this.priority = deptid;
	}

	public String getTodoDetail() {
		return todoDetail;
	}

	public void setTodoDetail(String todoDetail) {
		this.todoDetail = todoDetail;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public String getTypeVal() {
		return SelectHelper.getType(this.todoType);
	}

	public String getStateVal() {
		return SelectHelper.getState(this.todoStatus);
	}

	public String getPriorityVal() {
		return SelectHelper.getPriority(this.priority);
	}

	public void setTypeVal(String a) {
	}

	public void setStateVal(String a) {
	}

	public void setPriorityVal(String a) {
	}
}