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

import examples.teeda.web.common.PageUtil;
import examples.teeda.web.dto.FooDto;

public class GridEditConfirmPage extends BaseGridEditPage {

    public GridEditConfirmPage() {}

    public String initialize() {
        return null;
    }

    public String prerender() {
        return null;
    }
    
    public String getBbbStyleClass() {
        String cssClass = PageUtil.getStyleClass(super.bbbEditStatus);
        return cssClass;
    }

    public String doSubmit() {
        for(int i = 0; i < this.fooItems.length; i++) {
            FooDto dto = this.fooItems[i];
            System.out.println(dto);
        }
        return null;
    }

}
