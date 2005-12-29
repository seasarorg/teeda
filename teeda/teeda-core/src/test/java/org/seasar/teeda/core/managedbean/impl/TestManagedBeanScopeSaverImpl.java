package org.seasar.teeda.core.managedbean.impl;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.managedbean.IllegalManagedBeanScopeException;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeSaver;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TestManagedBeanScopeSaverImpl extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestManagedBeanScopeSaverImpl.class);
	}

	public TestManagedBeanScopeSaverImpl(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSaveToScope1(){
		ManagedBeanScopeSaverImpl saver = new ManagedBeanScopeSaverImpl();
		saver.saveToScope(getFacesContext(), Scope.APPLICATION, "hoge", "foo");
		
		assertEquals("foo",
				getFacesContext().getExternalContext().getApplicationMap().get("hoge"));
		
		saver.saveToScope(getFacesContext(), Scope.SESSION, "hoge", "foo");
		
		assertEquals("foo",
				getFacesContext().getExternalContext().getSessionMap().get("hoge"));

		saver.saveToScope(getFacesContext(), Scope.REQUEST, "hoge", "foo");
		
		assertEquals("foo",
				getFacesContext().getExternalContext().getRequestMap().get("hoge"));

	}
	
	public void testSaveToScope2(){
		ManagedBeanScopeSaverImpl saver = new ManagedBeanScopeSaverImpl();
		try{
			saver.saveToScope(getFacesContext(), new Scope(){
				public String getScopeKey() {
					return "hoge";
				}
				
			}, "hoge", "foo");
			fail();
		}catch(IllegalManagedBeanScopeException expected){
			success();
		}
	}
	
	public void testAddScope(){
		ManagedBeanScopeSaverImpl saver = new ManagedBeanScopeSaverImpl();
		Map map = new HashMap();
		CustomScopeSaver customSaver = new CustomScopeSaver(map);
		HogeScope scope = new HogeScope();
		saver.addScope(scope, customSaver);
		
		saver.saveToScope(getFacesContext(), scope, "a", "b");
		assertEquals("b", map.get("a"));
	}
	
	private static class HogeScope implements Scope{

		public String getScopeKey() {
			return "hoge";
		}
		
	}
	
	private static class CustomScopeSaver implements ScopeSaver{
		private Map map_;
		public CustomScopeSaver(Map map){
			map_ = map;
		}
		public void saveToScope(FacesContext context, String key, Object value) {
			map_.put(key, value);
		}
		
	}
}
