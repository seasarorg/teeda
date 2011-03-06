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
package examples.teeda.web.scope;

import org.seasar.teeda.extension.annotation.scope.PageScope;
import org.seasar.teeda.extension.annotation.scope.SubapplicationScope;

public class PageScopePage {

	@PageScope
	private String message1;

	@SubapplicationScope
	private String message2;

	@PageScope
	private String message3;

	public String getMessage3() {
		return message3;
	}

	public void setMessage3(String message3) {
		this.message3 = message3;
	}

	public String getMessage1() {
		return message1;
	}

	public void setMessage1(String message1) {
		this.message1 = message1;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public Class doPageScopeExecute() {
		return null;
	}

	public Class doPageScopeClear() {
		message1 = null;
		message2 = null;
		message3 = null;
		return null;
	}

	public Class initialize() {
		message1 = "init1";
		message2 = "init2";
		message3 = "init3";
		return null;
	}

	public Class prerender() {
		System.out.println("page scope : " + message1);
		System.out.println("subapp scope : " + message2);
		System.out.println("page scope : " + message3);
		return null;
	}

	public String getLayout() {
		return null;
	}
}
