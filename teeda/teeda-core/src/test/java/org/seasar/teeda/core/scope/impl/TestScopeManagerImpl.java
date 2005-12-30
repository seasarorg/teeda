package org.seasar.teeda.core.scope.impl;

import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.unit.TeedaTestCase;


public class TestScopeManagerImpl extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestScopeManagerImpl.class);
	}

	public TestScopeManagerImpl(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetScope(){
		ScopeManagerImpl manager = new ScopeManagerImpl();
		assertEquals(Scope.APPLICATION, manager.getScope(Scope.APPLICATION.getScopeKey()));
		assertEquals(Scope.SESSION, manager.getScope(Scope.SESSION.getScopeKey()));
		assertEquals(Scope.REQUEST, manager.getScope(Scope.REQUEST.getScopeKey()));
		assertEquals(Scope.NONE, manager.getScope(Scope.NONE.getScopeKey()));
		HogeScope hoge = new HogeScope();		
		manager.addScope(hoge, "aaa");
		assertEquals(hoge, manager.getScope(hoge.getScopeKey()));
	}
	
	public void testIsDefaultScope(){
		ScopeManagerImpl manager = new ScopeManagerImpl();
		assertTrue(manager.isDefaultScope(Scope.APPLICATION));
		assertTrue(manager.isDefaultScope(Scope.SESSION));
		assertTrue(manager.isDefaultScope(Scope.REQUEST));
		assertTrue(manager.isDefaultScope(Scope.NONE));
		
		assertFalse(manager.isDefaultScope(new Scope(){
			public String getScopeKey() {
				return "aaa";
			}
			
		}));
	}
	
	private static class HogeScope implements Scope{
		public String getScopeKey() {
			return "hoge";
		}
		
	}
}
