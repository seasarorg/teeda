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
package org.seasar.teeda.core.config.faces.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.seasar.teeda.core.config.faces.FacesConfigBuilder;
import org.seasar.teeda.core.config.faces.FacesConfigurator;
import org.seasar.teeda.core.config.faces.element.FacesConfig;

/**
 * @author shot
 */
public class FacesConfigBuilderImpl implements FacesConfigBuilder {

    private List configurators_ = Collections
            .synchronizedList(new LinkedList());

    public FacesConfigBuilderImpl() {
    }

    public FacesConfig buildFacesConfigs() {
        List configs = new LinkedList();
        for (Iterator itr = configurators_.iterator(); itr.hasNext();) {
            FacesConfigurator configurator = (FacesConfigurator) itr.next();
            FacesConfig config = configurator.configure();
            if (config != null) {
                configs.add(config);
            }
        }
        return FacesConfigUtil.collectAllFacesConfig(configs);
    }

    public void addFacesConfigurator(FacesConfigurator configurator) {
        configurators_.add(configurator);
    }

}
