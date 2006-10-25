package examples.teeda.web.grid;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import examples.teeda.web.common.PageUtil;
import examples.teeda.web.dto.FooDto;

public class GridEditPage extends BaseGridEditPage {

	private NumberFormat formater = new DecimalFormat("0.0000#");

	private String sortId;

	private String sortOrder;

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public GridEditPage() {
	}

	public String initialize() {

		if (super.fooItems == null) {
			super.fooItems = new FooDto[6];
			for (int i = 0; i < super.fooItems.length; i++) {
				Integer aaa = new Integer(i);
				String bbb = "str" + i;
				BigDecimal ccc = new BigDecimal(this.formater.format(aaa
						.intValue()));
				super.fooItems[i] = new FooDto(aaa, bbb, ccc);
			}
		}

		return null;
	}

	public String prerender() {
		return null;
	}

	public String doAdd() {
		if (super.fooItems == null) {
			super.fooItems = new FooDto[0];
		}

		int size = super.fooItems.length;
		FooDto[] newArray = new FooDto[size + 1];
		System.arraycopy(super.fooItems, 0, newArray, 0, size);

		FooDto dto = new FooDto();
		dto.setEditStatus(new Integer(PageUtil.EDIT_ADD));
		newArray[size] = dto;

		this.fooItems = newArray;

		return null;
	}

	public String doDelete() {
		if (super.fooItems == null || super.fooItems.length <= 0) {
			return null;
		}

		if ((super.fooIndexSelect == null)
				|| (super.fooIndexSelect.intValue() > super.fooItems.length)) {
			return null;
		}

		FooDto dto = this.fooItems[this.fooIndexSelect.intValue()];
		dto.setEditStatus(new Integer(PageUtil.EDIT_DELETE));

		// 実際に配列を削除する場合
		// List fooList = new ArrayList(Arrays.asList(this.fooItems));
		// fooList.remove(this.fooIndexSelect.intValue());
		// this.fooItems = (FooDto[]) fooList.toArray(new FooDto[0]);

		return null;
	}

	public String doUpdate() {
		return null;
	}

	public String doSort() {
		System.out.println("sortId=" + this.sortId + ", sortOrder="
				+ this.sortOrder);
		return null;
	}

}
