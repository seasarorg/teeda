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

import java.util.Iterator;
import java.util.List;

import org.seasar.framework.log.Logger;

/**
 * @author shot
 */
public class CancelUtil {

    private static final Logger logger = Logger.getLogger(CancelUtil.class);

    private static CancelHandler handler = null;

    public static boolean isCancelled(Throwable t) {
        if (t == null) {
            return false;
        }
        if (handler == null) {
            handler = (CancelHandler) DIContainerUtil
                    .getComponentNoException(CancelHandler.class);
            if (handler == null) {
                return false;
            }
        }
        final List candidates = handler.getCancellableExceptions();
        for (Iterator itr = candidates.iterator(); itr.hasNext();) {
            Class tc = (Class) itr.next();
            if (t.getClass() == tc) {
                logger.log("WTDA0206", new Object[] { t });
                return true;
            }
        }
        final List nameCandidates = handler.getCancellableExceptionNames();
        for (Iterator itr = nameCandidates.iterator(); itr.hasNext();) {
            final String name = (String) itr.next();
            if (t.getClass().getName().endsWith(name)) {
                logger.log("WTDA0206", new Object[] { t });
                return true;
            }
        }
        return false;
    }

    public static void clear() {
        handler = null;
    }
}
