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
package examples.teeda.web.condition;

import java.util.Date;
import java.util.Random;

import javax.faces.internal.PhaseUtil;

import org.seasar.teeda.extension.annotation.validator.Required;

/**
 * @author shot
 */
public class AaaPage {

	private boolean dayReport;

	@Required
	private Date inYearMonth;

	@Required
	private Date inYearMonthDayFrom;

	private Date inYearMonthDayTo;

	/**
	 * @return
	 */
	public Class initialize() {
		dayReport = true;
		return null;
	}

	/**
	 * @return
	 */
	public Class prerender() {
		Random random = new Random();
		int nextInt = random.nextInt(10);
		System.out.println("nextInt = " + nextInt);
		if (nextInt % 2 == 0) {
			System.out.println("ふぉるす");
			dayReport = false;
		} else {
			System.out.println("とぅるーー");
			dayReport = true;
		}
		return null;
	}

	public boolean isDayReport() {
		return dayReport;
	}

	public void setDayReport(boolean dayReport) {
		this.dayReport = dayReport;
		System.out.println("dayReport = " + dayReport);
		System.out.println("phase = " + PhaseUtil.getCurrentPhase());
	}

	public Date getInYearMonth() {
		return inYearMonth;
	}

	public void setInYearMonth(Date inYearMonth) {
		this.inYearMonth = inYearMonth;
	}

	public Date getInYearMonthDayFrom() {
		return inYearMonthDayFrom;
	}

	public void setInYearMonthDayFrom(Date inYearMonthDayFrom) {
		this.inYearMonthDayFrom = inYearMonthDayFrom;
	}

	public Date getInYearMonthDayTo() {
		return inYearMonthDayTo;
	}

	public void setInYearMonthDayTo(Date inYearMonthDayTo) {
		this.inYearMonthDayTo = inYearMonthDayTo;
	}

	public String getLayout() {
		return null;
	}

	public Class doExecute() {
		return null;
	}

}
