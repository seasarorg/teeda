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
package org.seasar.teeda.extension.render.html;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.fileupload.FileItem;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.render.EditableValueHolderDecoder;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.util.UploadedFileImpl;

/**
 * @author koichik
 */
public class UploadedFileDecoder extends EditableValueHolderDecoder {

    public void decode(final FacesContext context, final UIComponent component) {
        AssertionUtil.assertNotNull("context is null.", context);
        AssertionUtil.assertNotNull("component is null.", component);
        final Map requestMap = context.getExternalContext().getRequestMap();
        final Map fileItems = (Map) requestMap
                .get(ExtensionConstants.REQUEST_ATTRIBUTE_UPLOADED_FILES);
        if (fileItems == null) {
            return;
        }

        final String clientId = getClientId(component, context);
        final FileItem fileItem = (FileItem) fileItems.get(clientId);
        if (fileItem == null) {
            return;
        }

        final ValueHolderWrapper wrapper = createValueHolderWrapper(component);
        if (StringUtil.isEmpty(fileItem.getName())) {
            wrapper.setValue("");
        } else {
            wrapper.setValue(new UploadedFileImpl(fileItem));
        }
    }

}
