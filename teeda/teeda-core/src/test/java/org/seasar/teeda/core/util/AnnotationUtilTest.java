package org.seasar.teeda.core.util;

import java.lang.reflect.Field;

import junit.framework.TestCase;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author shot
 */
public class AnnotationUtilTest extends TestCase {

    public void testIsConstantAnnotation() throws Exception {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Hoge.class);
        Field f1 = beanDesc.getField("A");
        assertFalse(AnnotationUtil.isConstantAnnotation(f1));

        Field f2 = beanDesc.getField("B");
        assertFalse(AnnotationUtil.isConstantAnnotation(f2));

        Field f3 = beanDesc.getField("C");
        assertFalse(AnnotationUtil.isConstantAnnotation(f3));

        Field f4 = beanDesc.getField("D");
        assertFalse(AnnotationUtil.isConstantAnnotation(f4));

        Field f5 = beanDesc.getField("F");
        assertTrue(AnnotationUtil.isConstantAnnotation(f5));
    }

    public static class Hoge {
        public static String A = "A";

        protected static final String B = "B";

        public String C = "C";

        public static final int D = 1;

        public static final String F = "F";
    }

}
