package examples.teeda.web.select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectOneWithNamePage {

    private String  name;

    private Integer foo;

    private String  fooLabel;

    private String  fooName;

    private List    fooItems;

    public SelectOneWithNamePage() {}

    public String initialize() {
        this.fooItems = new ArrayList();
        this.fooItems.add(createMap(new Integer(1), "fooA", "nameFooA"));
        this.fooItems.add(createMap(new Integer(2), "fooB", "nameFooB"));
        this.fooItems.add(createMap(new Integer(3), "fooC", "nameFooC"));

        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getFooItems() {
        return fooItems;
    }

    public void setFooItems(List fooItems) {
        this.fooItems = fooItems;
    }

    public List getFooRelItems() {
        return fooItems;
    }

    public void setFooRelItems(List fooRelItems) {
    // do nothing
    }

    public Integer getFoo() {
        return foo;
    }

    public void setFoo(Integer foo) {
        this.foo = foo;
    }

    public String getFooLabel() {
        return fooLabel;
    }

    public void setFooLabel(String fooLabel) {
        this.fooLabel = fooLabel;
    }

    public String getFooName() {
        return fooName;
    }

    public void setFooName(String fooName) {
        this.fooName = fooName;
    }

    private static Map createMap(Integer value, String label, String name) {
        Map map = new HashMap();
        map.put("value", value);
        map.put("label", label);
        map.put("name", name);

        return map;
    }

    public String doSubmit() {
        return null;
    }

}
