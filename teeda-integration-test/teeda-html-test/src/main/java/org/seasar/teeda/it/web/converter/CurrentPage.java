package org.seasar.teeda.it.web.converter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.seasar.teeda.extension.annotation.convert.DateTimeConverter;
import org.seasar.teeda.extension.annotation.convert.TimestampConverter;

public class CurrentPage {

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
		try {
			this.selectDate = new SimpleDateFormat("yyyy/MM/dd")
					.parse("2007/10/19");
			this.zikan = new Timestamp(new SimpleDateFormat("HH:mm").parse(
					"15:00").getTime());
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Class prerender() {
		return null;
	}

	public Class doNextPage() {
		return NextPage.class;
	}

	public Class doCurrentPage() {
		return null;
	}

	public Class doBackPage() {
		return BackPage.class;
	}

}
