/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

package org.seasar.teeda.it.web.takeover;

import java.util.ArrayList;
import java.util.List;

/**
 * @author manhole
 */
public class Takeover1Page {

	private String aaaa = "a1";
	private String bbbb = "b1";
	private String cccc = "c1";
	public Object dddd = new Object();
	public transient List eeee = new ArrayList();

	public String getAaaa() {
		return aaaa;
	}

	public void setAaaa(String aaaa) {
		this.aaaa = aaaa;
	}

	public String getBbbb() {
		return bbbb;
	}

	public void setBbbb(String bbbb) {
		this.bbbb = bbbb;
	}

	public String getCccc() {
		return cccc;
	}

	public void setCccc(String cccc) {
		this.cccc = cccc;
	}

	public void initialize() {
		eeee.add(new Object());
	}

	public static final String goTakeover2_TAKE_OVER = "type=exclude,properties='bbbb'";

	public static final String doSomething1_TAKE_OVER = "type=exclude,properties='cccc'";

	public Class doSomething1() {
		return Takeover2Page.class;
	}

	public static final String doSomething2_TAKE_OVER = "properties='cccc,dddd,eeee'";

	public Class doSomething2() {
		return Takeover2Page.class;
	}

}
