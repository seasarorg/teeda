package org.seasar.teeda.core.el;

import java.util.HashMap;
import java.util.Map;

import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.mock.MockPropertyResolver;
import org.seasar.teeda.core.mock.MockVariableResolver;
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

	public void testValueBindingSimple1(){
		A a = new A();
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("a", a);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{a}", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertTrue(o == a);
	}
	
	public void testValueBindingSimple2(){
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
	
	public void testValueBindingSimple3(){
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
	
	public void testValueBindingSimple4(){
		Hoge hoge = new Hoge();
		MockVariableResolver variableResolver = getVariableResolver();
		variableResolver.putValue("hoge",hoge);
		MockPropertyResolver propertyResolver = getPropertyResolver();
		getApplication().setPropertyResolver(propertyResolver);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{hoge.a.name}", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertEquals("aaa", o);
	}
	
	public void testValueBindingSimple5(){
		C c = new C();
		c.setHoge(true);
		MockVariableResolver variableResolver = getVariableResolver();
		variableResolver.putValue("c_",c);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{c_.hoge}", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertTrue(((Boolean)o).booleanValue());
	}
	
	public void testValueBindingMap1(){
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
	
	public void testValueBindingMap2(){
		Map map = new HashMap();
		map.put("hoge","foo");
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("hogemap", map);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{hogemap.hoge}", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertEquals("foo", o);
	}
	
	public void testValueBindingMap3(){
		Map map = new HashMap();
		map.put("a", new A());
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("hogemap", map);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{hogemap[\"a\"]['name'] }", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertEquals("aaa", o);
	}
	
	public void testValueBindingExpression1(){
		A a = new A();
		B b = new B();
		MockVariableResolver resolver = getVariableResolver();
		resolver.putValue("a", a);
		resolver.putValue("b", b);
		ValueBinding vb = new ValueBindingImpl(getApplication(), "#{true ? a : b}", new CommonsELParser());
		Object o = vb.getValue(getFacesContext());
		assertSame(a, o);
	}
	
	public void testValueBindingMixed1(){
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
		notDoneYet();
	}
	
	public void testGetExpressionString(){
		notDoneYet();
	}
	
	public void testSaveAndRestoreState(){
		notDoneYet();
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
