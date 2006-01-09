package org.seasar.teeda.core.el.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.el.TeedaVariableResolver;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.mock.MockPropertyResolver;
import org.seasar.teeda.core.mock.MockVariableResolver;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TestValueBindingImpl extends TeedaTestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestValueBindingImpl.class);
	}

	public TestValueBindingImpl(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetValueSimple1(){
		A a = new A();
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("a", a);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a}", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertTrue(o == a);
	}
	
	public void testGetValueSimple2(){
		A a = new A();
		MockVariableResolver variableResolver = getVariableResolver();
		variableResolver.putValue("a", a);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a.name}", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertEquals("aaa", o);
		getApplication().getPropertyResolver().setValue(a, "name", "bbb");
		o = vb.getValue(getFacesContext());
		assertEquals("bbb", o);
	}
	
	public void testGetValueSimple3(){
		A a = new A();
		B b = new B();
		b.setName("hoge");
		MockVariableResolver variableResolver = getVariableResolver();
		variableResolver.putValue("a", a);
		MockPropertyResolver propertyResolver = getPropertyResolver();
		propertyResolver.setValue(a, "b", b);
		getApplication().setPropertyResolver(propertyResolver);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a.b.name}", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertEquals("hoge", o);
	}
	
	public void testGetValueSimple4(){
		Hoge hoge = new Hoge();
		MockVariableResolver variableResolver = getVariableResolver();
		variableResolver.putValue("hoge",hoge);
		MockPropertyResolver propertyResolver = getPropertyResolver();
		getApplication().setPropertyResolver(propertyResolver);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{hoge.a.name}", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertEquals("aaa", o);
	}
	
	public void testGetValueSimple5(){
		C c = new C();
		c.setHoge(true);
		MockVariableResolver variableResolver = getVariableResolver();
		variableResolver.putValue("c_",c);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{c_.hoge}", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertTrue(((Boolean)o).booleanValue());
	}
	
	public void testGetMapValue1(){
		Map map = new HashMap();
		map.put("hoge","foo");
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("hogemap", map);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{hogemap}", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertSame(map, o);
		Map m = (Map)o;
		assertEquals("foo", m.get("hoge"));
	}
	
	public void testGetMapValue2(){
		Map map = new HashMap();
		map.put("hoge","foo");
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("hogemap", map);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{hogemap.hoge}", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertEquals("foo", o);
	}
	
	public void testGetMapValue3(){
		Map map = new HashMap();
		map.put("a", new A());
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("hogemap", map);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{hogemap[\"a\"]['name'] }", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertEquals("aaa", o);
	}
	
	public void testGetListValue1(){
		List list = new ArrayList();
		list.add("aaa");
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("list", list);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{list[0] }", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertEquals("aaa", o);
		
		list.add(0, "bbb");
		o = vb.getValue(getFacesContext());
		assertEquals("bbb", o);
		
		vb.setValue(getFacesContext(), "ccc");
		o = vb.getValue(getFacesContext());
		assertEquals("ccc", o);
	}
	
	public void testGetExpressionalValue1(){
		A a = new A();
		B b = new B();
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("a", a);
		resolver.putValue("b", b);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{true ? a : b}", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertSame(a, o);
	}
	
	public void testGetMixedValue1(){
		A a = new A();
		B b = new B();
		a.setName("hoge");
		b.setName("bar");
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("a", a);
		resolver.putValue("b", b);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a.name} and #{b.name}", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertEquals("hoge and bar", o);
	}
	
	public void testIsReadOnly1(){
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{'baz'}", new CommonsELParser());
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("'baz'", "'baz'");
		assertTrue(vb.isReadOnly(getFacesContext()));
	}
	
	public void testIsReadOnly2(){
		A a = new A();
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("a", a);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a.name}", new CommonsELParser());
		assertFalse(vb.isReadOnly(getFacesContext()));
	}
	
	public void testIsReadOnly3(){
		Foo foo = new Foo();
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("foo", foo);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{foo.num}", new CommonsELParser());
		assertTrue(vb.isReadOnly(getFacesContext()));
	}
	
	public void testIsReadOnlyImplicit(){
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{cookie}", new CommonsELParser());
		assertTrue(vb.isReadOnly(getFacesContext()));
		
		vb = new ValueBindingImpl(getApplication(), "#{applicationScope}", new CommonsELParser());
		assertTrue(vb.isReadOnly(getFacesContext()));
		
		vb = new ValueBindingImpl(getApplication(), "#{facesContext}", new CommonsELParser());
		assertTrue(vb.isReadOnly(getFacesContext()));
		
		vb = new ValueBindingImpl(getApplication(), "#{header}", new CommonsELParser());
		assertTrue(vb.isReadOnly(getFacesContext()));
		
		vb = new ValueBindingImpl(getApplication(), "#{headerValues}", new CommonsELParser());
		assertTrue(vb.isReadOnly(getFacesContext()));
		
		vb = new ValueBindingImpl(getApplication(), "#{initParam}", new CommonsELParser());
		assertTrue(vb.isReadOnly(getFacesContext()));
		
		vb = new ValueBindingImpl(getApplication(), "#{param}", new CommonsELParser());
		assertTrue(vb.isReadOnly(getFacesContext()));
		
		vb = new ValueBindingImpl(getApplication(), "#{paramValues}", new CommonsELParser());
		assertTrue(vb.isReadOnly(getFacesContext()));
		
		vb = new ValueBindingImpl(getApplication(), "#{requestScope}", new CommonsELParser());
		assertTrue(vb.isReadOnly(getFacesContext()));
		
		vb = new ValueBindingImpl(getApplication(), "#{sessionScope}", new CommonsELParser());
		assertTrue(vb.isReadOnly(getFacesContext()));
	}
	
	public void testGetType1(){
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{'hoge'}", new CommonsELParser());
		assertSame(String.class, vb.getType(getFacesContext()));
	}
	
	public void testGetType2(){
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{true}", new CommonsELParser());
		assertSame(Boolean.class, vb.getType(getFacesContext()));
	}
	
	public void testGetType3(){
		getApplication().setVariableResolver(new TeedaVariableResolver());
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{requestScope}", new CommonsELParser());
		assertTrue(Map.class.isAssignableFrom(vb.getType(getFacesContext())));
	}
	
	public void testGetType4(){
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("aaa", new A());
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{aaa}", new CommonsELParser());
		assertEquals(A.class, vb.getType(getFacesContext()));
	}
	
	public void testGetExpressionString(){
		A a = new A();
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("a", a);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a}", new CommonsELParser());
		String str = vb.getExpressionString();
		assertEquals("#{a}", str);
	}
	
	public void testSetType1(){
		A a = new A();
		a.setName("hoge");
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("a", a);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a}", new CommonsELParser());
		A anotherA = (A)vb.getValue(getFacesContext());
		assertEquals("hoge", anotherA.getName());
		
		ManagedBeanFactory factory = getManagedBeanFactory();
		factory.setManagedBean("a", A.class, Scope.REQUEST);
		
		anotherA.setName("foo");
		vb.setValue(getFacesContext(), anotherA);
		anotherA = (A)vb.getValue(getFacesContext());
		assertEquals("foo", anotherA.getName());
	}
	
	public void testSetType2(){
		A a = new A();
		a.setName("aaa");
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("a", a);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a.name}", new CommonsELParser());
		String s = (String)vb.getValue(getFacesContext());
		assertEquals("aaa", s);
		
		vb.setValue(getFacesContext(), "bbb");
		assertEquals("bbb", (String)vb.getValue(getFacesContext()));
	}
	
	public void testSetType3(){
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("num", new Integer(123));
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{num}", new CommonsELParser());
		assertEquals(new Integer(123), vb.getValue(getFacesContext()));

		resolver.putValue("num", new Integer(345));
		vb.setValue(getFacesContext(), new Integer(345));
		
		Integer num = new Integer(345);
		assertEquals(num, vb.getValue(getFacesContext()));
	}
		
	public static class A{
		private String name = "aaa";
		private B b_;
		public A(){
		}
		public void setName(String name){
			this.name = name;
		}
		public String getName(){
			return name;
		}
		public void setB(B b){
			b_ = b;
		}
		public B getB(){
			return b_;
		}
	}
	
	public static class B{
		private String name_;
		public B(){
			
		}
		public void setName(String name){
			name_ = name;
		}
		public String getName(){
			return name_;
		}
	}

	public static class C{
		private boolean hoge_ = false;
		public void setHoge(boolean hoge){
			hoge_ = hoge;
		}
		public boolean isHoge(){
			return hoge_;
		}
	}
	
	public static class D{
		List list_;
		public D(List list){
			list_ = list;
		}
		public List getList(){
			return list_;
		}
	}
	
	public static class Hoge{
		public Hoge(){
		}
		public A getA(){
			return new A();
		}
	}
	
	public static class Foo{
		public int getNum(){
			return 0;
		}
	}
}
