package br.org.ons.platform.common.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class IdGeneratorTest {
	
	@Test
	public void test() {
		String id = IdGenerator.newId();
		assert(id != null);
		assert(id != IdGenerator.newId());
	}
}
