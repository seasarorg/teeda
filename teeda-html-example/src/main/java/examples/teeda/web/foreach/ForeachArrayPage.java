package examples.teeda.web.foreach;

public class ForeachArrayPage {

    private String aaa;

    private String[] aaaItems;

    public String[] getAaaItems() {
        if (aaaItems == null) {
            aaaItems = new String[] { "a", "b", "c", "d" };
        }
        return aaaItems;
    }

    public void setAaaItems(String[] aaaItems) {
        this.aaaItems = aaaItems;
    }

    public String getAaa() {
        System.out.println("ForeachArrayPage.getAaa() " + aaa);
        return aaa;
    }

    public void setAaa(String aaa) {
        System.out.println("ForeachArrayPage.setAaa() " + aaa);
        this.aaa = aaa;
    }

}
