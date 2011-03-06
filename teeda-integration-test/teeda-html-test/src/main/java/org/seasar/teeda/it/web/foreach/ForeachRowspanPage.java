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
package org.seasar.teeda.it.web.foreach;

import java.util.ArrayList;
import java.util.List;

public class ForeachRowspanPage {
	public List<ProductDto> productItems;
	public int productIndex;
	public String category;
	public String name;
	public int productItemsBorder = 2;

	public boolean isCategoryColumn() {
		if (productIndex == 0) {
			return true;
		}
		return !productItems.get(productIndex).category.equals(productItems
				.get(productIndex - 1).category);
	}

	public int getCategoryColumnRowspan() {
		int rowspan = 1;
		String category = productItems.get(productIndex).category;
		for (int i = productIndex + 1; i < productItems.size(); ++i) {
			if (!category.equals(productItems.get(i).category)) {
				break;
			}
			++rowspan;
		}
		return rowspan;
	}

	public void initialize() {
		productItems = new ArrayList();
		productItems.add(new ProductDto("A", "A1"));
		productItems.add(new ProductDto("A", "A2"));
		productItems.add(new ProductDto("A", "A3"));
		productItems.add(new ProductDto("B", "B1"));
		productItems.add(new ProductDto("C", "C1"));
		productItems.add(new ProductDto("C", "C2"));
	}

	public static class ProductDto {
		public String category;
		public String name;

		public ProductDto() {
		}

		public ProductDto(String category, String name) {
			this.category = category;
			this.name = name;
		}
	}
}
