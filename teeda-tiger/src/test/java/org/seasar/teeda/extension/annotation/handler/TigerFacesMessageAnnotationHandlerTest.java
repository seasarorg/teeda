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
		register(FooBean.class, "fooBean");
		register(BarBean.class, "barBean");
	}

	protected void tearDown() throws Exception {
		FacesMessageResource.removeAll();
	}

	public void testRegister_withoutId() throws Exception {
		TigerFacesMessageAnnotationHandler handler = new TigerFacesMessageAnnotationHandler();
		handler.registerFacesMessages("hogeBean");
		FacesMessage facesMessage = FacesMessageResource
				.getFacesMessage("#{hogeBean.aaaItems}");
		assertNotNull(facesMessage);
		assertEquals("hoge", facesMessage.getSummary());
		assertEquals("foo", facesMessage.getDetail());
	}

	public void testRegister_withId() throws Exception {
		getApplication()
				.setMessageBundle(
						"org.seasar.teeda.extension.annotation.handler.TestMessagesTiger");
		TigerFacesMessageAnnotationHandler handler = new TigerFacesMessageAnnotationHandler();
		handler.registerFacesMessages("barBean");
		FacesMessage facesMessage = FacesMessageResource
				.getFacesMessage("#{barBean.aaaItems}");
		assertNotNull(facesMessage);
		assertEquals("HOGE", facesMessage.getSummary());
	}

	public void testRegister_noAnnotation() throws Exception {
		TigerFacesMessageAnnotationHandler handler = new TigerFacesMessageAnnotationHandler();
		handler.registerFacesMessages("fooBean");
		FacesMessage facesMessage = FacesMessageResource
				.getFacesMessage("#{fooBean.aaaItems}");
		assertNull(facesMessage);
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

	public static class FooBean {

		private List aaaItems;

		public List getAaaItems() {
			return aaaItems;
		}

		public void setAaaItems(List aaaItems) {
			this.aaaItems = aaaItems;
		}
	}

	public static class BarBean {

		@MessageAggregation(id = "hoge")
		private List aaaItems;

		public List getAaaItems() {
			return aaaItems;
		}

		public void setAaaItems(List aaaItems) {
			this.aaaItems = aaaItems;
		}

	}

}
