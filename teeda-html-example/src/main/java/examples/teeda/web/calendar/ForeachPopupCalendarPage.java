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
package examples.teeda.web.calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.seasar.teeda.extension.annotation.convert.DateTimeConverter;
import org.seasar.teeda.extension.annotation.validator.Required;

/**
 * @author koichik
 */
public class ForeachPopupCalendarPage {

    public List<ForeachDto> dtoItems;

    @Required
    public String aaa;

    @DateTimeConverter(target = "doConvert")
    public Date bbb;

    public void initialize() {
        dtoItems = new ArrayList<ForeachDto>();
        dtoItems.add(new ForeachDto("a1", new Date()));
        dtoItems.add(new ForeachDto("a2", new Date()));
    }

    public void doConvert() {
        System.out.println(dtoItems);
    }

    public void doNoConvert() {
        System.out.println(dtoItems);
    }

    public static class ForeachDto implements Serializable {

        private static final long serialVersionUID = 1L;

        public String aaa;

        public Date bbb;

        /**
         * 
         */
        public ForeachDto() {
        }

        /**
         * @param aaa
         * @param bbb
         */
        public ForeachDto(String aaa, Date bbb) {
            this.aaa = aaa;
            this.bbb = bbb;
        }

        public String toString() {
            return aaa + " :: " + bbb;
        }

    }

}
