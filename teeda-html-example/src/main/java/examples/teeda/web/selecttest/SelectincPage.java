package examples.teeda.web.selecttest;

public class SelectincPage {
	public String[] fugaItems;

	public String fuga;

	public int fugaIndex;

	public Class prerender() {
		fugaItems = new String[] { "A", "B", "C" };
		return null;
	}

	public String getFugaimgSrc() {
		return "../selecttestimg/image.html?text=" + fugaItems[fugaIndex];
	}
}
