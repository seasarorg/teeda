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
    
    public Object ajaxAction2(String arg1, int arg2, int arg3) {
        this.name = arg1 + String.valueOf(arg2 + arg3);
        return this;
    }
}
