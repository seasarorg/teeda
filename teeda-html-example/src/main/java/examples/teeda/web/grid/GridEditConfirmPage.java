package examples.teeda.web.grid;

import examples.teeda.web.common.PageUtil;
import examples.teeda.web.dto.FooDto;

public class GridEditConfirmPage extends BaseGridEditPage {

    public GridEditConfirmPage() {}

    public String initialize() {
        return null;
    }

    public String prerender() {
        return null;
    }
    
    public String getBbbStyleClass() {
        String cssClass = PageUtil.getStyleClass(super.bbbEditStatus);
        return cssClass;
    }

    public String doSubmit() {
        for(int i = 0; i < this.fooItems.length; i++) {
            FooDto dto = this.fooItems[i];
            System.out.println(dto);
        }
        return null;
    }

}
