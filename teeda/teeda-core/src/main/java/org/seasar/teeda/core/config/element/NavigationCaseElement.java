package org.seasar.teeda.core.config.element;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public interface NavigationCaseElement extends JsfConfig {

    public static final String DEFAULT_TO_VIEW_ID = "*";
    
    public void setFromAction(String fromAction);
    
    public void setFromOutcome(String outcome);
    
    public void setToViewId(String toViewId);
    
    public void setRedirect(String dummy);

    public String getFromAction();
    
    public String getFromOutcome();
    
    public String getToViewId();
    
    public boolean isRedirect();

}
