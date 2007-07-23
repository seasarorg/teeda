/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.it.web.condition;

/**
 * @author shot
 */
public class Condition2Page {

	private int hoge;

	private String aaa = null;

	private String bbb = null;

	private Boolean foo;

	public Boolean getFoo() {
		return foo;
	}

	public void setFoo(Boolean foo) {
		this.foo = foo;
	}

	public int getHoge() {
		return hoge;
	}

	public void setHoge(int hoge) {
		this.hoge = hoge;
	}

	public String initialize() {
		return null;
	}

	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

	public String getAaa() {
		return aaa;
	}

	public String getBbb() {
		return bbb;
	}

	public void setBbb(String bbb) {
		this.bbb = bbb;
	}

	public String doHoge() {
		if (hoge <= 100) {
			foo = Boolean.TRUE;
			aaa = "AAA";
		} else {
			foo = Boolean.FALSE;
			aaa = "BBB";
		}
		return null;
	}
}
