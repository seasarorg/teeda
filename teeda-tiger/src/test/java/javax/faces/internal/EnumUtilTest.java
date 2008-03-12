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
package javax.faces.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.faces.internal.EnumUtil.EnumHolder;

import junit.framework.TestCase;

/**
 * @author koichik
 */
@SuppressWarnings("unchecked")
public class EnumUtilTest extends TestCase {

	public enum Hoge {
		FOO, BAR, BAZ
	}

	public void testConvertEnumToName() {
		assertNull(EnumUtil.convertEnumToName(null));
		assertTrue(EnumUtil.convertEnumToName(Collections.EMPTY_MAP).isEmpty());

		Map<String, Object> src = new HashMap<String, Object>();
		src.put("s", "Foo");
		src.put("e", Hoge.FOO);

		Map<String, Object> dest = EnumUtil.convertEnumToName(src);
		assertEquals(2, dest.size());
		assertEquals("Foo", dest.get("s"));
		EnumHolder holder = (EnumHolder) dest.get("e");
		assertEquals(Hoge.class.getName(), holder.enumType);
		assertEquals("FOO", holder.name);
	}

	public void testConvertNameToEnum() {
		assertNull(EnumUtil.convertNameToEnum(null));
		assertTrue(EnumUtil.convertNameToEnum(Collections.EMPTY_MAP).isEmpty());

		Map<String, Object> src = new HashMap<String, Object>();
		src.put("s", "Foo");
		src.put("e", new EnumHolder(Hoge.class.getName(), "FOO"));

		Map<String, Object> dest = EnumUtil.convertNameToEnum(src);
		assertEquals(2, dest.size());
		assertEquals("Foo", dest.get("s"));
		assertEquals(Hoge.FOO, dest.get("e"));
	}

}
