/**
 *
 */
package examples.teeda.web.add.impl;

import examples.teeda.web.add.Add3Page;
import examples.teeda.web.add.Add3Service;

/**
 * @author shot
 */
public class Add3ServiceImpl implements Add3Service {

	private static final int FLAVOR = 1000;

	public Add3Page doCalculate(Add3Page page) {
		Add3Page add3Page = new Add3Page();
		int arg1 = page.getArg1();
		int arg2 = page.getArg2();
		add3Page.setArg1(arg1);
		add3Page.setArg2(arg2);
		add3Page.setResult(arg1 + arg2 + FLAVOR);
		return add3Page;
	}

}
