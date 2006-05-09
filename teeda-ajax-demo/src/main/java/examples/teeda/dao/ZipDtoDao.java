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
package examples.teeda.dao;

import java.util.List;

import examples.teeda.dto.ZipDto;

/**
 * @author yone
 */
public interface ZipDtoDao {

    public Class BEAN = ZipDto.class;

    static final String getAllAddressCount_QUERY = "SELECT count(zipcode) FROM DEMO_POST";

    static final String getAddress_QUERY = "SELECT zipcode, address FROM DEMO_POST WHERE zipcode LIKE /*zipcode*/'130%' ORDER BY zipcode";

    int getAllAddressCount();

    List getAddress(String zipcode);
}
