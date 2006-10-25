package examples.teeda.helper;

import java.util.List;

/**
 * グリッドに関する操作を行うヘルパーのインタフェース。
 * 
 * @author takanori
 */
public interface GridHelper {
    
    /**
     * 指定されたリストを条件によってページングします。
     * 
     * @param data ページング対象のリスト
     * @param condition ページング条件
     * @return ページングされたリスト
     */
    List pagingFilter(List data, GridCondition condition);

    /**
     * 指定されたリストを条件によってページングします。
     * 
     * @param data ページング対象のリスト
     * @param offset ページング時の開始位置
     * @param limit ページング時の最大件数
     * @return ページングされたリスト
     */
    List pagingFilter(List data, int offset, int limit);
    
    /**
     * 指定されたリストを条件によってソートします。
     * 
     * @param data ソート対象のリスト
     * @param condition ソート条件
     * @return ソートされたリスト
     */
    List sort(List data, GridCondition condition);

    /**
     * 指定されたリストを条件によってソートします。
     * 
     * @param data ソート対象のリスト
     * @param sortProperty ソートのキーとなる
     * @param sortOrder ソート順（trueの場合は降順、 falseの場合は昇順）
     * @return ソートされたリスト
     */
    List sort(List data, String sortProperty, boolean sortOrder);

}
