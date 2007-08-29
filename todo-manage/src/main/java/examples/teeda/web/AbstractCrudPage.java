package examples.teeda.web;

public abstract class AbstractCrudPage {

	private int crudType = 0;

	public AbstractCrudPage() {
	}

	public int getCrudType() {
		return this.crudType;
	}

	public void setCrudType(int type) {
		this.crudType = type;
	}

	public boolean isCreate() {
		return getCrudType() == CrudType.CREATE;
	}

	public boolean isRead() {
		return getCrudType() == CrudType.READ;
	}

	public boolean isUpdate() {
		return getCrudType() == CrudType.UPDATE;
	}

	public boolean isDelete() {
		return getCrudType() == CrudType.DELETE;
	}

}