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

import org.seasar.teeda.extension.annotation.scope.PageScope;
import org.seasar.teeda.extension.annotation.scope.RedirectScope;
import org.seasar.teeda.extension.annotation.scope.SubapplicationScope;
import org.seasar.teeda.extension.annotation.takeover.TakeOver;
import org.seasar.teeda.extension.annotation.takeover.TakeOverType;

/**
 * @author koichik
 */
public class InputPage {

	public String data;

	@PageScope
	public String page;

	@RedirectScope
	public String redirect1;

	@RedirectScope
	public String redirect2;

	@SubapplicationScope
	public String subapp1;

	@SubapplicationScope
	public String subapp2;

	public Class doDefault() {
		return ConfirmPage.class;
	}

	@TakeOver(type = TakeOverType.NEVER)
	public Class doNever() {
		return ConfirmPage.class;
	}

	@TakeOver(type = TakeOverType.INCLUDE, properties = "redirect1")
	public Class doIncludeRedirect1() {
		return ConfirmPage.class;
	}

	@TakeOver(type = TakeOverType.EXCLUDE, properties = "redirect2")
	public Class doExcludeRedirect2() {
		return ConfirmPage.class;
	}

	@TakeOver(type = TakeOverType.INCLUDE, properties = "subapp1")
	public Class doIncludeSubapp1() {
		return ConfirmPage.class;
	}

	@TakeOver(type = TakeOverType.EXCLUDE, properties = "subapp2")
	public Class doExcludeSubapp2() {
		return ConfirmPage.class;
	}

	@TakeOver(type = TakeOverType.INCLUDE, properties = "subapp1")
	public Class doIncludeSubapp1NoProperty() {
		return NoPropertyPage.class;
	}

	@TakeOver(type = TakeOverType.EXCLUDE, properties = "subapp2")
	public Class doExcludeSubapp2NoProperty() {
		return NoPropertyPage.class;
	}

	public Class doSelf() {
		return null;
	}

	public Class doSelfRedirect() {
		return InputPage.class;
	}

	public Class doFinish() {
		return ConfirmPage.class;
	}

}
