/**
 *
 */
package org.seasar.teeda.extension.annotation.handler;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.internal.FacesMessageResource;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.annotation.message.MessageAggregation;

/**
 * @author shot
 */
public class TigerFacesMessageAnnotationHandlerTest extends TeedaTestCase {

	public void setUp() throws Exception {
		register(HogeBean.class, "hogeBean");
	}

	public void testRegister() throws Exception {
		TigerFacesMessageAnnotationHandler handler = new TigerFacesMessageAnnotationHandler();
		handler.registerFacesMessages("hogeBean");
		FacesMessage facesMessage = FacesMessageResource
				.getFacesMessage("#{hogeBean.aaaItems}");
		assertNotNull(facesMessage);
		assertEquals("hoge", facesMessage.getSummary());
		assertEquals("foo", facesMessage.getDetail());
	}

	public static class HogeBean {

		@MessageAggregation(summary = "hoge", detail = "foo")
		private List aaaItems;

		public List getAaaItems() {
			return aaaItems;
		}

		public void setAaaItems(List aaaItems) {
			this.aaaItems = aaaItems;
		}

	}
}
