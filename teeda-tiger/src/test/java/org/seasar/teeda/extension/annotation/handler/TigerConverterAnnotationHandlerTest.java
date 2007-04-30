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

import java.sql.Timestamp;
import java.util.Date;

import javax.faces.internal.ConverterResource;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.annotation.convert.DateTimeConverter;
import org.seasar.teeda.extension.annotation.convert.TimestampConverter;
import org.seasar.teeda.extension.convert.TDateTimeConverter;
import org.seasar.teeda.extension.convert.TTimestampConverter;

public class TigerConverterAnnotationHandlerTest extends TeedaTestCase {

	protected void setUp() {
		ComponentDef cd = new ComponentDefImpl(TDateTimeConverter.class,
				"TDateTimeConverter");
		cd.setInstanceDef(InstanceDefFactory.PROTOTYPE);
		register(cd);
		ComponentDef cd2 = new ComponentDefImpl(
				TTimestampConverter.class,
				"TTimestampConverter");
		cd2.setInstanceDef(InstanceDefFactory.PROTOTYPE);
		register(cd2);
		register(HogeBean.class, "hogeBean");
	}

	public void testTigerAnnotation() throws Exception {
		TigerConverterAnnotationHandler handler = new TigerConverterAnnotationHandler();
		handler.registerConverters("hogeBean");
		TDateTimeConverter converter = (TDateTimeConverter) ConverterResource
				.getConverter("#{hogeBean.aaa}");
		assertNotNull(converter);
		assertEquals("time", converter.getType());

		TTimestampConverter timestampConverter = (TTimestampConverter) ConverterResource
				.getConverter("#{hogeBean.ddd}");
		assertNotNull(timestampConverter);
		assertEquals("yyyy/MM/dd HH:mm:ss.SSS", timestampConverter.getPattern());
	}

	public void testConstantAnnotation() throws Exception {
		TigerConverterAnnotationHandler handler = new TigerConverterAnnotationHandler();
		handler.registerConverters("hogeBean");
		TDateTimeConverter converter = (TDateTimeConverter) ConverterResource
				.getConverter("#{hogeBean.bbb}");
		assertNotNull(converter);
		assertEquals("date", converter.getType());
	}

	public void testGetterMethod() throws Exception {
		TigerConverterAnnotationHandler handler = new TigerConverterAnnotationHandler();
		handler.registerConverters("hogeBean");
		TDateTimeConverter converter = (TDateTimeConverter) ConverterResource
				.getConverter("#{hogeBean.ccc}");
		assertNotNull(converter);
		assertEquals("date", converter.getType());
	}

	public static class HogeBean {

		@DateTimeConverter(type = "time")
		private Date aaa;

		public static final String bbb_TDateTimeConverter = null;

		private Date bbb;

		private Date ccc;

		@TimestampConverter(pattern = "yyyy/MM/dd HH:mm:ss.SSS")
		private Timestamp ddd;

		public Timestamp getDdd() {
			return ddd;
		}

		public void setDdd(Timestamp ddd) {
			this.ddd = ddd;
		}

		/**
		 * @return Returns the aaa.
		 */
		public Date getAaa() {
			return aaa;
		}

		/**
		 * @param aaa
		 *            The aaa to set.
		 */
		public void setAaa(Date aaa) {
			this.aaa = aaa;
		}

		/**
		 * @return Returns the bbb.
		 */
		public Date getBbb() {
			return bbb;
		}

		/**
		 * @param bbb
		 *            The bbb to set.
		 */
		public void setBbb(Date bbb) {
			this.bbb = bbb;
		}

		/**
		 * @return Returns the ccc.
		 */
		@DateTimeConverter
		public Date getCcc() {
			return ccc;
		}

		/**
		 * @param ccc
		 *            The ccc to set.
		 */
		public void setCcc(Date ccc) {
			this.ccc = ccc;
		}

	}
}