package examples.teeda.dto;

import java.io.Serializable;

/**
 * @author shot
 */
public class ItemDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer value;

	private String label;

	public ItemDto() {
	}

	public ItemDto(Integer aaa, String bbb) {
		this.value = aaa;
		this.label = bbb;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer aaa) {
		this.value = aaa;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String bbb) {
		this.label = bbb;
	}

}
