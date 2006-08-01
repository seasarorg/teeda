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
package org.seasar.teeda.core.unit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;

import org.seasar.framework.exception.ResourceNotFoundRuntimeException;
import org.seasar.framework.util.InputStreamReaderUtil;
import org.seasar.framework.util.ReaderUtil;
import org.seasar.framework.util.ResourceUtil;

/**
 * @author manhole
 */
public class TestUtil {

    public static String readText(Class clazz, String fileName, String encoding) {
        String pathByClass = clazz.getName().replace('.', '/') + "_" + fileName;
        InputStream is = null;
        try {
            is = ResourceUtil.getResourceAsStream(pathByClass);
        } catch (ResourceNotFoundRuntimeException e) {
            String pathByPackage = clazz.getPackage().getName().replace('.',
                    '/')
                    + "/" + fileName;
            is = ResourceUtil.getResourceAsStream(pathByPackage);
        }
        Reader reader = InputStreamReaderUtil.create(is, encoding);
        return ReaderUtil.readText(reader);
    }

    public static Object serializeAndDeserialize(final Object input)
            throws IOException, ClassNotFoundException {
        final byte[] serialized = serialize(input);
        final Object output = deserialize(serialized);
        return output;
    }

    private static Object deserialize(final byte[] byteArray)
            throws IOException, ClassNotFoundException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
        final ObjectInputStream ois = new ObjectInputStream(bais);
        final Object output = ois.readObject();
        ois.close();
        return output;
    }

    private static byte[] serialize(final Object input) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(input);
        oos.close();
        return baos.toByteArray();
    }

}
