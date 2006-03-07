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
package org.seasar.teeda.core.mock;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yone
 */
public class MockPageContext extends NullPageContext {

    private Map attr_ = new HashMap();

    public void setAttribute(String arg0, Object arg1) {
        attr_.put(arg0, arg1);
    }

    public void setAttribute(String arg0, Object arg1, int arg2) {
        attr_.put(arg0, arg1);
    }

    public Object getAttribute(String arg0) {
        return attr_.get(arg0);
    }

    public Object getAttribute(String arg0, int arg1) {
        return attr_.get(arg0);
    }

}
