package org.seasar.teeda.core.managedbean.impl;

import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TestManagedBeanFactoryImpl extends TeedaTestCase {

	private C c;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestManagedBeanFactoryImpl.class);
	}

	public TestManagedBeanFactoryImpl(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSetManagedBean(){
		ManagedBeanFactoryImpl factory = getManagedBeanFactory();
		factory.setManagedBean("a", A.class, Scope.REQUEST);
		A a = (A)factory.getManagedBean("a");
		assertNotNull(a);
		assertEquals("hoge", a.getStr());
	}
	
	public void testSetManagedBean2(){
		ManagedBeanFactoryImpl factory = getManagedBeanFactory();
		factory.setManagedBean("a", A.class, Scope.REQUEST);
		factory.setManagedBean("b", B.class, Scope.REQUEST);
		B b = (B)factory.getManagedBean("b");
		assertEquals("hoge", b.getString());
	}
	
	public void testSetManagedBean3(){
		ManagedBeanFactoryImpl factory = getManagedBeanFactory();
		factory.setManagedBean("c", C.class, Scope.SESSION, "init", "destroy");
		c = (C)factory.getManagedBean("c");
		assertEquals("init:", c.toString());
	}
	
	protected void tearDownSetManagedBean3() throws Throwable {
		super.tearDownForEachTestMethod();
		assertEquals("init:destroy", c.toString());
	}
	
	
	
	//scope matter
	//
	
	public static class A{
		public A(){
		}
		private String str = "hoge";
		public String getStr(){
			return str;
		}
	}
	
	public static class B{
		private A a_;
		public void setA(A a){
			a_ = a;
		}
		
		public String getString(){
			return (a_ != null) ? a_.getStr() : null;
		}
	}
	
	public static class C{
		private String initName = "";
		private String destroyName = "";
		public C(){
		}
		public void init(){
			initName = "init";
		}
		public void destroy(){
			destroyName = "destroy";
		}
		public String toString(){
			return initName + ":" + destroyName;
		}
	}

}
