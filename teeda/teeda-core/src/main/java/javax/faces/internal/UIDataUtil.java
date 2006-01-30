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
package javax.faces.internal;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;

import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.ResultDataModel;
import javax.faces.model.ResultSetDataModel;
import javax.faces.model.ScalarDataModel;
import javax.servlet.jsp.jstl.sql.Result;

/**
 * @author shot
 * 
 * This class might be changed without a previous notice. Please do not use it
 * excluding the JSF specification part.
 */
public class UIDataUtil {

    private UIDataUtil() {
    }

    public static DataModel getSuitableDataModel(Object obj) {
        DataModel model = null;
        if (obj == null) {
            model = new ListDataModel(Collections.EMPTY_LIST);
        } else if (obj instanceof DataModel) {
            model = (DataModel) obj;
        } else if (obj instanceof List) {
            model = new ListDataModel((List) obj);
        } else if (Object[].class.isAssignableFrom(obj.getClass())) {
            model = new ArrayDataModel((Object[]) obj);
        } else if (obj instanceof ResultSet) {
            model = new ResultSetDataModel((ResultSet) obj);
        } else if (obj instanceof Result) {
            model = new ResultDataModel((Result) obj);
        } else {
            model = new ScalarDataModel(obj);
        }
        return model;
    }

}
