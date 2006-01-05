package org.seasar.teeda.core.mock;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;

public class MockApplicationFactory extends ApplicationFactory {

    private Application application_;
    public MockApplicationFactory(){
        application_ = new MockApplication();
    }
	public Application getApplication() {
		return application_;
	}

	public void setApplication(Application application) {
        application_ = application;
	}

}