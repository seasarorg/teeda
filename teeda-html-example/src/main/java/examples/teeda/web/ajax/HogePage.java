package examples.teeda.web.ajax;

import java.math.BigDecimal;

import org.seasar.teeda.extension.annotation.scope.PageScope;


public class HogePage {

    @PageScope
    public BigDecimal fuga;
    public String aaa;
    public String bbb;
    public String ccc;


    public Class initialize() {

        System.out.println("init:" + fuga);

        fuga = new BigDecimal("11.2");

        return null;
    }

    public Class prerender() {

        System.out.println("prerender:" + fuga);

        return null;
    }

    public Class doAction() {

        fuga = new BigDecimal("22.3");

        System.out.println("***************doAction:" + fuga);
        return null;
    }

    public String ajaxGetAAAOptions() {
        System.out.println("==================================aaaOptions called: " + ccc);
        return "[{label:'aaaHoge', value:'1'}, {label:'aaaFoo',value:'2'}]";
    }

    public String ajaxGetBBBOptions() {
        System.out.println("**************************************bbbOptions called: " + ccc);
        return "[{label:'bbbHoge', value:'1'}, {label:'bbbFoo',value:'2'}]";
    }

    public String getLayout() {
        return null;
    }

}
