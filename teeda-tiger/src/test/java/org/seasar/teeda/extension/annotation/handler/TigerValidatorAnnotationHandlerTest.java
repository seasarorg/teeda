package org.seasar.teeda.extension.annotation.handler;

import javax.faces.internal.ValidatorChain;
import javax.faces.internal.ValidatorResource;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.Validator;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.annotation.validator.Length;
import org.seasar.teeda.extension.annotation.validator.Required;
import org.seasar.teeda.extension.validator.TLengthValidator;
import org.seasar.teeda.extension.validator.TRequiredValidator;

public class TigerValidatorAnnotationHandlerTest extends TeedaTestCase {

	protected void setUp() {
		register(TLengthValidator.class, "TLengthValidator");
		register(TRequiredValidator.class, "TRequiredValidator");
		register(HogeBean.class, "hogeBean");
	}

	public void testRegisterValidator_single() throws Exception {
		TigerValidatorAnnotationHandler handler = new TigerValidatorAnnotationHandler();
		handler.registerValidators("hogeBean");
		TLengthValidator validator = (TLengthValidator) ValidatorResource
				.getValidator("#{hogeBean.name}");
		assertNotNull(validator);
		assertEquals(2, validator.getMinimum());
		assertEquals(5, validator.getMaximum());
	}

	public void testRegisterValidator_multi() throws Exception {
		TigerValidatorAnnotationHandler handler = new TigerValidatorAnnotationHandler();
		handler.registerValidators("hogeBean");
		ValidatorChain chain = (ValidatorChain) ValidatorResource
				.getValidator("#{hogeBean.aaa}");
		assertNotNull(chain);
		assertEquals(2, chain.getValidatorSize());
		assertEquals(TRequiredValidator.class, chain.getValidator(0).getClass());
		LengthValidator validator = (LengthValidator) chain.getValidator(1);
		assertEquals(2, validator.getMinimum());
		assertEquals(5, validator.getMaximum());
	}

	public void testRegisterValidator_constantAnnotation() throws Exception {
		TigerValidatorAnnotationHandler handler = new TigerValidatorAnnotationHandler();
		handler.registerValidators("hogeBean");
		Validator validator = (Validator) ValidatorResource
				.getValidator("#{hogeBean.bbb}");
		assertNotNull(validator);
		assertEquals(TRequiredValidator.class, validator.getClass());
	}

	public static class HogeBean {

		@Length(minimum = 2, maximum = 5)
		private String name = null;

		@Required
		@Length(minimum = 2, maximum = 5)
		private String aaa;

		public static final String bbb_TRequiredValidator = null;

		private String bbb;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAaa() {
			return aaa;
		}

		public void setAaa(String aaa) {
			this.aaa = aaa;
		}

		/**
		 * @return Returns the bbb.
		 */
		public String getBbb() {
			return bbb;
		}

		/**
		 * @param bbb
		 *            The bbb to set.
		 */
		public void setBbb(String bbb) {
			this.bbb = bbb;
		}

	}
}