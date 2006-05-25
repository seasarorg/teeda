//javascript loader
loadScript("org.seasar.teeda.extension.spike.rhino.Target");

java.lang.System.out.println(a);
java.lang.System.out.println(b);

function testFunction2(){
		java.lang.System.out.println('TEST1');
		assertEquals(true, true);
		//assertEquals(true, true);
}

function testFunction1(){
		java.lang.System.out.println('TEST2');
		assertEquals(true, true);
		//assertEquals(true, true);
}
