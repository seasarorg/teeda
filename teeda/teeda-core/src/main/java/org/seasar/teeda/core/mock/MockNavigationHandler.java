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

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

public class MockNavigationHandler extends NavigationHandler {

    private String fromAction_;

    private String outCome_;

    public MockNavigationHandler() {
    }

    public void handleNavigation(FacesContext context, String fromAction,
            String outCome) {
        fromAction_ = fromAction;
        outCome_ = outCome;
    }

    public String getFromAction() {
        return fromAction_;
    }

    public String getOutCome() {
        return outCome_;
    }
}
