package examples.teeda.web.foreach;

import examples.teeda.web.common.PageUtil;

public class ForeachGridEditConfirmPage extends AbstractForeachGridPage {
    
    public ForeachGridEditConfirmPage() {}
    
    public String getBbbStyleClass() {
        String cssClass = PageUtil.getStyleClass(super.bbbEditStatus);
        return cssClass;
    }
    
    public String doSubmit() {
        return null;
    }

}
