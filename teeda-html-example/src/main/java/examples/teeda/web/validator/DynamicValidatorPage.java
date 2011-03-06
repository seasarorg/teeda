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

import javax.faces.internal.ValidatorChain;
import javax.faces.validator.Validator;

import org.seasar.teeda.extension.validator.TLengthValidator;
import org.seasar.teeda.extension.validator.TRequiredValidator;

public class DynamicValidatorPage {

	// これらは動的なValidatorが定義されている場合、無視される
	public static final String aaa_TRequiredValidator = null;

	public static final String aaa_TLengthValidator = "minimum = 1";

	public static final String bbb_TRequiredValidator = null;

	public static final String bbb_TLengthValidator = "minimum = 1";

	private Integer aaa;

	private Integer bbb;

	public Validator getAaaValidator() {
		return createValidator();
	}

	public Validator getBbbValidator() {
		return createValidator();
	}

	protected Validator createValidator() {
		ValidatorChain chain = new ValidatorChain();
		chain.add(new TRequiredValidator());
		TLengthValidator lengthValidator = new TLengthValidator();
		lengthValidator.setMinimum(5);
		chain.add(lengthValidator);
		return chain;
	}

	public void doExec() {
	}

	public Integer getAaa() {
		return aaa;
	}

	public Integer getBbb() {
		return bbb;
	}

	public void setAaa(Integer aaa) {
		this.aaa = aaa;
	}

	public void setBbb(Integer bbb) {
		this.bbb = bbb;
	}

}
