package org.seasar.teeda.core.util;

import java.util.Enumeration;
import java.util.Vector;

import junit.framework.TestCase;

public class TestEnumerationIterator extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestEnumerationIterator.class);
	}

	public TestEnumerationIterator(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testEnumerationIterator(){
		Vector vector = new Vector();
		vector.add("a");
		EnumerationIterator itr = 
			new EnumerationIterator(vector.elements());
		assertTrue(itr.hasNext());
		assertEquals("a", itr.next());
		try{
			itr.remove();
		}catch(UnsupportedOperationException expected){
		}
	}
}
