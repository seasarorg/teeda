package javax.faces.component;

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


class UIDataUtil_ {

    private UIDataUtil_(){
    }
    
    public static DataModel getSuitableDataModel(Object obj){
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
