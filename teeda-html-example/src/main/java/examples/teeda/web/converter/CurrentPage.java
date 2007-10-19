package examples.teeda.web.converter;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.seasar.teeda.extension.annotation.convert.DateTimeConverter;
import org.seasar.teeda.extension.annotation.convert.TimestampConverter;
import org.seasar.teeda.extension.annotation.scope.SubapplicationScope;

public class CurrentPage {

	@SubapplicationScope
	private String hoge;

	private Date selectDate;

	private Timestamp zikan;

	public String getHoge() {
		return hoge;
	}

	public void setHoge(String hoge) {
		this.hoge = hoge;
	}

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
		this.hoge = "HOGE";
		this.selectDate = Calendar.getInstance().getTime();
		this.zikan = new Timestamp(Calendar.getInstance().getTimeInMillis());
		return null;
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
