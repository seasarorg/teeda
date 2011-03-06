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
