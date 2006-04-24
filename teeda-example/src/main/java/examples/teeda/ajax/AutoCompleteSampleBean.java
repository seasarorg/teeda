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
package examples.teeda.ajax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * @author mopemope
 */
public class AutoCompleteSampleBean {

    private static final String[] JSON_DATA = { "test1", "test2", "test3",
            "dummy1", "dummy2", "dummy3", "dummy4", "teeda1", "teeda2", "teeda3" };

    private HttpServletResponse response;

    private String name;

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String autoComplete() {
        List list = new ArrayList();
        for (int i = 0; i < JSON_DATA.length; i++) {
            if (JSON_DATA[i].indexOf(this.name) > -1) {
                list.add(JSON_DATA[i]);
            }
        }
        StringBuffer buf = new StringBuffer();
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            buf.append("\""+iter.next()+"\"");
            buf.append(",");
        }
        if(buf.length() > 0){
            buf.delete(buf.length() - 1, buf.length());
        }
        return "{data:[" + buf.toString() + "]}";
    }
    

}