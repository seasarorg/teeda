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
package org.seasar.teeda.core.util;

import java.util.Date;

import junit.framework.TestCase;

/**
 * @author yone
 */
public class ConverterUtilTest extends TestCase {
    
    public void testConvertToInt() throws Exception {
        assertEquals(20, ConverterUtil.convertToInt(new Integer(20)));
        assertEquals(10, ConverterUtil.convertToInt("10"));
        boolean goException = false;
        try {
            ConverterUtil.convertToInt("hoge");
        } catch(IllegalArgumentException e) {
            goException = true;
            System.out.println(e.getMessage());
        }
        assertTrue(goException);
        goException = false;
        try {
            ConverterUtil.convertToInt(new Boolean(false));
        } catch(IllegalArgumentException e) {
            goException = true;
            System.out.println(e.getMessage());
        }
        assertTrue(goException);
    }
    
    public void testConvertToBoolean() throws Exception {
        assertTrue(ConverterUtil.convertToBoolean(new Boolean(true)));
        assertFalse(ConverterUtil.convertToBoolean("false"));
        boolean goException = false;
        try {
            ConverterUtil.convertToBoolean(new Date());
        } catch(IllegalArgumentException e) {
            goException = true;
            System.out.println(e.getMessage());
        }
        assertTrue(goException);
    }
    
    public void testConvertToLong() throws Exception {
        assertEquals(8888888888L, ConverterUtil.convertToLong(new Long(8888888888L)));
        assertEquals(9999999999L, ConverterUtil.convertToLong("9999999999"));
        boolean goException = false;
        try {
            ConverterUtil.convertToLong("hoge");
        } catch(IllegalArgumentException e) {
            goException = true;
            System.out.println(e.getMessage());
        }
        assertTrue(goException);
        goException = false;
        try {
            ConverterUtil.convertToLong(new Boolean(false));
        } catch(IllegalArgumentException e) {
            goException = true;
            System.out.println(e.getMessage());
        }
        assertTrue(goException);       
    }
    
    public void testConvertToDouble() throws Exception {
        assertTrue(1d == ConverterUtil.convertToDouble(new Double(1d)));
        assertTrue(2d == ConverterUtil.convertToDouble("2"));
        boolean goException = false;
        try {
            ConverterUtil.convertToDouble("hoge");
        } catch(IllegalArgumentException e) {
            goException = true;
            System.out.println(e.getMessage());
        }
        assertTrue(goException);
        goException = false;
        try {
            ConverterUtil.convertToDouble(new Boolean(false));
        } catch(IllegalArgumentException e) {
            goException = true;
            System.out.println(e.getMessage());
        }
        assertTrue(goException);       
    }
}
