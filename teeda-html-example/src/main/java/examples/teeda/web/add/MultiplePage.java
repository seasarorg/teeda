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
package examples.teeda.web.add;

import java.util.Map;

public class MultiplePage {

	private Integer arg1;

	private Map requestScope;

	public Map getRequestScope() {
		return requestScope;
	}

	public void setRequestScope(Map requestScope) {
		this.requestScope = requestScope;
	}

	public Integer getArg1() {
		return arg1;
	}

	public void setArg1(Integer arg1) {
		this.arg1 = arg1;
	}

	public String doCalculate() {
		arg1 = new Integer(arg1.intValue() * 2);
		System.out.println(requestScope);
		return null;
	}

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}

}
