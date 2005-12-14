package org.seasar.teeda.core.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.seasar.framework.util.EmptyIterator;


public class IteratorUtil {

    private static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    private IteratorUtil(){
    }
    
    public static Iterator getIterator(Collection collection){
        if(collection != null && !collection.isEmpty()){
            return collection.iterator();
        }else{
            return EMPTY_ITERATOR;
        }
    }
    
    public static Iterator getIterator(Map map){
        if(map != null && !map.isEmpty()){
            return map.keySet().iterator();
        }else{
            return EMPTY_ITERATOR;
        }
    }
}
