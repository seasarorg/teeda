package examples.teeda.web.foreach;

import java.util.Arrays;
import java.util.List;

public class ForeachStringListPage {

    private String aaa;

    private List aaaItems;

    public List getAaaItems() {
        if (aaaItems == null) {
            aaaItems = Arrays.asList(new String[] { "D", "C", "B", "A" });
        }
        return aaaItems;
    }

    public void setAaaItems(List aaaItems) {
        this.aaaItems = aaaItems;
    }

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }

}
