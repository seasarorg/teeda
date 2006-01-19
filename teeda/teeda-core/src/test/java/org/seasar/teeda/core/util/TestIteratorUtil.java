package org.seasar.teeda.core.util;

import junit.framework.TestCase;
import java.util.*;

public class TestIteratorUtil extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestIteratorUtil.class);
	}

	public TestIteratorUtil(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetIterator(){
		List list = new ArrayList();
		list.add("a");
		for(Iterator itr = IteratorUtil.getIterator(list);itr.hasNext();){
			assertNotNull(itr);
			assertNotNull(itr.next());
		}
		list = null;
		Iterator itr = IteratorUtil.getIterator(list);
		assertNotNull(itr);
		for(;itr.hasNext();){
			fail();
		}
	}
	
	public void testGetIterator2(){
		Map map = new HashMap();
		map.put("a","A");
		for(Iterator itr = IteratorUtil.getEntryIterator(map);itr.hasNext();){
			assertNotNull(itr);
			assertNotNull(itr.next());
		}
		map = null;
		Iterator itr = IteratorUtil.getEntryIterator(map);
		assertNotNull(itr);
		for(;itr.hasNext();){
			fail();
		}
	}
}
