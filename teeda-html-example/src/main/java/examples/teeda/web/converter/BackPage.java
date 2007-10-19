package examples.teeda.web.converter;

import java.sql.Timestamp;
import java.util.Date;

import org.seasar.teeda.extension.annotation.convert.DateTimeConverter;
import org.seasar.teeda.extension.annotation.convert.TimestampConverter;

public class BackPage {

	private Date selectDate;

	private Timestamp zikan;

	@DateTimeConverter(pattern = "yyyy/MM/dd")
	public Date getSelectDate() {
		return this.selectDate;
	}

	public void setSelectDate(Date selectDate) {
		this.selectDate = selectDate;
	}

	@TimestampConverter(pattern = "HH:mm", target = "doNextPage")
	public Timestamp getZikan() {
		return this.zikan;
	}

	public void setZikan(Timestamp zikan) {
		this.zikan = zikan;
	}

	public Class initialize() {
		return null;
	}

	public Class prerender() {
		return null;
	}

	public Class doCurrentPage() {
		return CurrentPage.class;
	}
}
