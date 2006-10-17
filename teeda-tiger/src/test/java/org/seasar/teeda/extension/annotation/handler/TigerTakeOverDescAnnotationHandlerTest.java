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
}