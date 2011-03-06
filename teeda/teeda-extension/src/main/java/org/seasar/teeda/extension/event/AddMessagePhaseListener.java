/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.event;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.teeda.extension.util.ExternalMessageUtil;

/**
 * @author koichik
 */
public class AddMessagePhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    public void beforePhase(final PhaseEvent event) {
        final Map messages = ExternalMessageUtil.getMessages(event
                .getFacesContext());
        if (messages != null) {
            for (final Iterator it = messages.entrySet().iterator(); it
                    .hasNext();) {
                final Entry entry = (Entry) it.next();
                final String key = (String) entry.getKey();
                final Object[] value = (Object[]) entry.getValue();
                FacesMessageUtil.addErrorMessage(key, value);
            }
        }
    }

    public void afterPhase(final PhaseEvent event) {
    }

}
