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
package examples.teeda.web.validator;

import org.seasar.teeda.extension.annotation.validator.Required;

public class ValidatorPage {

	public static final String aaa_TGreaterThanConstantValidator = "targetCommand='doValidate, doValidate2'";

	public static final String bbb_TGreaterValidator = "target='doValidate,doValidate2', targetId=aaa";

	public static final String bbb_TGreaterThanConstantValidator = "targetCommand='doValidate, doValidate2'";

	private Integer aaa;

	private Integer bbb;

	@Required(target = "doHoge")
	public Integer ccc;

	public Integer getBbb() {
		return bbb;
	}

	public void setBbb(Integer bbb) {
		this.bbb = bbb;
	}

	public Integer getAaa() {
		return aaa;
	}

	public void setAaa(Integer aaa) {
		this.aaa = aaa;
	}

	public String doValidate() {
		return null;
	}

	public String doNoValidate() {
		return null;
	}

	public String doNoValidate2() {
		return null;
	}

	public void doHoge() {
	}

	public String initialize() {
		return null;
	}

	public String prerender() {
		return null;
	}

}
