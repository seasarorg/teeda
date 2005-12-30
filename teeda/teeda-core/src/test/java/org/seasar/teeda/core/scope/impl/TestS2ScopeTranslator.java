package org.seasar.teeda.core.scope.impl;

import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.teeda.core.scope.Scope;

import junit.framework.TestCase;

public class TestS2ScopeTranslator extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestS2ScopeTranslator.class);
	}

	public TestS2ScopeTranslator(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testToScope(){
		S2ScopeTranslator translator = new S2ScopeTranslator();
		assertEquals(Scope.NONE, translator.toScope(InstanceDefFactory.OUTER));
		assertEquals(Scope.REQUEST, translator.toScope(InstanceDefFactory.REQUEST));
		assertEquals(Scope.SESSION, translator.toScope(InstanceDefFactory.SESSION));
		assertEquals(Scope.APPLICATION, translator.toScope(InstanceDefFactory.SINGLETON));
	}
	
	public void testToExternalComponentScope(){
		S2ScopeTranslator translator = new S2ScopeTranslator();
		assertEquals(InstanceDefFactory.OUTER, translator.toExternalComponentScope(Scope.NONE));
		assertEquals(InstanceDefFactory.REQUEST, translator.toExternalComponentScope(Scope.REQUEST));
		assertEquals(InstanceDefFactory.SESSION, translator.toExternalComponentScope(Scope.SESSION));
		assertEquals(InstanceDefFactory.SINGLETON, translator.toExternalComponentScope(Scope.APPLICATION));		
	}
}
