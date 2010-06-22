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
