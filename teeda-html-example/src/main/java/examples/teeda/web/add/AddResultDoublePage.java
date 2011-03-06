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

public class AddResultDoublePage {

	private Double arg1;

	private Double arg2;

	private Double result;

	public Double getArg1() {
		return arg1;
	}

	public void setArg1(Double arg1) {
		this.arg1 = arg1;
	}

	public Double getArg2() {
		return arg2;
	}

	public void setArg2(Double arg2) {
		this.arg2 = arg2;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public String initialize() {
		result = new Double(arg1.doubleValue() + arg2.doubleValue());
		return null;
	}
}