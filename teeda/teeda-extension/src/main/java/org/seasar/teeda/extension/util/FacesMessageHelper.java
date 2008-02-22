/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

/**
 * @author shot
 *
 * JSFに対してメッセージを追加したりするHelper.
 * このクラスは基本削除変更はしない．
 *
 */
public interface FacesMessageHelper {

    /**
     * WARNレベルのメッセージを追加する.
     * @param messageId
     */
    public void addWarnMessage(String messageId);

    public void addWarnMessage(String messageId, Object[] args);

    public void addWarnMessage(String clientId, String messageId);

    public void addWarnMessage(String clientId, String messageId, Object[] args);

    /**
     * INFOレベルのメッセージを追加する.画面への通知メッセージ用途に使う.
     * @param messageId
     */
    public void addInfoMessage(String messageId);

    public void addInfoMessage(String messageId, Object[] args);

    public void addInfoMessage(String clientId, String messageId);

    public void addInfoMessage(String clientId, String messageId, Object[] args);

    /**
     * ERRORレベルのメッセージを追加する.画面へのバリデーションメッセージ用途に使う.
     * @param messageId
     */
    public void addErrorMessage(String messageId);

    public void addErrorMessage(String messageId, Object[] args);

    public void addErrorMessage(String clientId, String messageId);

    public void addErrorMessage(String clientId, String messageId, Object[] args);

    /**
     * FATALレベルのメッセージを追加する.
     * @param messageId
     */
    public void addFatalMessage(String messageId);

    public void addFatalMessage(String messageId, Object[] args);

    public void addFatalMessage(String clientId, String messageId);

    public void addFatalMessage(String clientId, String messageId, Object[] args);

    /**
     * 既にFacesContextにmessageIdに紐づくFacesMessageがあれば、そのサマリを返す.
     * @param messageId
     * @param args
     */
    public String getSummary(String messageId, Object[] args);

    /**
     * 既にFacesContextにmessageIdに紐づくFacesMessageがあれば、そのディティールを返す.
     * @param messageId
     * @param args
     */
    public String getDetail(String messageId, Object[] args);

    /**
     * @return FacesMessageが存在する場合、true
     */
    public boolean hasMessages();

    /**
     * @return ERRORかFATALのFacesMessageが存在する場合、true
     */
    public boolean hasErrorOrFatalMessage();
}
