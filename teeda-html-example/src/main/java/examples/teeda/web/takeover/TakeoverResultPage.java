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
package examples.teeda.web.takeover;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yone
 */
public class TakeoverResultPage {

	private static final String TAKEOVER_HTML = "takeover";

	private Integer arg1;

	private Integer arg2;

	public Object arg3 = new Object();

	public transient List arg4 = new ArrayList();

	public static final String doInclude_TAKE_OVER = "type='include', properties='arg1,arg3,arg4'";

	public static final String doExclude_TAKE_OVER = "type='exclude', properties='arg1,arg3,arg4'";

	public static final String jumpTakeover_TAKE_OVER = "type=never";

	public void initialize() {
		arg4.add(new Object());
	}

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

	public String doInclude() {
		return TAKEOVER_HTML;
	}

	public String doExclude() {
		return TAKEOVER_HTML;
	}
}