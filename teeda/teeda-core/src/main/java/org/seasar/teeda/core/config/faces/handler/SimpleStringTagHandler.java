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
package org.seasar.teeda.core.config.faces.handler;

import java.lang.reflect.Method;

import org.seasar.framework.log.Logger;
import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.util.TagConvertUtil;

/**
 * @author shot
 */
public class SimpleStringTagHandler extends JsfTagHandler {

    private static Logger logger = Logger
            .getLogger(SimpleStringTagHandler.class);

    private static final long serialVersionUID = 3257284721212863026L;

    private static final int SINGLE_PARAMETER = 1;

    private String tagName;

    public SimpleStringTagHandler(String tagName) {
        this.tagName = tagName;
    }

    public void end(TagHandlerContext context, String body) {
        setAppropriateProperty(context.peek(), body);
    }

    protected void setAppropriateProperty(Object tag, String context) {
        if (context == null) {
            return;
        }
        String[] setters = TagConvertUtil.convertToSetter(tagName);
        Method[] methods = tag.getClass().getDeclaredMethods();
        String setterName = null;
        Method method = null;
        for (int i = 0; i < setters.length; i++) {
            setterName = setters[i];
            for (int j = 0; j < methods.length; j++) {
                method = methods[j];
                boolean isSameSetter = isSameSetter(method, setterName);
                boolean isSingleParameter = isSingleParameter(method);
                if (isSameSetter && isSingleParameter) {
                    try {
                        method.invoke(tag, new Object[] { context });
                    } catch (Exception ignore) {
                        logger.log(ignore);
                        break;
                    } finally {
                        /*
                         if (logger.isDebugEnabled()) {
                         logger.debug("<" + tagName + ">" + context + "</"
                         + tagName + ">");
                         }
                         */
                    }
                }
            }
        }
    }

    private static boolean isSameSetter(Method method, String setterName) {
        return setterName.equals(method.getName());
    }

    private static boolean isSingleParameter(Method method) {
        return (method.getParameterTypes().length == SINGLE_PARAMETER);
    }

}
