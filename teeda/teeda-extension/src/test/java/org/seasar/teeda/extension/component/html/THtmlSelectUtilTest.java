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
package org.seasar.teeda.extension.component.html;

import java.util.List;

import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class THtmlSelectUtilTest extends TeedaTestCase {

    public void testTakeOverLabel1() throws Exception {
        FacesContext context = getFacesContext();
        THtmlSelectOneMenu select = new THtmlSelectOneMenu();
        select.setValue(new Integer(1));
        select.getChildren().add(new UISelectItem() {

            public String getId() {
                return "aaa";
            }

            public Object getValue() {
                return new SelectItem() {

                    public String getLabel() {
                        return "hoge";
                    }

                    public Object getValue() {
                        return "1";
                    }

                };
            }

        });
        HogePage page = new HogePage();
        THtmlSelectUtil.takeOverLabel(context, select, page, "aaaLabel");
        assertEquals("hoge", page.getAaaLabel());
    }

    public void testTakeOverLabel2() throws Exception {
        FacesContext context = getFacesContext();
        THtmlSelectOneMenu select = new THtmlSelectOneMenu();
        select.setValue(null);
        select.getChildren().add(new UISelectItem() {

            public String getId() {
                return "aaa";
            }

            public Object getValue() {
                return new SelectItem() {

                    public String getLabel() {
                        return "hoge";
                    }

                    public Object getValue() {
                        return "1";
                    }

                };
            }

        });
        HogePage page = new HogePage();
        page.setAaa(new Integer(0));
        THtmlSelectUtil.takeOverLabel(context, select, page, "aaaLabel");
        assertNull(page.getAaaLabel());
    }

    public static class HogePage {

        private Integer aaa;

        private List aaaItems;

        private String aaaLabel;

        public Integer getAaa() {
            return aaa;
        }

        public List getAaaItems() {
            return aaaItems;
        }

        public String getAaaLabel() {
            return aaaLabel;
        }

        public void setAaa(Integer aaa) {
            this.aaa = aaa;
        }

        public void setAaaItems(List aaaItems) {
            this.aaaItems = aaaItems;
        }

        public void setAaaLabel(String aaaLabel) {
            this.aaaLabel = aaaLabel;
        }

    }
}
