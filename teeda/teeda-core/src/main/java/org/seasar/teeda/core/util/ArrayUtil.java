package org.seasar.teeda.core.util;

import java.util.List;


public class ArrayUtil {

    private ArrayUtil(){
    }
    
    public static boolean isEmpty(Object[] arrays){
        return (arrays == null || arrays.length == 0);
    }

}
