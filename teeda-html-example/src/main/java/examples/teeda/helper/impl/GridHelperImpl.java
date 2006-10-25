package examples.teeda.helper.impl;

import java.util.ArrayList;
import java.util.List;

import examples.teeda.helper.GridCondition;
import examples.teeda.helper.GridHelper;

/**
 * グリッドに関する操作を行うヘルパー。<br>
 * ページングおよびソート処理を行う。
 * 
 * @author takanori
 */
public class GridHelperImpl implements GridHelper {

    public List pagingFilter(List data, GridCondition condition) {
        int offset = condition.getOffset();
        int limit = condition.getLimit();
        List pagingResult = pagingFilter(data, offset, limit);

        return pagingResult;
    }

    public List pagingFilter(List data, int offset, int limit) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset : " + offset);
        }
        if (limit < 0) {
            throw new IllegalArgumentException("limit : " + limit);
        }

        List pagingResult;
        if (data == null || data.size() <= 0) {
            return new ArrayList();
        } else if (offset > data.size()) {
            return new ArrayList();
        }

        int lastIndex = offset + limit;
        if (lastIndex > data.size()) {
            lastIndex = data.size();
        }
        pagingResult = new ArrayList(data.subList(offset, lastIndex));

        return pagingResult;
    }

    public List sort(List data, GridCondition condition) {
        String sortProperty = condition.getSortProperty();
        boolean sortOrder = condition.isSortOrder();

        List sortedList = sort(data, sortProperty, sortOrder);
        return sortedList;
    }

    public List sort(List data, String sortProperty, boolean sortOrder) {
        // TODO
        return null;
    }

}
