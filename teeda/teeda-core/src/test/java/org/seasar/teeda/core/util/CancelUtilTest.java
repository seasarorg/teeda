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
package org.seasar.teeda.core.util;

import java.io.IOException;
import java.net.SocketException;

import javax.faces.FacesException;
import javax.faces.event.AbortProcessingException;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class CancelUtilTest extends TeedaTestCase {

    public void testIsCancelled() throws Exception {
        try {
            CancelHandler handler = new DefaultCancelHandler();
            handler.addCancellableException(SocketException.class);
            handler.addCancellableException(AbortProcessingException.class);
            handler.addCancellableException("javax.faces.FacesException");
            register(handler);
            assertFalse(CancelUtil.isCancelled(new IOException()));
            assertTrue(CancelUtil.isCancelled(new SocketException()));
            assertTrue(CancelUtil.isCancelled(new FacesException()));
        } finally {
            CancelUtil.clear();
        }
    }

    public void testIsCancelled2() throws Exception {
        try {
            CancelHandler handler = new DefaultCancelHandler();
            register(handler);
            assertFalse(CancelUtil.isCancelled(new IOException()));
            assertFalse(CancelUtil.isCancelled(new SocketException()));
            assertFalse(CancelUtil.isCancelled(new FacesException()));
        } finally {
            CancelUtil.clear();
        }
    }

}
