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
package examples.teeda.web.foreach;

import java.util.ArrayList;
import java.util.List;

public class ForeachRowspanPage {
	public List<ProductDto> productItems;
	public int productIndex;
	public String category;
	public String name;
	public String productItemsTitle = "プロダクト一覧";

	public String getCategoryColumnTitle() {
		return category;
	}

	public boolean isCategoryColumn() {
		if (productIndex == 0) {
			return true;
		}
		return !category.equals(productItems.get(productIndex - 1).category);
	}

	public int getCategoryColumnRowspan() {
		int rowspan = 1;
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
		productItems.add(new ProductDto("プレゼンテーション", "Cubby"));
		productItems.add(new ProductDto("プレゼンテーション", "Mayaa"));
		productItems.add(new ProductDto("プレゼンテーション", "S2Flex"));
		productItems.add(new ProductDto("プレゼンテーション", "S2JSF"));
		productItems.add(new ProductDto("プレゼンテーション", "S2OpenAMF"));
		productItems.add(new ProductDto("プレゼンテーション", "S2Portlet"));
		productItems.add(new ProductDto("プレゼンテーション", "S2Struts"));
		productItems.add(new ProductDto("プレゼンテーション", "SAStruts"));
		productItems.add(new ProductDto("プレゼンテーション", "Teeda"));
		productItems.add(new ProductDto("パーシステンス", "Kuina"));
		productItems.add(new ProductDto("パーシステンス", "S2Dao"));
		productItems.add(new ProductDto("パーシステンス", "S2Hibernate"));
		productItems.add(new ProductDto("パーシステンス", "S2TopLink"));
		productItems.add(new ProductDto("コミュニケーション", "S2Axis"));
		productItems.add(new ProductDto("コミュニケーション", "S2JCA"));
		productItems.add(new ProductDto("コミュニケーション", "S2JMS"));
		productItems.add(new ProductDto("コミュニケーション", "S2Remoting"));
		productItems.add(new ProductDto("コミュニケーション", "S2RMI"));
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
