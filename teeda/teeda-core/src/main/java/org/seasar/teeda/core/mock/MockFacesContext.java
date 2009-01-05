/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.mock;

import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public abstract class MockFacesContext extends FacesContext {

    public abstract void setExternalContext(ExternalContext context);

    public abstract void setMockExternalContext(MockExternalContext context);

    public abstract MockExternalContext getMockExternalContext();

    public abstract void setApplication(Application application);

    public abstract void removeMessages();

    public static void setFacesContext(FacesContext context) {
        setCurrentInstance(context);
    }
}
