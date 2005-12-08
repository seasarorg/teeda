package org.seasar.teeda.core.mock;

import javax.faces.model.DataModel;
public class MockDataModel extends DataModel{

		private String name_ = null;
		
		public MockDataModel(){
			super();
		}
		
		public MockDataModel(String name){
			super();
			name_ = name;
		}
		
		public int getRowCount() {
			return 0;
		}

		public Object getRowData() {
			return null;
		}

		public int getRowIndex() {
			return 0;
		}

		public Object getWrappedData() {
			return null;
		}

		public boolean isRowAvailable() {
			return false;
		}
		
		public String toString(){
			return name_;
		}

		public void setRowIndex(int rowIndex) {
		}

		public void setWrappedData(Object data) {
		}
	}
