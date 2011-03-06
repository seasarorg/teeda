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
package org.seasar.teeda.extension.annotation.handler;

import java.util.Map;

import junitx.framework.ArrayAssert;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.annotation.takeover.TakeOver;
import org.seasar.teeda.extension.annotation.takeover.TakeOverType;
import org.seasar.teeda.extension.html.TakeOverDesc;
import org.seasar.teeda.extension.html.impl.TakeOverTypeDescFactory;

public class TigerTakeOverDescAnnotationHandlerTest extends TeedaTestCase {

	protected void setUp() {
		register(HogeBean.class, "hogeBean");
		register(FooPage.class, "fooPage");
	}

	public void testGetTakeOverDescs() throws Exception {
		TigerTakeOverDescAnnotationHandler handler = new TigerTakeOverDescAnnotationHandler();
		Map m = handler.getTakeOverDescs("hogeBean");
		assertEquals(2, m.size());
		TakeOverDesc takeOverDesc = (TakeOverDesc) m.get("doAaa");
		assertNotNull(takeOverDesc);
		assertEquals(TakeOverTypeDescFactory.INCLUDE, takeOverDesc
				.getTakeOverTypeDesc());
		ArrayAssert.assertEquals(new String[] { "aaa", "bbb" }, takeOverDesc
				.getProperties());
		takeOverDesc = (TakeOverDesc) m.get("doBbb");
		assertNotNull(takeOverDesc);
		assertEquals(TakeOverTypeDescFactory.NEVER, takeOverDesc
				.getTakeOverTypeDesc());
		assertEquals(0, takeOverDesc.getProperties().length);
	}

	public void testGetTakeOverDescs2() throws Exception {
		TigerTakeOverDescAnnotationHandler handler = new TigerTakeOverDescAnnotationHandler();
		Map m = handler.getTakeOverDescs("fooPage");
		assertEquals(2, m.size());
		TakeOverDesc takeOverDesc = (TakeOverDesc) m.get("doBbb");
		assertNotNull(takeOverDesc);
		assertEquals(TakeOverTypeDescFactory.INCLUDE, takeOverDesc
				.getTakeOverTypeDesc());
		ArrayAssert.assertEquals(new String[] { "aaa", "bbb" }, takeOverDesc
				.getProperties());
		takeOverDesc = (TakeOverDesc) m.get("jumpHoge3");
		assertNotNull(takeOverDesc);
		assertEquals(TakeOverTypeDescFactory.NEVER, takeOverDesc
				.getTakeOverTypeDesc());
		assertEquals(0, takeOverDesc.getProperties().length);
	}

	public static class HogeBean {

		@TakeOver(properties = "aaa, bbb")
		public String doAaa() {
			return null;
		}

		@TakeOver(type = TakeOverType.NEVER)
		public String doBbb() {
			return null;
		}
	}
	
	public static class FooPage {
		
		private String aaa;
		
		private String bbb;

        public static final String jumpHoge3_TAKE_OVER = "type=never";
        
		@TakeOver(properties = "aaa, bbb")
		public String doBbb() {
			return null;
		}
		
		public String getAaa() {
			return aaa;
		}

		public void setAaa(String aaa) {
			this.aaa = aaa;
		}


		public String getBbb() {
			return bbb;
		}


		public void setBbb(String bbb) {
			this.bbb = bbb;
		}

	}
	
	
}