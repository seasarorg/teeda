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
package examples.teeda.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.seasar.teeda.core.exception.AppFacesException;

public class DownloadHelper {

	private final static String CAMMA = ",";

	private final static String CR = "\n";

	private static final List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

	static {
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("aaa", "AAA1");
			map.put("bbb", new Integer(1234));
			map.put("ccc", Boolean.TRUE);
			items.add(map);
		}
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("aaa", "AAA2");
			map.put("bbb", new Integer(2345));
			map.put("ccc", Boolean.FALSE);
			items.add(map);
		}
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("aaa", "AAA3");
			map.put("bbb", new Integer(3456));
			map.put("ccc", Boolean.TRUE);
			items.add(map);
		}
	};

	public File getFile(final String filename) {
		File csv = null;
		try {
			csv = new File(filename);
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv));
			for (Iterator itr = items.iterator(); itr.hasNext();) {
				Map map = (Map) itr.next();
				bw.write(map.get("aaa") + CAMMA);
				bw.write(map.get("bbb") + CAMMA);
				bw.write(map.get("ccc") + CAMMA + CR);
			}
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			throw new AppFacesException("E0000002");
		}
		return csv;
	}
}
