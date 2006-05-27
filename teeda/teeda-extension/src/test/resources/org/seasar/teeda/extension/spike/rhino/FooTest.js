//javascript loader
loadScript("org.seasar.teeda.extension.spike.rhino.Target");

//java.lang.System.out.println(a);
//java.lang.System.out.println(b);

function testFunction1(){
		java.lang.System.out.println('TEST1');
		assertEquals(a, 'TEST_1');
		//assertEquals(true, true);
}

function testFunction2(){
		java.lang.System.out.println('TEST2');
		assertEquals(b, 'TEST_2');
		//assertEquals(true, true);
}
