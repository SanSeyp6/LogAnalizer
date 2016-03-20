package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import util.StringComparison;

/**
 * Данный тест проверяет работу метода
 * {@link StringComparison#computeAllLCSubstringsForStringGroupAndLCS(java.util.List, String)}
 * 
 * @author pih
 *
 */
public class Test17 {
	
	public static void main(String[] args) {
		//List<String> similarStrings=Arrays.asList(new String[]{"someonesometwoanyfour", "sometwosomeoneanyfour", "anyfoursomeonesometwo"});
		List<String> similarStrings=Test16.TEST_CASE_1;
		String lcSubsequence = StringComparison.computeLCSubsequenceForStringGroup(similarStrings);
		System.out.println(lcSubsequence);
		Set<String> lcStrings = StringComparison.computeAllLCSubstringsForStringGroup(similarStrings);
		System.out.println(lcStrings);
		System.out.println();
		
		List<String> left=buildLeftSubstrings(similarStrings, lcStrings.toArray(new String[0])[0]);
		for(String s: left){
			System.out.println(s);
		}
		lcStrings = StringComparison.computeAllLCSubstringsForStringGroup(left);
		System.out.println(lcStrings);
		System.out.println();
		
		left=buildLeftSubstrings(left, lcStrings.toArray(new String[0])[0]);
		for(String s: left){
			System.out.println(s);
		}
		lcStrings = StringComparison.computeAllLCSubstringsForStringGroup(left);
		System.out.println(lcStrings);
		System.out.println();
		
		System.out.println("---begin-right----------");
		List<String> right=buildRightSubstrings(left, lcStrings.toArray(new String[0])[0]);
		for(String s: right){
			System.out.println(s);
		}
		System.out.println(StringComparison.computeLCSubsequenceForStringGroup(right));
		lcStrings = StringComparison.computeAllLCSubstringsForStringGroup(right);
		System.out.println(lcStrings);
		
		System.out.println();
	}
	
	private static List<String> buildLeftSubstrings(List<String> strings, String lcSubstring){
		List<String> leftSubstrings = new ArrayList<String>(strings.size());
		int index;
		for(String s: strings){
			index = s.indexOf(lcSubstring);
			leftSubstrings.add(s.substring(0, index));
		}
		return leftSubstrings;
	}

	private static List<String> buildRightSubstrings(List<String> strings, String lcSubstring){
		List<String> rightSubstrings = new ArrayList<String>(strings.size());
		int index;
		for(String s: strings){
			index = s.indexOf(lcSubstring);
			index +=lcSubstring.length();
			rightSubstrings.add(s.substring(index, s.length()));
		}
		return rightSubstrings;
	}

}
