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
package examples.teeda.web.link;

import java.util.Date;

public class LinkResultPage {

	private Integer arg1;

	private Integer arg2;

	private Integer arg3;

	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getArg3() {
		return arg3;
	}

	public void setArg3(Integer arg3) {
		this.arg3 = arg3;
	}

	public Integer getArg1() {
		return arg1;
	}

	public void setArg1(Integer arg1) {
		this.arg1 = arg1;
	}

	public Integer getArg2() {
		return arg2;
	}

	public void setArg2(Integer arg2) {
		this.arg2 = arg2;
	}

	public String initialize() {
		System.out.println(date);
		return null;
	}

	public String prerender() {
		return null;
	}

}
