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
package examples.teeda.web.grid;

public class BaseGridPage {

    private static final int DEFAULT_LIMIT = 2;

    private String           sortId;

    private String           sortOrder;

    private int              count = 10;

    private int              limit         = DEFAULT_LIMIT;

    private int              offset;

    // TODO : int／Integer にできない。インスタンスの生成に失敗してしまう。
    private String           paging;

    private int              pagingIndex;

    private int              currentIndex;

    public BaseGridPage() {}

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String[] getPagingItems() {
        int pageCount = 0;
        if (this.limit > 0) {
            pageCount = this.count / this.limit;
        }

        String[] pagingItems = new String[pageCount];
        for (int i = 0; i < pageCount; i++) {
            pagingItems[i] = (new Integer(i + 1)).toString();
        }

        return pagingItems;
    }

    public void setPagingItems(String[] pageingItems) {
    // do nothing.
    }

    public String getPaging() {
        return this.paging;
    }

    public void setPaging(String paging) {
        this.paging = paging;
    }

    public int getPagingIndex() {
        return pagingIndex;
    }

    public void setPagingIndex(int pagingIndex) {
        this.pagingIndex = pagingIndex;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public boolean isCurrent() {
        boolean isCurrent = (this.currentIndex == this.pagingIndex);
        return isCurrent;
    }

    public void setCurrent(boolean isCurrent) {
    // do nothing.
    }

}
