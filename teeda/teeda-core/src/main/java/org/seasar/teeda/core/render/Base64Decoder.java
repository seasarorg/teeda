/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.render;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.faces.context.FacesContext;

import org.seasar.framework.util.Base64Util;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.GZIPInputStreamUtil;
import org.seasar.teeda.core.util.ObjectInputStreamUtil;

/**
 * @author shot
 */
public class Base64Decoder implements Decoder {

    public Object decode(String state) {
        byte[] bytes = Base64Util.decode(state);
        InputStream decodedStream = null;
        InputStream is = null;
        try {
            decodedStream = new ByteArrayInputStream(bytes);
            if (isCompressRequested()) {
                is = GZIPInputStreamUtil.getInputStream(decodedStream);
                return ObjectInputStreamUtil.getObject(is);
            } else {
                return ObjectInputStreamUtil.getObject(decodedStream);
            }
        } finally {
            if(is != null) {
                InputStreamUtil.close(is);
            }
            InputStreamUtil.close(decodedStream);
        }
    }

    protected boolean isCompressRequested() {
        FacesContext context = FacesContext.getCurrentInstance();
        String compressState = context.getExternalContext().getInitParameter(
                JsfConstants.COMPRESS_STATE_ATTR);
        return (compressState != null)
                && compressState.equalsIgnoreCase("true");
    }
}
