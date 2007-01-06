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
package org.seasar.teeda.core.render;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPOutputStream;

import javax.faces.internal.FacesConfigOptions;

import org.seasar.framework.util.Base64Util;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.OutputStreamUtil;
import org.seasar.teeda.core.util.GZIPInputStreamUtil;
import org.seasar.teeda.core.util.GZIPOutputStreamUtil;
import org.seasar.teeda.core.util.ObjectInputStreamUtil;
import org.seasar.teeda.core.util.ObjectOutputStreamUtil;

/**
 * @author shot
 */
public class Base64EncodeConverter implements EncodeConverter {

    public String getAsEncodeString(Object target) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzipOut = null;
        ObjectOutputStream oos = null;
        try {
            if (isCompressRequested()) {
                gzipOut = GZIPOutputStreamUtil.getOutputStream(bos);
                oos = ObjectOutputStreamUtil.getOutputStream(gzipOut);
                ObjectOutputStreamUtil.writeObject(oos, target);
                GZIPOutputStreamUtil.finish(gzipOut);
            } else {
                oos = ObjectOutputStreamUtil.getOutputStream(bos);
                ObjectOutputStreamUtil.writeObject(oos, target);
            }
            return Base64Util.encode(bos.toByteArray());
        } finally {
            OutputStreamUtil.close(oos);
            OutputStreamUtil.close(gzipOut);
            OutputStreamUtil.close(bos);
        }
    }

    public Object getAsDecodeObject(String state) {
        final byte[] bytes = Base64Util.decode(state);
        final InputStream bis = new ByteArrayInputStream(bytes);
        InputStream is = null;
        ObjectInputStream ois = null;
        try {
            if (isCompressRequested()) {
                is = GZIPInputStreamUtil.getInputStream(bis);
                ois = ObjectInputStreamUtil.getInputStream(is);
            } else {
                ois = ObjectInputStreamUtil.getInputStream(bis);
            }
            return ObjectInputStreamUtil.readObject(ois);
        } finally {
            InputStreamUtil.close(ois);
            InputStreamUtil.close(is);
            InputStreamUtil.close(bis);
        }
    }

    protected boolean isCompressRequested() {
        return FacesConfigOptions.getCompressState();
    }

}
