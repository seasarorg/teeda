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
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.faces.application.StateManager.SerializedView;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.Base64Util;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.GZIPInputStreamUtil;
import org.seasar.teeda.core.util.GZIPOutputStreamUtil;
import org.seasar.teeda.core.util.ObjectInputStreamUtil;
import org.seasar.teeda.core.util.ObjectOutputStreamUtil;
import org.seasar.teeda.core.util.OutputStreamUtil;

/**
 * @author shot
 */
public class Base64EncodeConverter implements EncodeConverter {

    public String getAsEncodeString(Object target) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream out = null;
        ObjectOutputStream oos = null;
        try {
            if (isCompressRequested()) {
                out = GZIPOutputStreamUtil.getOutputStream(bos);
                oos = ObjectOutputStreamUtil.getOutputStream(out);
            } else {
                oos = ObjectOutputStreamUtil.getOutputStream(bos);
            }
            if (target instanceof SerializedView) {
                SerializedView view = (SerializedView) target;
                ObjectOutputStreamUtil.writeObject(oos, view.getStructure());
                ObjectOutputStreamUtil.writeObject(oos, view.getState());
            } else {
                ObjectOutputStreamUtil.writeObject(oos, target);
            }
            return Base64Util.encode(bos.toByteArray());
        } finally {
            if (oos != null) {
                OutputStreamUtil.close(oos);
            }
            if (out != null) {
                OutputStreamUtil.close(out);
            }
            OutputStreamUtil.close(bos);
        }
    }

    public Object getAsDecodeObject(String state) {
        byte[] bytes = Base64Util.decode(state);
        InputStream bis = null;
        InputStream is = null;
        Object structure = null;
        Object viewState = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            if (isCompressRequested()) {
                is = GZIPInputStreamUtil.getInputStream(bis);
                ois = ObjectInputStreamUtil.getInputStream(is);
            } else {
                ois = ObjectInputStreamUtil.getInputStream(bis);
            }
            structure = ObjectInputStreamUtil.readObject(ois);
            viewState = ObjectInputStreamUtil.readObject(ois);
            storeViewState(viewState);
            return structure;
        } finally {
            if (ois != null) {
                InputStreamUtil.close(ois);
            }
            if (is != null) {
                InputStreamUtil.close(is);
            }
            InputStreamUtil.close(bis);
        }
    }

    protected boolean isCompressRequested() {
        FacesContext context = FacesContext.getCurrentInstance();
        String compressState = context.getExternalContext().getInitParameter(
                JsfConstants.COMPRESS_STATE_ATTR);
        return "true".equalsIgnoreCase(compressState);
    }

    protected void storeViewState(Object viewState) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getRequestMap().put(
                AbstractResponseStateManager.FACES_VIEW_STATE, viewState);
    }
}
