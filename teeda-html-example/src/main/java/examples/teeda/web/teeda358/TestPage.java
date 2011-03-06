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
package examples.teeda.web.teeda358;

import org.seasar.teeda.extension.annotation.scope.PageScope;
import org.seasar.teeda.extension.annotation.scope.SubapplicationScope;

public class TestPage {
	@PageScope
	public String pageField;

	@SubapplicationScope
	public String subAppField;

	public Class initialize() {
		pageField = "pageField";
		subAppField = "subAppField";
		return null;
	}

	public Class prerender() {
		// System.out.println("prerender pageFiled=" + pageField);
		System.out.println("prerender subAppField=" + subAppField);
		return null;
	}

	public void doUpdate() {
		// System.out.println("doUpdate pageFiled=" + pageField);
		System.out.println("doUpdate subAppField=" + subAppField);
	}

	public String getLayout() {
		return null;
	}
}