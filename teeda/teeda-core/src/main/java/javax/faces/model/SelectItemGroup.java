package javax.faces.model;

/**
 * TODO TEST
 */

public class SelectItemGroup extends SelectItem {

	private SelectItem[] selectItems_ = null;
	
	public SelectItemGroup(){
		super();
	}
	
	public SelectItemGroup(String label){
		super("", label);
	}
	
	public SelectItemGroup(String label, String description, 
			boolean disabled, SelectItem[] selectItems){
		super("", label, description, disabled);
		selectItems_ = selectItems;
	}
	
	public SelectItem[] getSelectItems() {
		return selectItems_;
	}
	public void setSelectItems(SelectItem[] selectItems) {
		this.selectItems_ = selectItems;
	}
}
