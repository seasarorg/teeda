package examples.teeda.web.foreach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.seasar.dao.pager.DefaultPagerCondition;
import org.seasar.dao.pager.PagerUtil;
import org.seasar.teeda.extension.annotation.scope.PageScope;

public class ForeachInputPage {

	private static final int ROWLIMIT = 5;
	private static final int RECOARDS = 50;

	@PageScope
	public List<Foo> targetItems;

	private String aaa;
	private List<Foo> fooItems;
	public int find_count;
	public int current_page;
	public int total_page;

	public Class initialize() {
		if (targetItems == null) {
			int count = RECOARDS;
			targetItems = new ArrayList<Foo>();
			for (int i = 1; i <= count; i++) {
				Foo item = new Foo();
				item.setAaa("a" + String.valueOf(i));
				targetItems.add(item);
			}
		}
		current_page = 1;
		return null;
	}

	public Class prerender() {
		find_count = targetItems.size();
		current_page = find_count == 0 ? 0 : current_page;
		current_page = current_page <= 0 ? 1 : current_page;
		current_page = current_page > getTotal_page() ? getTotal_page()
				: current_page;

		DefaultPagerCondition condition = new DefaultPagerCondition();
		condition.setLimit(ROWLIMIT);
		condition.setOffset((current_page - 1) * ROWLIMIT);
		fooItems = PagerUtil.filter(targetItems, condition);

		System.out.println("current_page:" + current_page);
		for (int i = 0; i < fooItems.size(); i++) {
			Foo array_element = fooItems.get(i);
			System.out.println(array_element);
		}
		return null;
	}

	public String doPageFirst() {
		current_page = 1;
		return null;
	}

	public String doPagePrv() {
		current_page = current_page > 1 ? current_page - 1 : 1;
		return null;
	}

	public String doPageMove() {
		return null;
	}

	public String doPageNext() {
		current_page = current_page >= getTotal_page() - 1 ? getTotal_page()
				: current_page + 1;
		return null;
	}

	public String doPageFinal() {
		current_page = getTotal_page();
		return null;
	}

	public int getTotal_page() {
		int result = 0;
		result = find_count == 0 ? 0 : (int) (find_count / ROWLIMIT)
				+ ((find_count % ROWLIMIT) == 0 ? 0 : 1);
		return result;
	}

	public String doTest() {
		System.out.println("=== 入力値 ===");
		for (int i = 0; i < fooItems.size(); i++) {
			Foo array_element = fooItems.get(i);
			System.out.println(array_element);
		}
		return null;
	}

	public static class Foo implements Serializable {

		private static final long serialVersionUID = 1L;

		private String aaa;

		public String getAaa() {
			return aaa;
		}

		public void setAaa(String aaa) {
			this.aaa = aaa;
		}

		public String toString() {
			return "[" + aaa + "]";
		}
	}

	public String getAaa() {
		return aaa;
	}

	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

	public List<Foo> getFooItems() {
		return fooItems;
	}

	public void setFooItems(List<Foo> fooItems) {
		this.fooItems = fooItems;
	}

}
