/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.helper.impl;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.log.Logger;
import org.seasar.teeda.extension.helper.GridHelper;

/**
 * @author higa
 *
 */
public class GridHelperImpl implements GridHelper {

    private static final long serialVersionUID = 1L;

    private long token = 0;

    private Map tables = new HashMap();

    protected String createToken() {
        return String.valueOf(token++);
    }

    public synchronized String addTable(String table) {
        String token = createToken();
        tables.put(token, table);
        return token;
    }

    public synchronized String ajaxGetTable(String token) {
        return (String) tables.remove(token);
    }
}
