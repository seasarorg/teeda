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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 検索のテスト
 */
public class ListTestPage {

	/**
	 * コード(検索条件)
	 */
	private String inCustomerCd;

	/**
	 * コード
	 */
	private String custCd;

	/**
	 * 名称
	 */
	private String customerNm;

	/**
	 * 一覧Items
	 */
	private List listItems;

	/**
	 * Getter custCd
	 * 
	 * @return custCd を戻します。
	 */
	public String getCustCd() {
		return custCd;
	}

	/**
	 * Setter custCd
	 * 
	 * @param custCd
	 *            設定する custCd。
	 */
	public void setCustCd(String custCd) {
		this.custCd = custCd;
	}

	/**
	 * Getter customerNm
	 * 
	 * @return customerNm を戻します。
	 */
	public String getCustomerNm() {
		return customerNm;
	}

	/**
	 * Setter customerNm
	 * 
	 * @param customerNm
	 *            設定する customerNm。
	 */
	public void setCustomerNm(String customerNm) {
		this.customerNm = customerNm;
	}

	/**
	 * Getter inCustomerCd
	 * 
	 * @return inCustomerCd を戻します。
	 */
	public String getInCustomerCd() {
		return inCustomerCd;
	}

	/**
	 * Setter inCustomerCd
	 * 
	 * @param inCustomerCd
	 *            設定する inCustomerCd。
	 */
	public void setInCustomerCd(String inCustomerCd) {
		this.inCustomerCd = inCustomerCd;
	}

	/**
	 * Getter listItems
	 * 
	 * @return listItems を戻します。
	 */
	public List getListItems() {
		return listItems;
	}

	/**
	 * Setter listItems
	 * 
	 * @param listItems
	 *            設定する listItems。
	 */
	public void setListItems(List listItems) {
		this.listItems = listItems;
	}

	/**
	 * initialize
	 * 
	 * @return null
	 */
	public String initialize() {
		return null;
	}

	/**
	 * prerender
	 * 
	 * @return null
	 */
	public String prerender() {
		return null;
	}

	/**
	 * 検索
	 * 
	 * @return null
	 */
	public Class doSearch() {

		Random r = new Random();
		int nextInt = r.nextInt();
		System.out.println(nextInt);
		// 検索
		listItems = new ArrayList();
		{
			Map map = new HashMap();
			map.put("custCd", "aaa" + nextInt);
			map.put("customerNm", "bbb" + nextInt);
			listItems.add(map);
		}
		{
			Map map = new HashMap();
			map.put("custCd", "ccc" + nextInt);
			map.put("customerNm", "ddd" + nextInt);
			listItems.add(map);
		}
		return ListTestPage.class;
	}

	public String getLayout() {
		return null;
	}

	private static int i = 0;
}
