package org.seasar.teeda.extension.html.factory.sub.web.foo;

import java.util.List;

import org.seasar.teeda.extension.component.TreeNode;

public class FooPage {

    private int hogeNo;

    private String hogeNama;

    private List hogeItems;

    private String ccc;

    private TreeNode tttt;

    private String abc;

    public String getAbcValue() {
        return "ABC";
    }

    public String getAaaLabelValue() {
        return "LabelValue";
    }

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    public TreeNode getTttt() {
        return tttt;
    }

    public void setTttt(TreeNode tttt) {
        this.tttt = tttt;
    }

    public String getAaa() {
        return null;
    }

    public String getAaaStyleClass() {
        return "mystyle";
    }

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc;
    }

    public String doBbb() {
        return null;
    }

    public String getHogeNama() {
        return hogeNama;
    }

    public void setHogeNama(String hogeNama) {
        this.hogeNama = hogeNama;
    }

    public int getHogeNo() {
        return hogeNo;
    }

    public void setHogeNo(int hogeNo) {
        this.hogeNo = hogeNo;
    }

    public List getHogeItems() {
        return hogeItems;
    }

    public void setHogeItems(List hogeItems) {
        this.hogeItems = hogeItems;
    }

    public String getHogeRowStyleClass() {
        return "mystyle";
    }

    public String getHogeRowStyle() {
        return "mystyle";
    }

}
