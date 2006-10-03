package org.seasar.teeda.it.web.converter;

public class InputTextPage {

    private Integer aaa;
    private Integer bbb;
    private Integer ccc;
    private Integer ddd;

    public String initialize() {
        aaa = new Integer(123);
        bbb = new Integer(234);
        ccc = new Integer(345);
        ddd = new Integer(456);
        return null;
    }

    public String doSomething() {
        return null;
    }

    public Integer getAaa() {
        return aaa;
    }

    public void setAaa(Integer aaa) {
        this.aaa = aaa;
    }

    public Integer getBbb() {
        return bbb;
    }

    public void setBbb(Integer bbb) {
        this.bbb = bbb;
    }

    public Integer getCcc() {
        return ccc;
    }

    public void setCcc(Integer ccc) {
        this.ccc = ccc;
    }

    public Integer getDdd() {
        return ddd;
    }

    public void setDdd(Integer ddd) {
        this.ddd = ddd;
    }

}
