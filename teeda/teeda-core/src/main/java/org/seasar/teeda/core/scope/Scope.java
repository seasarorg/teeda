package org.seasar.teeda.core.scope;

import org.seasar.teeda.core.JsfConstants;


public interface Scope {

    public Scope NONE = new Scope(){
        public String getScopeKey() {
            return JsfConstants.SCOPE_NONE;
        }
    };
    
    public Scope APPLICATION = new Scope(){
        public String getScopeKey() {
            return JsfConstants.SCOPE_APPLICATION;
        }
    };

    public Scope SESSION = new Scope(){
        public String getScopeKey() {
            return JsfConstants.SCOPE_SESSION;
        }
    };

    public Scope REQUEST = new Scope(){
        public String getScopeKey() {
            return JsfConstants.SCOPE_REQUEST;
        }
    };

    public String getScopeKey();
}
