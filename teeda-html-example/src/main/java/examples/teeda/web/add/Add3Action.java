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

/**
 * @author shot
 *
 */
public class Add3Action {

	private Add3Page add3Page;

	private Add3Service add3Service;

	public Add3Service getAdd3Service() {
		return add3Service;
	}

	public void setAdd3Service(Add3Service add3Service) {
		this.add3Service = add3Service;
	}

	public Add3Page getAdd3Page() {
		return add3Page;
	}

	public void setAdd3Page(Add3Page add3Page) {
		this.add3Page = add3Page;
	}

	public String doCalculate() {
		add3Page = add3Service.doCalculate(this.add3Page);
		return null;
	}

}
