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

import org.seasar.teeda.extension.annotation.scope.SubapplicationScope;

/**
 * @author shot
 */
public class SubapplicationScopeInputPage {

	@SubapplicationScope
	private String message1 = null;

	private String message2 = null;

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

	public Class doAction() {
		return SubapplicationScopeResultPage.class;
	}

	/**
	 * @return
	 */
	public Class initialize() {
		message1 = "hogehogehoge";
		message2 = "mogemogemoge";
		return null;
	}

	/**
	 * @return
	 */
	public Class prerender() {
		return null;
	}

}
