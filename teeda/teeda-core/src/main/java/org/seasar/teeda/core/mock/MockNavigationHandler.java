package org.seasar.teeda.core.mock;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;


public class MockNavigationHandler extends NavigationHandler {

    private String fromAction_;
    private String outCome_;
    public MockNavigationHandler(){
    }
    
    public void handleNavigation(FacesContext context, String fromAction, String outCome) {
        fromAction_ = fromAction;
        outCome_ = outCome;
    }

    public String getFromAction(){
        return fromAction_;
    }
    
    public String getOutCome(){
        return outCome_;
    }
}
