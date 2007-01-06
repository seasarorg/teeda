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
package test.examples.teeda.dao;

import java.util.List;

import org.seasar.dao.unit.S2DaoTestCase;

import examples.teeda.dao.ZipDtoDao;

/**
 * @author yone
 */
public class ZipDtoDaoTest extends S2DaoTestCase {

    private ZipDtoDao zipDtoDao;

    public void setUpContainer() throws Throwable {
        setServletContext(new MyMockServletContextImpl());
        super.setUpContainer();
    }

    public void setUp() {
        include("app.dicon");
    }

    public void testGetSearchCountAll() throws Exception {
        // test for insert count [130272]
        assertEquals("1", 130272, zipDtoDao.getAllAddressCount());
    }

    public void testSearchZipDtoList() throws Exception {
        // test for tokyo address
        List addresses = zipDtoDao.getAddress("1%");
        assertNotNull("searchZipDtoList", addresses);
        System.out.println("- Tokyo Address Count=[" + addresses.size() + "]");
    }

}