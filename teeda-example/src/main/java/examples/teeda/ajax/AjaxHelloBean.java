package examples.teeda.ajax;

public class AjaxHelloBean {

    private String name = "hoge";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Object ajaxAction() {
        return this;
    }
}
