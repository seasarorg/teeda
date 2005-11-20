package org.seasar.teeda.core.context;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;


public abstract class AbstractUnmodifiableExternalContextMap extends
        AbstractExternalContextMap {

    public AbstractUnmodifiableExternalContextMap(){
    }
    
    public final Set entrySet() {
        return Collections.unmodifiableSet(super.entrySet());
    }
    public final Set keySet() {
        return Collections.unmodifiableSet(super.keySet());
    }
    public final Collection values() {
        return Collections.unmodifiableCollection(super.values());
    }
    
    public final void clear() {
        throw new UnsupportedOperationException();
    }
    
    public final Object put(Object key, Object value) {
        throw new UnsupportedOperationException();
    }
    
    public final void putAll(Map map) {
        throw new UnsupportedOperationException();
    }
    
    public final Object remove(Object key) {
        throw new UnsupportedOperationException();
    }
    
    protected final void setAttribute(String key, Object value) {
        throw new UnsupportedOperationException();
    }
    
    protected final void removeAttribute(String key){
        throw new UnsupportedOperationException();
    }
}
