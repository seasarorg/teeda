package org.seasar.teeda.core.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.WeakHashMap;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockVariableResolver;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class VariableResolverUtilTest extends TeedaTestCase {

    public void testResolveVariable() {
        MockVariableResolver resolver = getVariableResolver();
        resolver.putValue("hoge", "foo");
        Object value = VariableResolverUtil.resolveVariable(getFacesContext(),
                "hoge");
        assertEquals("foo", value);
    }

    public void testGetDefaultScopeMap() {
        MockVariableResolver resolver = getVariableResolver();
        Map reqMap = new HashMap();
        reqMap.put("key", "value");
        resolver.putValue(JsfConstants.REQUEST_SCOPE, reqMap);
        Object value = VariableResolverUtil.getDefaultScopeMap(
                getFacesContext(), resolver, "key");
        assertNotNull(value);
        assertTrue(value instanceof HashMap);
        assertTrue(((Map) value).containsKey("key"));

        Map sessionMap = new WeakHashMap();
        sessionMap.put("key2", "value2");
        resolver.putValue(JsfConstants.SESSION_SCOPE, sessionMap);
        value = VariableResolverUtil.getDefaultScopeMap(getFacesContext(),
                resolver, "key2");
        assertNotNull(value);
        assertTrue(value instanceof WeakHashMap);
        assertTrue(((Map) value).containsKey("key2"));

        Map appMap = new Hashtable();
        appMap.put("key3", "value3");
        resolver.putValue(JsfConstants.APPLICATION_SCOPE, appMap);
        value = VariableResolverUtil.getDefaultScopeMap(getFacesContext(),
                resolver, "key3");
        assertNotNull(value);
        assertTrue(value instanceof Hashtable);
        assertTrue(((Map) value).containsKey("key3"));
    }

}
