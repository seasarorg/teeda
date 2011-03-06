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
package examples.teeda.web.include;

public class Teeda313Page {

	private int result;

	private AddPage addPage;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Class doAction() {
		System.out.println("doAction");
		result = addPage.getArg1() + addPage.getArg2();
		addPage.setArg2(12345);
		return null;
	}

	public Class initialize() {
		System.out.println("teeda313 init");
		return null;
	}

	public Class prerender() {
		System.out.println("teeda313 prerender");
		return null;
	}

	/**
	 * @return the addPage
	 */
	public AddPage getAddPage() {
		return addPage;
	}

	/**
	 * @param addPage
	 *            the addPage to set
	 */
	public void setAddPage(AddPage addPage) {
		this.addPage = addPage;
	}

}
