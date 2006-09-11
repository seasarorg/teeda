package org.seasar.teeda.it.web.hello;

public class HelloPage {

    public HelloPage() {
        System.out.println("HelloPage.enclosing_method()");
    }

    private String aaa = "Teeda Extension Tests";

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String name) {
        this.aaa = name;
    }

}
