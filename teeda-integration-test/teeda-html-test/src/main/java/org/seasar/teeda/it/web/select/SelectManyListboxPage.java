package org.seasar.teeda.it.web.select;

import java.util.ArrayList;
import java.util.List;

import org.seasar.framework.util.ArrayUtil;

public class SelectManyListboxPage {

    private List aaaItems;

    private Integer[] aaa;

    public String prerender() {
        aaaItems = new ArrayList();
        final AaaDto dto1 = new AaaDto();
        dto1.setValue(0);
        dto1.setLabel("AAA");
        aaaItems.add(dto1);
        final AaaDto dto2 = new AaaDto();
        dto2.setValue(1);
        dto2.setLabel("BBB");
        aaaItems.add(dto2);
        final AaaDto dto3 = new AaaDto();
        dto3.setValue(2);
        dto3.setLabel("CCC");
        aaaItems.add(dto3);
        return null;
    }

    public List getAaaItems() {
        return aaaItems;
    }

    public void setAaaItems(final List aaaItems) {
        this.aaaItems = aaaItems;
    }

    public Integer[] getAaa() {
        if (aaa == null) {
            aaa = new Integer[0];
        }
        return aaa;
    }

    public void setAaa(final Integer[] aaa) {
        this.aaa = aaa;
    }

    public String getAaaAsString() {
        return ArrayUtil.toString(getAaa());
    }

    public String doAction() {
        return null;
    }

    public static class AaaDto {

        private String label;

        private int value;

        public String getLabel() {
            return label;
        }

        public int getValue() {
            return value;
        }

        public void setLabel(final String label) {
            this.label = label;
        }

        public void setValue(final int value) {
            this.value = value;
        }

    }

}
