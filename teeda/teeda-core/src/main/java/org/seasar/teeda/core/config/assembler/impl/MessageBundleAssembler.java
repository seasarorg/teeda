/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.assembler.impl;

import javax.faces.application.Application;

import org.seasar.teeda.core.config.assembler.ApplicationChildAssembler;

/**
 * @author shot
 */
public class MessageBundleAssembler extends ApplicationChildAssembler {

    public MessageBundleAssembler(String messageBundle, Application application) {
        super(messageBundle, application);
    }

    public void assemble() {
        getApplication().setMessageBundle(getTargetName());
    }

}
