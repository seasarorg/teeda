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
package examples.teeda.web.dynamicproperty;

import java.util.Random;

public class DynamicpropertyPage {

	private static final String BLUE = "background-color:blue";

	private static final String YELLOW = "background-color:yellow";

	private static final String RED = "background-color:red";

	private static final Random random = new Random();

	private String aaa = "Dynamic";

	public String getAaa() {
		return aaa;
	}

	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

	public String getAaaStyle() {
		int mod = Math.abs(random.nextInt()) % 3;
		switch (mod) {
		case 0:
			return BLUE;
		case 1:
			return YELLOW;
		default:
			return RED;
		}
	}

	public String getImgSrc() {
		int mod = Math.abs(random.nextInt()) % 2;
		return mod == 0 ? "../../img/nav-collapse.gif"
				: "/teeda-html-example/img/nav-expand.gif";
	}

	public String getBbb1Href() {
		return "HOGE";
	}

}
