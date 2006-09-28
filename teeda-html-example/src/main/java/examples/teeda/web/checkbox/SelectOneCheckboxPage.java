/**
 *
 */
package examples.teeda.web.checkbox;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shot
 */
public class SelectOneCheckboxPage {

	private boolean aaa;

	private Map map;

	public String prerender() {
		map = new HashMap();
		map.put(new Boolean(true), "YES");
		map.put(new Boolean(false), "NO");
		return null;
	}

	public boolean isAaa() {
		return aaa;
	}

	public void setAaa(boolean aaa) {
		this.aaa = aaa;
	}

	public String getBbb() {
		return (String) map.get(new Boolean(isAaa()));
	}

	public String doAction() {
		return null;
	}
}
