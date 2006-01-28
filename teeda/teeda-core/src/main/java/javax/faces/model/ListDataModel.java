/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package javax.faces.model;

import java.util.List;

public class ListDataModel extends DataModel {

	private List list_ = null;
	int index_ = -1;
	
	public ListDataModel(){
		this(null);
	}
	
	public ListDataModel(List list){
		super();
		setWrappedData(list);
	}
	
	public int getRowCount() {
		return (list_ != null) ? list_.size() : -1;
	}

	public Object getRowData() {
		if(list_ == null){
			return null;
		}
		
		if(!isRowAvailable()){
			throw new IllegalArgumentException();
		}
		return list_.get(index_);
	}

	public int getRowIndex() {
		return index_;
	}

	public Object getWrappedData() {
		return list_;
	}

	public boolean isRowAvailable() {
		if(list_ == null){
			return false;
		}
		return (index_ >= 0 && index_ < list_.size());
	}
	
	public void setRowIndex(int index){
		
		if(index < -1){
			throw new IllegalArgumentException();
		}
		
		int oldIndex = index_;
		index_ = index;

		if(list_ == null){
			return;
		}
		
		DataModelListener[] listeners = getDataModelListeners();
		if((oldIndex != index_) && (listeners != null)){
			Object rowData = null;
			if(isRowAvailable()){
				rowData = getRowData();
			}
			
			DataModelEvent event = new DataModelEvent(this, index_, rowData);
			for(int i = 0; i < listeners.length; i++){
				listeners[i].rowSelected(event);
			}
		}

	}
	
	public void setWrappedData(Object data){
		list_ = (List)data;
		int index = (list_ != null) ? 0 : -1;
		setRowIndex(index);
	}
	

}
