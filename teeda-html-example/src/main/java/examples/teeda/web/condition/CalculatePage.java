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
 * 
 */
public class CalculatePage {

	private Integer arg1;

	private Integer arg2;

	private boolean calType = true;

	private Integer result;

	public Integer getArg1() {
		return arg1;
	}

	public void setArg1(Integer arg1) {
		this.arg1 = arg1;
	}

	public Integer getArg2() {
		return arg2;
	}

	public void setArg2(Integer arg2) {
		this.arg2 = arg2;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String doAdd() {
		System.out.println("doAdd");
		result = new Integer(arg1.intValue() + arg2.intValue());
		return null;
	}

	public String doMinus() {
		System.out.println("doMinus");
		result = new Integer(arg1.intValue() - arg2.intValue());
		return null;
	}

	public boolean isCalType() {
		return calType;
	}

	public void setCalType(boolean calType) {
		this.calType = calType;
	}

}
