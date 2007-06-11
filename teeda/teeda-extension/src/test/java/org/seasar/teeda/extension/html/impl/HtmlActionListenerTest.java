/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.html.impl;

import javax.faces.application.NavigationHandler;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockMethodBinding;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.html.HtmlComponentInvoker;

/**
 * @author shot
 */
public class HtmlActionListenerTest extends TeedaTestCase {

    public void testProcesssAction_noMethodBinding() throws Exception {
        NavigationHandler orgHandler = getApplication().getNavigationHandler();
        try {

            getApplication().setNavigationHandler(new NavigationHandler() {

                public void handleNavigation(FacesContext context,
                        String fromAction, String outcome) {
                    assertTrue(context != null);
                    success();
                }

            });
            MockFacesContext context = getFacesContext();
            HtmlActionListener listener = new HtmlActionListener();
            listener.processAction(new ActionEvent(new MockUIComponent()) {

                public UIComponent getComponent() {
                    return new UICommand() {

                        public MethodBinding getAction() {
                            return null;
                        }

                    };
                }

            });
            assertTrue(context.getRenderResponse());
        } finally {
            getApplication().setNavigationHandler(orgHandler);
        }
    }

    public void testProcesssAction_invokerInvoke() throws Exception {
        NavigationHandler orgHandler = getApplication().getNavigationHandler();
        try {

            getApplication().setNavigationHandler(new NavigationHandler() {

                public void handleNavigation(FacesContext context,
                        String fromAction, String outcome) {
                    assertTrue(context != null);
                    success();
                }

            });
            final MockFacesContext ctx = getFacesContext();
            HtmlActionListener listener = new HtmlActionListener();
            listener.setHtmlComponentInvoker(new HtmlComponentInvoker() {

                public String getComponentName(String path, String methodName) {
                    return null;
                }

                public String invoke(FacesContext context,
                        String componentName, String methodName) {
                    assertTrue(ctx == context);
                    success();
                    return null;
                }

                public String invokeInitialize(FacesContext context,
                        String componentName) {
                    return null;
                }

                public String invokePrerender(FacesContext context,
                        String componentName) {
                    return null;
                }

                public boolean isInitialized(FacesContext context) {
                    return false;
                }

            });
            listener.processAction(new ActionEvent(new MockUIComponent()) {

                public UIComponent getComponent() {
                    return new UICommand() {

                        public MethodBinding getAction() {
                            return new MockMethodBinding("#{aaa.bbb}");
                        }

                    };
                }

            });
        } finally {
            getApplication().setNavigationHandler(orgHandler);
        }
    }

}
