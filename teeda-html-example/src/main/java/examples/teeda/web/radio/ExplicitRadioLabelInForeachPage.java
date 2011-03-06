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
package examples.teeda.web.radio;

/**
 * @author manhole
 */
public class ExplicitRadioLabelInForeachPage {

    public String prerender() {
        if (radioSelectedItems != null) {
            for (int i = 0; i < radioSelectedItems.length; i++) {
                System.out.println("[" + i + "] "
                    + radioSelectedItems[i].toString());
            }
        }
        return null;
    }

    public void initialize() {
        radioSelectedItems = new RadioSelected[3];
        radioSelectedItems[0] = new RadioSelected();
        radioSelectedItems[0].setAaa(new Integer(2));
        radioSelectedItems[1] = new RadioSelected();
        radioSelectedItems[1].setAaa(new Integer(1));
        radioSelectedItems[2] = new RadioSelected();
        radioSelectedItems[2].setAaa(new Integer(3));
    }

    private RadioSelected[] radioSelectedItems;

    private Integer aaa;

    public Integer getAaa() {
        return aaa;
    }

    public void setAaa(final Integer aaa) {
        this.aaa = aaa;
    }

    public String doAction() {
        return null;
    }

    public RadioSelected[] getRadioSelectedItems() {
        return radioSelectedItems;
    }

    public void setRadioSelectedItems(final RadioSelected[] radioSelectedItems) {
        this.radioSelectedItems = radioSelectedItems;
    }

    public static class RadioSelected {

        private Integer aaa;

        public Integer getAaa() {
            return aaa;
        }

        public void setAaa(final Integer aaa) {
            this.aaa = aaa;
        }

        public String toString() {
            return "aaa=" + aaa;
        }

    }

}
