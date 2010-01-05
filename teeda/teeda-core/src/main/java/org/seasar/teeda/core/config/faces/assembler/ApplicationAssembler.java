/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.faces.assembler;

import java.util.Collections;
import java.util.List;

import org.seasar.teeda.core.config.faces.element.ApplicationElement;

public abstract class ApplicationAssembler extends AbstractJsfAssembler {

    private List applications_ = Collections.EMPTY_LIST;

    public ApplicationAssembler(List applications) {
        isAllSuitableJsfElement(applications, ApplicationElement.class);
        applications_ = applications;
        setupBeforeAssemble();
    }

    protected final List getApplications() {
        return applications_;
    }

}
