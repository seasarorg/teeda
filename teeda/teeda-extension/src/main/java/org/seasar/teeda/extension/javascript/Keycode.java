/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.javascript;

/**
 * @author shot
 *
 */
public interface Keycode {

    //TODO use this when generating JavaScript dynamically.
    //0-9
    int[] NUMBER_CODE = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 96, 97, 98,
            99, 100, 101, 102, 103, 104, 105 };

    //a-zA-Z for onkeypress
    int[] ONKEYPRESS_ALPHABET_CODE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74,
            75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97,
            98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111,
            112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };

    //a-zA-Z for onkeydown
    int[] ONKEYDOWN_ALPHABET_CODE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74,
            75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90 };

    //mbtMBT
    int[] MONETARY_UNIT_CODE = { 66, 77, 84, 98, 109, 116 };

    int TAB_CODE = 9;

    //left, up, right, down, and tab.
    int[] MOVABLE_CODE = { 37, 38, 39, 40, TAB_CODE };

    //. for IE, FireFox
    int[] PERIOD_CODE = { 46, 110, 190 };

    //, for IE, FireFox
    int[] COMMA_CODE = { 44, 188 };

    //del
    int DELETE_CODE = 46;

    //backspace
    int BACKSPACE_CODE = 8;

    int[] DELETABLE_CODE = { DELETE_CODE, BACKSPACE_CODE };

    int ONKEYPRESS_MINUS_CODE = 45;

    //be careful. 109 is used for alphabetical code as "m" for onkeypress
    //109 for IE, 189 for FireFox.
    int[] ONKEYDOWN_MINUS_CODE = { 109, 189 };

    int ENTER_CODE = 13;
}
