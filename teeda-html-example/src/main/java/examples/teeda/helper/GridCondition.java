package examples.teeda.helper;

public class GridCondition {

    /** ページング時の最大件数のデフォルト値（100件） */
    public static final int     DEFAULT_LIMIT = 100;

    /** ソート順（降順） */
    public static final boolean SORT_NORMAL   = false;

    /** ソート順（昇順） */
    public static final boolean SORT_REVERSE  = true;

    /** ページング対象データの総件数 */
    private int                 count;

    /** ページング時の開始位置 */
    private int                 offset;

    /** ページング時の最大件数 */
    private int                 limit;

    /** ソートプロパティ */
    private String              sortProperty;

    /** ソート順 */
    private boolean             sortOrder     = SORT_NORMAL;

    /**
     * コンストラクタ。
     */
    public GridCondition() {}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSortProperty() {
        return sortProperty;
    }

    public void setSortProperty(String sortProperty) {
        this.sortProperty = sortProperty;
    }

    public boolean isSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(boolean sortOrder) {
        this.sortOrder = sortOrder;
    }

}
