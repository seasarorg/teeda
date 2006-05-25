package org.seasar.teeda.extension.spike.rhino;

import junit.framework.Test;

public class FooTest extends RhinoTestCase {

	public static Test suite() throws Throwable {
		return new FooTest().createJsTestCase();
	}
}
