package org.seasar.teeda.spike;

import java.io.Serializable;
import java.lang.reflect.Array;

import junit.framework.TestCase;

public class ArrayTest extends TestCase {

    public void test1() throws Exception {
        HogePage h1 = new HogePage("a");
        HogePage h2 = new HogePage("b");
        HogePage h3 = new HogePage("c");
        HogePage[] h = new HogePage[] { h1, h2, h3 };
        System.out.println(h.getClass());
        System.out.println(h.getClass().getComponentType());
        System.out.println(h.length);

        Object o = h;
        int i = 0;
        for (i = 0;; i++) {
            try {
                Array.get(o, i);
            } catch (ArrayIndexOutOfBoundsException ignore) {
                break;
            }
        }
        System.out.println(i);

    }

    public static class HogePage implements Serializable {

        private String name;

        public HogePage(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
