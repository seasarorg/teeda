package examples.teeda.web.todo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.util.FileUtil;
import org.seasar.teeda.core.exception.AppFacesException;
import org.seasar.teeda.extension.annotation.convert.DateTimeConverter;
import org.seasar.teeda.extension.annotation.takeover.TakeOver;

import examples.teeda.common.TodoConstant;
import examples.teeda.entity.Todo;
import examples.teeda.helper.SelectHelper;
import examples.teeda.web.CrudType;

public class TodoListPage extends AbstractTodoPage {

	private Todo[] todoItems;

	private int todoIndex;

	private boolean delete;

	private HttpServletResponse response;

	private FacesContext facesContext;

	private DownloadLogic downloadLogic;

	public TodoListPage() {
	}

	public String initialize() {
		return null;
	}

	public String prerender() {
		todoItems = getTodoDao().selectAll();
		return null;
	}

	public String doDelete() {
		for (int i = 0, cnt = todoItems.length; i < cnt; i++) {
			if (todoItems[i].isDelete()) {
				getTodoDao().delete(todoItems[i]);
			}
		}
		return null;
	}

	public Class doFinishLogout() {
		removeSessionAttribute(TodoConstant.SESSION_BIND_LOGIN);
		return LoginPage.class;
	}

	@TakeOver(properties = "crudType")
	public String doCreate() {
		setCrudType(CrudType.CREATE);
		return "todoEdit";
	}

	@Override
	@DateTimeConverter
	public Date getLimitDate() {
		return super.getLimitDate();
	}

	public Todo[] getTodoItems() {
		return this.todoItems;
	}

	public void setTodoItems(Todo[] items) {
		this.todoItems = items;
	}

	public int getTodoIndex() {
		return this.todoIndex;
	}

	public void setTodoIndex(int todoIndex) {
		this.todoIndex = todoIndex;
	}

	public String getRowStyle() {
		if (getTodoIndex() % 2 == 0) {
			return "background-color: #FFE2B7;";
		}
		return "background-color: #F2FFB7;";
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public String getTypeVal() {
		Integer i = super.getTodoType();
		if (i == null && todoItems != null) {
			i = todoItems[todoIndex].getTodoType();
		}
		return SelectHelper.getType(i);
	}

	public String getStateVal() {
		Integer s = super.getTodoStatus();
		if (s == null && todoItems != null) {
			s = todoItems[todoIndex].getTodoStatus();
		}
		return SelectHelper.getState(s);
	}

	public String getPriorityVal() {
		Integer p = super.getPriority();
		if (p == null && todoItems != null) {
			p = todoItems[todoIndex].getPriority();
		}
		return SelectHelper.getPriority(p);
	}

	public String getPriorityImgSrc() {
		Integer p = super.getPriority();
		if (p == null && todoItems != null) {
			p = todoItems[todoIndex].getPriority();
		}
		return SelectHelper.getPriorityImg(p);
	}

	public void setTypeVal(String a) {
	}

	public void setStateVal(String a) {
	}

	public void setPriorityVal(String a) {
	}

	/**
	 * download処理を実行します
	 */
	public void doDownload() {
		final File file = downloadLogic.getFile();
		response.setContentType("application/octet-stream");
		response.setHeader("content-disposition", "attachment; filename=\""
				+ file.getName() + "\"");
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			os.write(FileUtil.getBytes(file));
		} catch (IOException e) {
			throw new AppFacesException("E0000002");
		} finally {
			try {
				os.close();
			} catch (final IOException e) {
			}
		}
		// レスポンス出力が完了したことをJSFへ通知する
		// final FacesContext context = FacesContext.getCurrentInstance();
		// context.responseComplete();
		this.facesContext.responseComplete();
	}

	public void setDownloadLogic(final DownloadLogic downloadLogic) {
		this.downloadLogic = downloadLogic;
	}

	public void setResponse(final HttpServletResponse response) {
		this.response = response;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}
}