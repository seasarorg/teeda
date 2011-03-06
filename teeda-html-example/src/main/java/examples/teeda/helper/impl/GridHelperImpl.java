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
package examples.teeda.helper.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import examples.teeda.helper.GridCondition;
import examples.teeda.helper.GridHelper;

/**
 * グリッドに関する操作を行うヘルパー。<br>
 * ページングおよびソート処理を行う。
 * 
 * @author takanori
 */
public class GridHelperImpl implements GridHelper, Serializable {

	private static final long serialVersionUID = 1L;

	public List pagingFilter(List data, GridCondition condition) {
		int offset = condition.getOffset();
		int limit = condition.getLimit();
		List pagingResult = pagingFilter(data, offset, limit);

		return pagingResult;
	}

	public List pagingFilter(List data, int offset, int limit) {
		if (offset < 0) {
			throw new IllegalArgumentException("offset : " + offset);
		}
		if (limit < 0) {
			throw new IllegalArgumentException("limit : " + limit);
		}

		List pagingResult;
		if (data == null || data.size() <= 0) {
			return new ArrayList();
		} else if (offset > data.size()) {
			return new ArrayList();
		}

		int lastIndex = offset + limit;
		if (lastIndex > data.size()) {
			lastIndex = data.size();
		}
		pagingResult = new ArrayList(data.subList(offset, lastIndex));

		return pagingResult;
	}

	public List sort(List data, GridCondition condition) {
		String sortProperty = condition.getSortProperty();
		boolean sortOrder = condition.isSortOrder();

		List sortedList = sort(data, sortProperty, sortOrder);
		return sortedList;
	}

	public List sort(List data, String sortProperty, boolean sortOrder) {
		// TODO
		return null;
	}

}
