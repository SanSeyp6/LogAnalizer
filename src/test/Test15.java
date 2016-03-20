package test;

import util.StringComparison;

/**
 * Данный тест проверяет работу метода {@link StringComparison#computeAllLCSubstings(String, String)}
 * @author pih
 *
 */
public class Test15 {

	public static void main(String[] args) {
		String a= "someonesometwoanyfour";
		String b= "sometwosomeoneanyfour";

		System.out.println(StringComparison.computeAllLCSubstings(a, b));
	}

}
