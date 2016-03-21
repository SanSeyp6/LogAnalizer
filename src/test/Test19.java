package test;

import java.util.Arrays;
import java.util.List;

import util.StringComparison;

/**
 * Данный тест проверяет работу метода {@link StringComparison#computeAllLCSubstringsForStringGroup(java.util.List)}
 * @author pih
 *
 */
public class Test19 {

	public static void main(String[] args) {
		List<String> similarStrings = Arrays.asList(
				"ho",
				"ho",
				"to=<",
				"ho",
				"to=<",
				"ho",
				"to=<"
		);

		System.out.println(StringComparison.computeAllLCSubstringsForStringGroup(similarStrings));
	}

}
