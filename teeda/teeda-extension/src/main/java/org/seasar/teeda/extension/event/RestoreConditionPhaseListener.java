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

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.render.Base64EncodeConverter;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.util.ConditionUtil;

/**
 * @author koichik
 */
public class RestoreConditionPhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    protected Base64EncodeConverter converter = new Base64EncodeConverter();

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    public void beforePhase(final PhaseEvent event) {
    }

    public void afterPhase(final PhaseEvent event) {
        final FacesContext context = FacesContext.getCurrentInstance();
        final Map param = context.getExternalContext().getRequestParameterMap();
        final Object o = param.get(ExtensionConstants.CONDITIONS_PARAMETER);
        if (o == null || !(o instanceof String)) {
            return;
        }
        final String encoded = (String) o;
        if (StringUtil.isEmpty(encoded)) {
            return;
        }
        final Object conditions = converter.getAsDecodeObject(encoded);
        if (conditions == null || !(conditions instanceof Map)) {
            return;
        }
        ConditionUtil.setConditions(context, (Map) conditions);
    }

}
