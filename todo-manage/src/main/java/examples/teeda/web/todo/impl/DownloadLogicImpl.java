package examples.teeda.web.todo.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.seasar.teeda.core.exception.AppFacesException;

import examples.teeda.dao.TodoDao;
import examples.teeda.entity.Todo;
import examples.teeda.web.todo.DownloadLogic;

/**
 * この処理はあくまでサンプル用です
 * 
 * @author yone
 */
public class DownloadLogicImpl implements DownloadLogic {

	private final static String CAMMA = ",";

	private final static String CR = "\n";

	private TodoDao todoDao;

	public File getFile() {
		File csv = null;

		Todo[] todoItems = getTodoDao().selectAll();
		try {
			csv = new File("javaexpert-sample.csv");
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv));
			for (int i = 0, cnt = todoItems.length; i < cnt; i++) {
				Todo todo = todoItems[i];
				bw.write(todo.getTypeVal() + CAMMA);
				bw.write(todo.getTodoDetail() + CAMMA);
				bw.write(todo.getStateVal() + CAMMA);
				bw.write(todo.getPriorityVal() + CAMMA);
				bw.write(todo.getLimitDate() + CAMMA + CR);
			}
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			throw new AppFacesException("E0000002");
		}

		return csv;
	}

	public TodoDao getTodoDao() {
		return this.todoDao;
	}

	public void setTodoDao(TodoDao todoDao) {
		this.todoDao = todoDao;
	}

}
