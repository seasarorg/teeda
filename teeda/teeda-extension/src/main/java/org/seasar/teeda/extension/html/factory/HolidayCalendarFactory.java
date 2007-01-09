/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.html.factory;

import java.util.Map;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author higa
 */
public class HolidayCalendarFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "holidayCalendar";

    private static final String HOLIDAYS = "Holidays";

    private static final String JANUARY = "january";

    private static final String FEBRUARY = "february";

    private static final String MARCH = "march";

    private static final String APRIL = "april";

    private static final String MAY = "may";

    private static final String JUNE = "june";

    private static final String JULY = "july";

    private static final String AUGUST = "august";

    private static final String SEPTEMBER = "september";

    private static final String OCTOBER = "october";

    private static final String NOVEMBER = "november";

    private static final String DECEMBER = "december";

    private static final String HOLIDAYS_YEAR = "holidaysYear";

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return ExtensionConstants.TEEDA_EXTENSION_URI;
    }

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (!JsfConstants.SPAN_ELEM.equalsIgnoreCase(elementNode.getTagName())) {
            return false;
        }
        String id = elementNode.getId();
        if (id == null) {
            return false;
        }
        if (pageDesc == null) {
            return false;
        }
        if (!id.endsWith(HOLIDAYS)) {
            return false;
        }
        return pageDesc.hasProperty(id);
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        if (pageDesc == null) {
            return;
        }
        String pageName = pageDesc.getPageName();
        String id = elementNode.getId();
        properties.put(JsfConstants.VALUE_ATTR, getBindingExpression(pageName,
                id));
        String month = calcMonth(id);
        if (month != null) {
            properties.put(ExtensionConstants.MONTH_ATTR, month);
        }
        if (pageDesc.hasProperty(HOLIDAYS_YEAR)) {
            properties.put(ExtensionConstants.YEAR_ATTR, getBindingExpression(
                    pageName, HOLIDAYS_YEAR));
        }
    }

    protected String calcMonth(String id) {
        String s = StringUtil.trimSuffix(id, HOLIDAYS);
        if (JANUARY.equals(s)) {
            return "0";
        }
        if (FEBRUARY.equals(s)) {
            return "1";
        }
        if (MARCH.equals(s)) {
            return "2";
        }
        if (APRIL.equals(s)) {
            return "3";
        }
        if (MAY.equals(s)) {
            return "4";
        }
        if (JUNE.equals(s)) {
            return "5";
        }
        if (JULY.equals(s)) {
            return "6";
        }
        if (AUGUST.equals(s)) {
            return "7";
        }
        if (SEPTEMBER.equals(s)) {
            return "8";
        }
        if (OCTOBER.equals(s)) {
            return "9";
        }
        if (NOVEMBER.equals(s)) {
            return "10";
        }
        if (DECEMBER.equals(s)) {
            return "11";
        }
        return null;
    }

    public boolean isLeaf() {
        return true;
    }
}