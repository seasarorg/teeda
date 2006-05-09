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
package examples.teeda.ajax;

import java.util.List;

import examples.teeda.dto.ZipDto;
import examples.teeda.logic.ZipLogic;

/**
 * @author yone
 */
public class ZipBean {
    private ZipLogic zipLogic;

    private String zipcode;

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setZipLogic(ZipLogic zipLogic) {
        this.zipLogic = zipLogic;
    }

    public String getAddress() {
        List zipDtoList = zipLogic.getAddressDtoList(this.zipcode + "%");

        return createJson("address", zipDtoList);
    }

    private String createJson(String name, List zipDtoList) {
        StringBuffer res = new StringBuffer(1024);
        res.append("{").append(name).append(":[");
        ZipDto[] dtoList = new ZipDto[zipDtoList.size()];
        zipDtoList.toArray(dtoList);
        for (int i = 0; i < dtoList.length; i++) {
            res.append("'").append(dtoList[i].getZipcode()).append(" : ")
                    .append(dtoList[i].getAddress()).append("'").append(",");
        }
        if (dtoList.length > 0)
            res.deleteCharAt(res.length() - 1);
        res.append("]}");

        return res.toString();
    }

}