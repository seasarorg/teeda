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
