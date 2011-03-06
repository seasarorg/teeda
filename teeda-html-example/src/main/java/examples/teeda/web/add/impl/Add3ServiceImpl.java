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
package examples.teeda.web.add.impl;

import examples.teeda.web.add.Add3Page;
import examples.teeda.web.add.Add3Service;

/**
 * @author shot
 */
public class Add3ServiceImpl implements Add3Service {

	private static final int FLAVOR = 1000;

	public Add3Page doCalculate(Add3Page page) {
		Add3Page add3Page = new Add3Page();
		int arg1 = page.getArg1();
		int arg2 = page.getArg2();
		add3Page.setArg1(arg1);
		add3Page.setArg2(arg2);
		add3Page.setResult(arg1 + arg2 + FLAVOR);
		return add3Page;
	}

}
