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
package org.seasar.teeda.extension.annotation.handler;

import java.util.Map;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.annotation.scope.RedirectScope;

public class TigerScopeAnnotationHandlerTest extends TeedaTestCase {

	protected void setUp() {
		register(HogeBean2.class, "hogeBean2");
	}

	public void testGetPropertyScopes() throws Exception {
		TigerScopeAnnotationHandler handler = new TigerScopeAnnotationHandler();
		Map m = handler.getPropertyScopes("hogeBean2");
		assertEquals(1, m.size());
		assertEquals(ExtensionConstants.REDIRECT_SCOPE, m.get("aaa"));
	}

	public static class HogeBean {

		@RedirectScope
		private String aaa;

		/**
		 * @return Returns the aaa.
		 */
		public String getAaa() {
			return aaa;
		}

		/**
		 * @param aaa
		 *            The aaa to set.
		 */
		public void setAaa(String aaa) {
			this.aaa = aaa;
		}
	}

	public static class HogeBean2 extends HogeBean {
	}
}