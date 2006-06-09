//javascript loader
loadScript("org.seasar.teeda.extension.spike.rhino.Target");

//java.lang.System.out.println(a);
//java.lang.System.out.println(b);

function testFunction1(){
		assertEquals(a, 'TEST_1');
}

function testFunction2(){
		assertEquals(b, 'TEST_2');
}

//function testElementById(){
//	var ele = document.getElementById("test");
//	var name = ele.nodeName;
//	assertEquals(name, 'DIV');
//}
