package org.ta.test;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.ta.common.config.TaException;
import org.ta.common.util.TaMathUtil;
import org.ta.trader.hista.tohlcv.TaTimeBarsFile;

public class TaTestSupport extends TestCase {
	public TaTimeBarsFile loadTohlcvFile(String sufix) throws IOException {
		return this.loadTohlcvFile(this.getClass(), sufix);
	}

	public TaTimeBarsFile loadTohlcvFile(Class cls, String sufix)
			throws IOException {
		return this.loadTohlcvFile(cls.getPackage(), cls.getSimpleName()
				+ sufix);
	}

	public TaTimeBarsFile loadTohlcvFile(Package pkg, String name)
			throws IOException {

		String res = "" + pkg.getName().replace('.', '/') + "/" + name;

		InputStream is = TaTestSupport.class.getClassLoader().getResourceAsStream(
				res);
		if (is == null) {
			throw new TaException("not found :" + res);
		}
		TaTimeBarsFile rt = new TaTimeBarsFile().load(is);

		return rt;
	}

	protected void assertEqauls(String message, double d1, double d2,
			int decimalsPower) {
		d2 = TaMathUtil.round(d2, decimalsPower);
		Assert.assertEquals(message, d1, d2);
	}
}
