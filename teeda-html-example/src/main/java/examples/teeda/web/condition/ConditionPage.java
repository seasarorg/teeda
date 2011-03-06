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

/**
 * @author shot
 */
public class ConditionPage {

	private int hoge;

	private String aaa = null;

	private String bbb = null;

	private boolean foo;

	public boolean isFoo() {
		return foo;
	}

	public void setFoo(boolean foo) {
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
			foo = true;
			aaa = "AAA";
		} else {
			foo = false;
			aaa = "BBB";
		}
		return null;
	}
}
