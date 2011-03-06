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

import org.seasar.teeda.extension.annotation.validator.Length;
import org.seasar.teeda.extension.annotation.validator.Required;

/**
 * @author shot
 * 
 */
public class Add3Page {

	@Required
	@Length(minimum = 3)
	private int arg1;

	@Required
	@Length(minimum = 3)
	private int arg2;

	private int result;

	private String bbb;

	private Integer aaa;

	public String getBbb() {
		return bbb;
	}

	public void setBbb(String bbb) {
		this.bbb = bbb;
	}

	public String initialize() {
		aaa = new Integer(100);
		bbb = "This is Page + Action + ActionSupportInterceptor example.";
		result = 1000;
		return null;
	}

	public Integer getAaa() {
		return aaa;
	}

	public void setAaa(Integer aaa) {
		this.aaa = aaa;
	}

	public int getArg1() {
		return arg1;
	}

	public void setArg1(int arg1) {
		this.arg1 = arg1;
	}

	public int getArg2() {
		return arg2;
	}

	public void setArg2(int arg2) {
		this.arg2 = arg2;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
