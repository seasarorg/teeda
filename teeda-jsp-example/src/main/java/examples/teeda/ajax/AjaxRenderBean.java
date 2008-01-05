/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AjaxRenderBean {
    private String name = "hoge";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object ajaxAction() {
        AjaxRenderDto dto = new AjaxRenderDto();
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String date = df.format(cal.getTime());
        dto.setDate(date);
        List list = new ArrayList();
        DataTableBean bean1 = new DataTableBean();
        bean1.setDomainName("python.org");
        bean1.setCreationDate("1995-03-27");
        bean1.setExpireDate("2007-03-28");
        bean1.setOrganizationName("Python Software Foundation");
        DataTableBean bean2 = new DataTableBean();
        bean2.setDomainName("undefined.org");
        bean2.setCreationDate("2000-01-10");
        bean2.setExpireDate("2000-01-10");
        bean2.setOrganizationName("Robert J Ippolito");
        DataTableBean bean3 = new DataTableBean();
        bean3.setDomainName("pythonmac.org");
        bean3.setCreationDate("2003-09-24");
        bean3.setExpireDate("2006-09-24");
        bean3.setOrganizationName("Bob Ippolito");
        DataTableBean bean4 = new DataTableBean();
        bean4.setDomainName("xml.com");
        bean4.setCreationDate("1996-09-30");
        bean4.setExpireDate("2012-09-12");
        bean4.setOrganizationName("Tim Bray");
        DataTableBean bean5 = new DataTableBean();
        bean5.setDomainName("seasar.org");
        bean5.setCreationDate("2003-03-25");
        bean5.setExpireDate("2012-09-12");
        bean5.setOrganizationName("The Seasar Foundation");
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        dto.setDataList(list);
        return dto;
    }

}
