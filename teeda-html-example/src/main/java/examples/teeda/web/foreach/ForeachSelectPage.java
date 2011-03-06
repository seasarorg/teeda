/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package examples.teeda.web.foreach;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ForeachSelectPage {
	public static final String PAGE_SCOPE="selectionItems";

	public Selection[] selectionItems;
	public int selectionIndex;
	public Selection selection;
	private int selectionValue;
	private String selectionValueLabel;
	public Map<String, Integer> selectionValueItems;
	public String msg;

	public int getSelectionValue() {
		return selectionValue;
	}

	public void setSelectionValue(int selectionValue) {
		this.selectionValue = selectionValue;
	}

	public String getSelectionValueLabel() {
		return selectionValueLabel;
	}

	public void setSelectionValueLabel(String selectionValueLabel) {
		this.selectionValueLabel = selectionValueLabel;
	}

	public Class initialize() {
		selectionItems=new Selection[2];
		selectionItems[0]=new Selection();
		selectionItems[0].put("○", 1);
		selectionItems[0].put("×", 0);
		selectionItems[1]=new Selection();
		selectionItems[1].put("true", 1);
		selectionItems[1].put("false", 0);
		return null;
	}

	public Class prerender() {
		return null;
	}

	public Class doDump(){
		StringBuffer sb =new StringBuffer();
		for (int i = 0; i < selectionItems.length; i++) {
			sb.append(selectionItems[i].getSelectionValue()).append(":");
			sb.append(selectionItems[i].getSelectionValueLabel()).append(", ");
		}
		msg=sb.toString();
		return null;
	}

	public static class Selection implements Serializable {
		private static final long serialVersionUID = 1L;
		private int selectionValue;
		private String selectionValueLabel;
		private Map<String, Integer> selectionValueItems = new LinkedHashMap<String, Integer>();

		public int getSelectionValue() {
			return selectionValue;
		}

		public void setSelectionValue(int selectionValue) {
			this.selectionValue = selectionValue;
		}

		public Map<String, Integer> getSelectionValueItems() {
			return selectionValueItems;
		}

		public void setSelectionValueItems(
				Map<String, Integer> selectionValueItems) {
			this.selectionValueItems = selectionValueItems;
		}

		public String getSelectionValueLabel() {
			return selectionValueLabel;
		}

		public void setSelectionValueLabel(String selectionValueLabel) {
			this.selectionValueLabel = selectionValueLabel;
		}

		public Integer put(String key, Integer value) {
			return selectionValueItems.put(key, value);
		}

	}
}
