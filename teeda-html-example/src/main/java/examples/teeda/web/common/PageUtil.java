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
package examples.teeda.web.common;

public class PageUtil {

    public static final int EDIT_NOT_CHANGE = 0;

    public static final int EDIT_ADD        = 1;

    public static final int EDIT_UPDATE     = 2;

    public static final int EDIT_DELETE     = 3;

    public static String getStyleClass(int status) {
        String styleClass = null;

        switch (status) {
        case EDIT_ADD:
            styleClass = "editStatusAdd";
            break;
        case EDIT_UPDATE:
            styleClass = "editStatusUpdate";
            break;
        case EDIT_DELETE:
            styleClass = "editStatusDelete";
            break;
        default:
            break;
        }

        return styleClass;
    }

    public static String getStyle(int status) {
        String style = null;

        switch (status) {
        case EDIT_ADD:
            style = "";
            break;
        case EDIT_UPDATE:
            style = "";
            break;
        case EDIT_DELETE:
            style = "display : none;";
            break;
        default:
            break;
        }

        return style;
    }

}
