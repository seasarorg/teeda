package org.seasar.teeda.it.web.foreach;

import java.util.ArrayList;
import java.util.List;

public class AbstractForeachPage {

    public String doSomething() {
        return null;
    }

    public String prerender() {
        return null;
    }

    protected List createItems() {
        List l = new ArrayList();
        {
            FooDto f = new FooDto();
            f.setFooNo(new Integer(1));
            f.setAaa("aa1");
            f.setBbb("bb1");
            l.add(f);
        }
        {
            FooDto f = new FooDto();
            f.setFooNo(new Integer(2));
            f.setAaa("aa2");
            f.setBbb("bb2");
            l.add(f);
        }
        {
            FooDto f = new FooDto();
            f.setFooNo(new Integer(3));
            f.setAaa("aa3");
            f.setBbb("bb3");
            l.add(f);
        }
        return l;
    }

    private Integer fooNo;
    private String aaa;
    private String bbb;

    public Integer getFooNo() {
        return fooNo;
    }

    public void setFooNo(Integer no) {
        this.fooNo = no;
    }

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }

    public String getBbb() {
        return bbb;
    }

    public void setBbb(String bbb) {
        this.bbb = bbb;
    }

}
