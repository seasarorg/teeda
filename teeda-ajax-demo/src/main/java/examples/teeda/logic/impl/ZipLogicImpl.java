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
package examples.teeda.logic.impl;

import java.util.List;

import examples.teeda.dao.ZipDtoDao;
import examples.teeda.logic.ZipLogic;

/**
 * @author yone
 */
public class ZipLogicImpl implements ZipLogic {

    private ZipDtoDao zipDtoDao;

    public void setZipDtoDao(ZipDtoDao zipDtoDao) {
        this.zipDtoDao = zipDtoDao;
    }

    public int getAllAddressCount() {
        return this.zipDtoDao.getAllAddressCount();
    }

    public List getAddressDtoList(String zipcode) {
        return this.zipDtoDao.getAddress(zipcode);
    }

}
