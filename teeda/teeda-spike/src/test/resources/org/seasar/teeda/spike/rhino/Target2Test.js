//javascript loader
loadScript("org.seasar.teeda.spike.rhino.Target2");

function testAdd1(){
		java.lang.System.out.println('add test');
		assertEquals(2, add(1,1));
}
