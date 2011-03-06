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
package org.seasar.teeda.core.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.log.Logger;

/**
 * @author shot
 */
public class MeasurementInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger
            .getLogger(MeasurementInterceptor.class);

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy/MM/dd hh:mm:ss SSS zzz", Locale.getDefault());

    public Object invoke(MethodInvocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        String startDate = formatter.format(new Date());
        final Class clazz = getTargetClass(invocation);
        logger.debug("[measurement] start date = " + startDate + "("
                + clazz.getName() + ")");
        try {
            return invocation.proceed();
        } finally {
            logger.debug("[measurement] perform ms:"
                    + new Long(System.currentTimeMillis() - start));
            String endDate = formatter.format(new Date());
            logger.debug("[measurement] end date = " + endDate + "("
                    + clazz.getName() + ")");
        }

    }

}
