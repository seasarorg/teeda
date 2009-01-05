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
package org.seasar.teeda.core.util;

import org.seasar.teeda.core.config.webapp.element.WebappConfig;
import org.seasar.teeda.core.config.webapp.element.impl.WebappConfigImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class WebappConfigUtilTest extends TeedaTestCase {

    public void testGetWebappConfig() throws Exception {
        WebappConfig config = new WebappConfigImpl();
        getExternalContext().getApplicationMap().put(
                WebappConfig.class.getName(), config);

        WebappConfig c = WebappConfigUtil.getWebappConfig(getFacesContext());
        assertEquals(config, c);
    }
}
