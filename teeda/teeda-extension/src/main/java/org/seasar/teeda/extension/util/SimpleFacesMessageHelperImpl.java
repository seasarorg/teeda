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
package org.seasar.teeda.extension.util;

import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;

/**
 * @author shot
 */
public class SimpleFacesMessageHelperImpl implements FacesMessageHelper {

    public void addErrorMessage(String messageId) {
        FacesMessageUtil.addErrorMessage(messageId);
    }

    public void addErrorMessage(String messageId, Object[] args) {
        FacesMessageUtil.addErrorMessage(messageId, args);
    }

    public void addErrorMessage(String clientId, String messageId) {
        final FacesContext context = FacesContext.getCurrentInstance();
        FacesMessageUtil.addErrorComponentMessage(context, clientId, messageId);
    }

    public void addErrorMessage(String clientId, String messageId, Object[] args) {
        final FacesContext context = FacesContext.getCurrentInstance();
        FacesMessageUtil.addErrorComponentMessage(context, clientId, messageId,
                args);
    }

    public void addFatalMessage(String messageId) {
        FacesMessageUtil.addFatalMessage(messageId);
    }

    public void addFatalMessage(String messageId, Object[] args) {
        FacesMessageUtil.addFatalMessage(messageId, args);
    }

    public void addFatalMessage(String clientId, String messageId) {
        final FacesContext context = FacesContext.getCurrentInstance();
        FacesMessageUtil.addFatalComponentMessage(context, clientId, messageId);
    }

    public void addFatalMessage(String clientId, String messageId, Object[] args) {
        final FacesContext context = FacesContext.getCurrentInstance();
        FacesMessageUtil.addFatalComponentMessage(context, clientId, messageId,
                args);
    }

    public void addInfoMessage(String messageId) {
        FacesMessageUtil.addInfoMessage(messageId);
    }

    public void addInfoMessage(String messageId, Object[] args) {
        FacesMessageUtil.addInfoMessage(messageId, args);
    }

    public void addInfoMessage(String clientId, String messageId) {
        final FacesContext context = FacesContext.getCurrentInstance();
        FacesMessageUtil.addInfoComponentMessage(context, clientId, messageId);
    }

    public void addInfoMessage(String clientId, String messageId, Object[] args) {
        final FacesContext context = FacesContext.getCurrentInstance();
        FacesMessageUtil.addInfoComponentMessage(context, clientId, messageId,
                args);
    }

    public void addWarnMessage(String messageId) {
        FacesMessageUtil.addWarnMessage(messageId);
    }

    public void addWarnMessage(String messageId, Object[] args) {
        FacesMessageUtil.addWarnMessage(messageId, args);
    }

    public void addWarnMessage(String clientId, String messageId) {
        final FacesContext context = FacesContext.getCurrentInstance();
        FacesMessageUtil.addWarnComponentMessage(context, clientId, messageId);
    }

    public void addWarnMessage(String clientId, String messageId, Object[] args) {
        final FacesContext context = FacesContext.getCurrentInstance();
        FacesMessageUtil.addWarnComponentMessage(context, clientId, messageId,
                args);
    }

    public String getDetail(String messageId, Object[] args) {
        return FacesMessageUtil.getDetail(messageId, args);
    }

    public String getSummary(String messageId, Object[] args) {
        return FacesMessageUtil.getSummary(messageId, args);
    }

    public boolean hasErrorOrFatalMessage() {
        return FacesMessageUtil.hasErrorOrFatalMessage();
    }

    public boolean hasMessages() {
        return FacesMessageUtil.hasMessages();
    }

}
