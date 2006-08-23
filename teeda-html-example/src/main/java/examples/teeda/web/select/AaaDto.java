package examples.teeda.web.select;

public class AaaDto {

	private String label;

	private int value;

	private String nullLabel;
	
	public String getNullLabel() {
		return nullLabel;
	}

	public void setNullLabel(String nullLabel) {
		this.nullLabel = nullLabel;
	}

	public String getLabel() {
		return label;
	}

	public int getValue() {
		return value;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
