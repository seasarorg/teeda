/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import javax.faces.internal.ValidatorResource;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.annotation.transition.ProtocolType;
import org.seasar.teeda.extension.annotation.transition.Redirect;
import org.seasar.teeda.extension.html.RedirectDesc;

/**
 * @author koichik
 * 
 */
public class TigerRedirectDescAnnotationHandlerTest extends TeedaTestCase {

	@Override
	protected void tearDown() {
		ValidatorResource.removeAll();
	}

	public void testGetRedirectDescs() throws Exception {
		TigerRedirectDescAnnotationHandler handler = new TigerRedirectDescAnnotationHandler();
		getContainer().register(HogeBean.class, "hogeBean");
		Map map = handler.getRedirectDescs("hogeBean");
		assertTrue(map.containsKey("initialize"));
		assertTrue(map.containsKey("prerender"));
		assertTrue(map.containsKey("doHoge"));
		assertTrue(map.containsKey("doHoge2"));
		assertTrue(map.containsKey("jumpHoge3"));
		assertFalse(map.containsKey("notAllowedPrefixMethod"));
		assertFalse(map.containsKey("xxx"));

		RedirectDesc rd = (RedirectDesc) map.get("initialize");
		assertEquals(RedirectDesc.HTTP, rd.getProtocol());

		rd = (RedirectDesc) map.get("prerender");
		assertEquals(RedirectDesc.HTTPS, rd.getProtocol());

		rd = (RedirectDesc) map.get("doHoge");
		assertEquals(RedirectDesc.HTTP, rd.getProtocol());

		rd = (RedirectDesc) map.get("doHoge2");
		assertEquals(RedirectDesc.HTTPS, rd.getProtocol());

		rd = (RedirectDesc) map.get("jumpHoge3");
		assertEquals(RedirectDesc.HTTP, rd.getProtocol());
	}

	public static class HogeBean {

		public static final String jumpHoge3_REDIRECT = "protocol=http";

		@Redirect(protocol = ProtocolType.HTTP)
		public String initialize() {
			return null;
		}

		@Redirect(protocol = ProtocolType.HTTPS)
		public String prerender() {
			return null;
		}

		@Redirect(protocol = ProtocolType.HTTP)
		public String doHoge() {
			return null;
		}

		@Redirect(protocol = ProtocolType.HTTPS)
		public String doHoge2() {
			return null;
		}
	}

}
