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
package org.seasar.teeda.core.webapp;

import javax.faces.FactoryFinder;

import org.seasar.framework.container.servlet.PortletExtendedS2ContainerServlet;

public class TeedaPortletExtendedServlet extends
        PortletExtendedS2ContainerServlet {

    private static final long serialVersionUID = 2290614456118342149L;

    public TeedaPortletExtendedServlet() {
        super();
    }

    public void init() {
        super.init();
        TeedaInitializer initializer = new TeedaInitializer();
        initializer.setServletContext(getServletContext());
        initializer.initializeFaces();
    }

    public void destroy() {
        super.destroy();
        FactoryFinder.releaseFactories();
    }
}
