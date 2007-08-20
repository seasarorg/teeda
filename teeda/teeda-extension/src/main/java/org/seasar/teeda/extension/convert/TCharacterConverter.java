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
package org.seasar.teeda.extension.convert;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.CharacterConverter;

import org.seasar.framework.util.StringUtil;

/**
 * @author shot
 */
public class TCharacterConverter extends CharacterConverter implements
        StateHolder {

    protected String objectMessageId;

    protected String stringMessageId;

    protected boolean transientValue = false;

    /**
     * @return Returns the objectMessageId.
     */
    public String getObjectMessageId() {
        return (!StringUtil.isEmpty(objectMessageId)) ? objectMessageId : super
                .getObjectMessageId();
    }

    /**
     * @param objectMessageId The objectMessageId to set.
     */
    public void setObjectMessageId(String objectMessageId) {
        this.objectMessageId = objectMessageId;
    }

    /**
     * @return Returns the stringMessageId.
     */
    public String getStringMessageId() {
        return (!StringUtil.isEmpty(stringMessageId)) ? stringMessageId : super
                .getStringMessageId();
    }

    /**
     * @param stringMessageId The stringMessageId to set.
     */
    public void setStringMessageId(String stringMessageId) {
        this.stringMessageId = stringMessageId;
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        objectMessageId = (String) values[0];
        stringMessageId = (String) values[1];
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[2];
        values[0] = objectMessageId;
        values[1] = stringMessageId;
        return null;
    }

    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }

}
