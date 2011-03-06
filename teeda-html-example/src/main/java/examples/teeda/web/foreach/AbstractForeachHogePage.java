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

/**
 * @author shot
 */
public abstract class AbstractForeachHogePage {

	protected FooDto[] fooItems;

	private int num1;

	private int num2;

	private String str = "str";

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public int getNum2() {
		return num2;
	}

	public void setNum2(int num2) {
		this.num2 = num2;
	}

	public int getNum1() {
		return num1;
	}

	public void setNum1(int num1) {
		this.num1 = num1;
	}

	public FooDto[] getFooItems() {
		return fooItems;
	}

	public void setFooItems(FooDto[] fooItems) {
		this.fooItems = fooItems;
	}

	private Integer fooNo;

	private String aaa;

	private String bbb;

	public Integer getFooNo() {
		return fooNo;
	}

	public void setFooNo(Integer no) {
		this.fooNo = no;
	}

	public String getAaa() {
		return aaa;
	}

	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

	public String getBbb() {
		return bbb;
	}

	public void setBbb(String bbb) {
		this.bbb = bbb;
	}

	public static class FooDto implements Serializable {

		private static final long serialVersionUID = 1L;

		private Integer fooNo;

		private String aaa;

		private String bbb;

		public Integer getFooNo() {
			return fooNo;
		}

		public void setFooNo(Integer no) {
			this.fooNo = no;
		}

		public String getAaa() {
			return aaa;
		}

		public void setAaa(String aaa) {
			this.aaa = aaa;
		}

		public String getBbb() {
			return bbb;
		}

		public void setBbb(String bbb) {
			this.bbb = bbb;
		}

	}

}
