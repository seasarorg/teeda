package org.seasar.teeda.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.seasar.framework.exception.IORuntimeException;

public class GZIPInputStreamUtil {

    private GZIPInputStreamUtil() {
    }
    
    public static GZIPInputStream getInputStream(InputStream is) {
        try {
            return new GZIPInputStream(is);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
}
