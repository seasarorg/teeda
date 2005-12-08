package org.seasar.teeda.core.mock;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;

public class MockApplicationFactory2 extends ApplicationFactory {

    ApplicationFactory applicationFactory_;
    public MockApplicationFactory2(){
    }

    public MockApplicationFactory2(ApplicationFactory applicationFactory){
        applicationFactory_ = applicationFactory;
    }

    public Application getApplication() {
		return null;
	}

	public void setApplication(Application application) {
	}

}