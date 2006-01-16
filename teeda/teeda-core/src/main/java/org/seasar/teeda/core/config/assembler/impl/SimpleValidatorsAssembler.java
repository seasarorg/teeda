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

import java.util.Iterator;
import java.util.Map;

import javax.faces.application.Application;

import org.seasar.teeda.core.config.assembler.ValidatorsAssembler;
import org.seasar.teeda.core.util.ApplicationUtil;
import org.seasar.teeda.core.util.IteratorUtil;

/**
 * @author shot
 */
public class SimpleValidatorsAssembler extends ValidatorsAssembler {

    private Application application_;
    
    public SimpleValidatorsAssembler(Map validators) {
        super(validators);
    }

    protected void setupBeforeAssemble() {
        application_ = ApplicationUtil.getApplicationFromFactory();
    }

    public void assemble() {
        String validatorId = null;
        String validatorClass = null;
        for(Iterator itr = IteratorUtil.getIterator(getValidators());itr.hasNext();){
            Map.Entry entry = (Map.Entry)itr.next();
            validatorId = (String)entry.getKey();
            validatorClass = (String)entry.getValue();
            application_.addValidator(validatorId, validatorClass);
        }
    }

}
