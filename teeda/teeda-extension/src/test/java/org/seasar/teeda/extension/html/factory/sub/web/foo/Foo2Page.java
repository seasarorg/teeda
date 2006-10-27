package org.seasar.teeda.extension.html.factory.sub.web.foo;

import java.util.List;

public class Foo2Page {

    private int hogeNo;

    private String hogeNama;

    private List hogeItems;

    private String ccc;

    public String getCccClass() {
        return "mystyle";
    }

    public String getAaa() {
        return null;
    }

    public String getAaaStyleClass() {
        return "mystyle";
    }

    public String getDoBbbOnclick() {
        return "BBBBBBB";
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
