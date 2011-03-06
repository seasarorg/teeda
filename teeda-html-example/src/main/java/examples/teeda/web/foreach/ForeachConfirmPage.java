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

public class ForeachConfirmPage {
	private String foo;

	private String bar;

	public String initialize() {
		System.out.println("<initialize>foo:" + foo);
		System.out.println("<initialize>bar:" + bar);
		return null;
	}

	public String prerender() {
		System.out.println("<prerender>foo:" + foo);
		System.out.println("<prerender>bar:" + bar);
		return null;
	}

	public Class doForeach() {
		System.out.println("<doForEach>foo:" + foo);
		System.out.println("<doForEach>bar:" + bar);
		return ForeachPage.class;
	}

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

}
